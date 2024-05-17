package com.zapiszto.controllers.userSex.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.userSex.dto.GenderDto;
import com.zapiszto.controllers.userSex.dto.UserSexDto;
import com.zapiszto.controllers.userSex.service.UserSexService;
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
public class UserSexController implements ControllerCommon {

  @Autowired
  private UserSexService userSexService;

  @GetMapping("/get_sex")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<UserSexDto> getUserSex() {
    var userId = extractUserId();
    try {
      var response = userSexService.getUserSex(userId);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }


  @PostMapping("/post_sex")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> saveUserSex(
      @RequestBody GenderDto gender
  ) {
    var userId = extractUserId();
    userSexService.saveUserSex(userId, gender);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
