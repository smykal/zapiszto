package com.zapiszto.controllers.userDetails.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.userDetails.dto.UserDetailsAgeDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsBirthdateDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsGenderDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsLanguageDto;
import com.zapiszto.controllers.userDetails.dto.UserDetailsSexDto;
import com.zapiszto.controllers.userDetails.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class UserDetailsController implements ControllerCommon {

  @Autowired
  UserDetailsService userDetailsService;

  @GetMapping("/get_language")
  public ResponseEntity<UserDetailsDto> getUserLanguage() {
    var userId = extractUserId();
    try {
      var response = userDetailsService.getUserDetails(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/post_language")
  public ResponseEntity<String> saveUserLanguage(
      @RequestBody UserDetailsLanguageDto userDetailsLanguageDto
  ) {
    var userId = extractUserId();
    userDetailsService.saveOrUpdate(userId, userDetailsLanguageDto);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GetMapping("/get_sex")
  public ResponseEntity<UserDetailsSexDto> getUserSex() {
    var userId = extractUserId();
    try {
      var response = userDetailsService.getUserSex(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/get_sex/{userId}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<UserDetailsSexDto> getUserSex(
      @PathVariable("userId") Long userId
  ) {
    var requestorId = extractUserId();
    try {
      var response = userDetailsService.getUserSex(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/post_sex")
  public ResponseEntity<String> saveUserSex(
      @RequestBody UserDetailsGenderDto gender
  ) {
    var userId = extractUserId();
    userDetailsService.saveUserSex(userId, gender);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GetMapping("/get_age")
  public ResponseEntity<UserDetailsAgeDto> getUserAge() {
    var userId = extractUserId();
    try {
      var response = userDetailsService.getUserAge(userId);
      return new ResponseEntity<>(
          response,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/get_age/{userId}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<UserDetailsAgeDto> getUserAge(
      @PathVariable("userId") Long userId
  ) {
    var requestorId = extractUserId();
    try {
      var response = userDetailsService.getUserAge(userId);
      return new ResponseEntity<>(
          response,
          HttpStatus.OK
      );
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/post_birthdate")
  public ResponseEntity<String> saveUserBirthdate(
      @RequestBody UserDetailsBirthdateDto birthdate
  ) {
    var userId = extractUserId();
    userDetailsService.saveUserBirthDate(userId, birthdate);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
