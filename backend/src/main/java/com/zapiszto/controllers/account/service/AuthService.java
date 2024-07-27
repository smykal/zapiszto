package com.zapiszto.controllers.account.service;

import com.zapiszto.controllers.account.dto.ForgotPasswordRequest;
import com.zapiszto.controllers.account.dto.ResetPasswordRequest;
import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.account.repository.UserRepository;
import com.zapiszto.utilities.emailSender.EmailService;
import com.zapiszto.utilities.emailSender.MailDto;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  EmailService emailService;

  @Transactional
  public void deleteUser(Long userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
      log.info("User with ID {} and its related entities have been deleted", userId);
    } else {
      log.warn("User with ID {} does not exist", userId);
    }
  }

  public void updatePassword(Long userId, String oldPassword, String newPassword) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Error: User not found."));

    if (!encoder.matches(oldPassword, user.getPassword())) {
      throw new RuntimeException("Error: Old password is incorrect.");
    }

    user.setPassword(encoder.encode(newPassword));
    userRepository.save(user);
  }

  @Transactional
  @Modifying
  public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
    User user = userRepository.findByEmail(forgotPasswordRequest.email())
        .orElseThrow(() -> new RuntimeException("Error: User not found."));

    String token = setPasswordRecoveryToken(forgotPasswordRequest);
    sendEmailWithRecoveryLink(token, user.getEmail());

  }

  public void sendEmailWithRecoveryLink(String recoveryToken, String userMail) {

    String resetLink = "http://www.zapiszto.pl/reset_password?token=" + recoveryToken;
    var mail = new MailDto(userMail,
        "Recovery Password",
        "To reset your password, click the link below:\n" + resetLink);

    emailService.sendEmail(mail, "presq333@gmail.com", "RECOVERY_PASSWORD");

  }


  public String setPasswordRecoveryToken(ForgotPasswordRequest forgotPasswordRequest){
    User user = userRepository.findByEmail(forgotPasswordRequest.email())
        .orElseThrow(() -> new RuntimeException("Error: User not found."));

    String token = UUID.randomUUID().toString();
    user.setResetToken(token);
    userRepository.save(user);
    return token;
  }

  public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
    User user = userRepository.findByResetToken(resetPasswordRequest.token())
        .orElseThrow(() -> new RuntimeException("Error: Invalid token."));

    user.setPassword(encoder.encode(resetPasswordRequest.newPassword()));
    user.setResetToken(null); // usunięcie tokenu po zresetowaniu hasła
    userRepository.save(user);
  }
}
