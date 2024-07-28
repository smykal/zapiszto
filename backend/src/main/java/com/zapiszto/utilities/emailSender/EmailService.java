package com.zapiszto.utilities.emailSender;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  public void sendEmail(MailDto mailDto, String replyTo, String source) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailDto.to());
    message.setSubject(source);
    message.setReplyTo(replyTo);
    message.setSubject(mailDto.subject());
    message.setText(mailDto.body());

    mailSender.send(message);
  }

}
