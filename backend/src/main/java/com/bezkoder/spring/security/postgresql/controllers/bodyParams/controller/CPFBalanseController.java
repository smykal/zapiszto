package com.bezkoder.spring.security.postgresql.controllers.bodyParams.controller;

import com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto.CPFDto;
import com.bezkoder.spring.security.postgresql.controllers.bodyParams.service.CPFBalanseService;
import com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
public class CPFBalanseController {

  @Autowired
  private CPFBalanseService cpfBalanseService;

  @GetMapping("/cpf")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<List<CPFDto>>> getCPF() {
    var userId = extractUserId();
    var response = cpfBalanseService.getCPF(userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private Long extractUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl user) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getId();
      //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }
}
