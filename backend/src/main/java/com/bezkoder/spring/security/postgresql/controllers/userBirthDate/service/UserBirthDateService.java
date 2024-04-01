package com.bezkoder.spring.security.postgresql.controllers.userBirthDate.service;

import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.dto.UserAgeDto;
import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.repository.UserBirthDateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserBirthDateService {

  @Autowired
  private UserBirthDateRepository userBirthDateRepository;

  public UserAgeDto getUserAge(Long userId) {
    Integer ageById = userBirthDateRepository.getAgeById(userId);
    return new UserAgeDto(ageById);
  }
}
