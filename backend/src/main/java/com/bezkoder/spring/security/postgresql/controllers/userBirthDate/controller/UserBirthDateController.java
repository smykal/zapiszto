package com.bezkoder.spring.security.postgresql.controllers.userBirthDate.controller;

import com.bezkoder.spring.security.postgresql.controllers.ControllerCommon;
import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.dto.UserAgeDto;
import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.dto.UserBirthdateDto;
import com.bezkoder.spring.security.postgresql.controllers.userBirthDate.service.UserBirthDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
public class UserBirthDateController implements ControllerCommon {

  @Autowired
  private UserBirthDateService userBirthDateService;

  @GetMapping("/get_age")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<UserAgeDto> getUserAge() {
    var userId = extractUserId();
    try {
      var response = userBirthDateService.getUserAge(userId);
      return new ResponseEntity<>(
          response,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

  }

  @PostMapping("/post_birthdate")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> saveUserBirthdate(
      @RequestBody UserBirthdateDto birthdate
  ) {
    var userId = extractUserId();
    userBirthDateService.saveUserBirthDate(userId, birthdate);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
