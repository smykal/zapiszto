package com.zapiszto.controllers.account.service;

import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.account.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Transactional
  public void deleteUser(Long userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
      log.info("User with ID {} and its related entities have been deleted", userId);
    } else {
      log.warn("User with ID {} does not exist", userId);
    }
  }
}
