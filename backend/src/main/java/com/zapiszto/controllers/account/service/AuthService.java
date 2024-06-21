package com.zapiszto.controllers.account.service;

import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import com.zapiszto.controllers.account.repository.UserRepository;
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

  public void deleteUser(Long userId){
    userDetailsRepository.deleteByUserId(userId);
    userRepository.deleteById(userId);
    log.info("user with id {} has been deleted", userId);
  }
}
