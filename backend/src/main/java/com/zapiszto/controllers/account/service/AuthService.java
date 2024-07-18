package com.zapiszto.controllers.account.service;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.account.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
}
