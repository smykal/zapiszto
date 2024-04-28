package com.zapiszto.controllers.bodyParams.controller;

import com.zapiszto.controllers.ControllerCommon;
import com.zapiszto.controllers.bodyParams.dto.BmrDto;
import com.zapiszto.controllers.bodyParams.service.BmrService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
public class BmrController implements ControllerCommon {

  @Autowired
  BmrService bmrService;

      @GetMapping("/get_bmr")
      @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
      public ResponseEntity<List<BmrDto>> getTestTable() {
        var userId = extractUserId();
        log.info("get all body parameters for user: {} ", userId);
        try {
          var response = bmrService.getBmrByUserId(userId);
          return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NullPointerException e) {
          return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

      }
}
