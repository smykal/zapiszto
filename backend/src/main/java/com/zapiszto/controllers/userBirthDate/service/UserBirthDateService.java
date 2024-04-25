package com.zapiszto.controllers.userBirthDate.service;

import com.zapiszto.controllers.userBirthDate.dto.UserAgeDto;
import com.zapiszto.controllers.userBirthDate.dto.UserBirthdateDto;
import com.zapiszto.controllers.userBirthDate.entity.UserBirthDateEntity;
import com.zapiszto.controllers.userBirthDate.repository.UserBirthDateRepository;
import java.sql.Date;
import java.time.ZonedDateTime;
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
      log.info("get user age for user: {}", userId);
      return new UserAgeDto(ageById);

  }

  public void saveUserBirthDate(Long userId, UserBirthdateDto userBirthdateDto){
    ZonedDateTime birthDate = ZonedDateTime.parse(userBirthdateDto.getBirthDate());

    UserBirthDateEntity userBirthDate = new UserBirthDateEntity(userId, birthDate);
    userBirthDateRepository.save(userBirthDate);
    log.info("save birthdate for user: {}", userId);
  }
}
