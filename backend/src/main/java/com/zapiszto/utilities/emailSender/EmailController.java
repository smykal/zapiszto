package com.zapiszto.utilities.emailSender;

import com.zapiszto.controllers.common.ControllerCommon;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class EmailController implements ControllerCommon {

  @Autowired
  EmailService emailService;

  @PostMapping("/send")
  public ResponseEntity<String> sendEmail(@Valid @RequestBody MailDto mailDto, @RequestParam String source) {
    try {
      String replyTo = extractUserMail();
      emailService.sendEmail(mailDto, replyTo, source);
      return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
