package com.zapiszto.controllers.clientBodyTest.dictBodyTest.controller;

import com.zapiszto.controllers.clientBodyTest.dictBodyTest.dto.DictBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.dictBodyTest.dto.NewDictBodyTestDto;
import com.zapiszto.controllers.clientBodyTest.dictBodyTest.repository.DictBodyTestRepository;
import com.zapiszto.controllers.clientBodyTest.dictBodyTest.service.DictBodyTestService;
import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictCategory.dto.DictCategoryDto;
import com.zapiszto.controllers.dictCategory.dto.NewDictCategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
public class DictBodyTestController implements ControllerCommon {

  @Autowired
  DictBodyTestService dictBodyTestService;

  @PostMapping("/add_body_test_per_user")
  public ResponseEntity<DictBodyTestDto> addBodyTestPerUser(
      @RequestBody NewDictBodyTestDto dictBodyTestDto
  ) {
    var userId = extractUserId();
    DictBodyTestDto result = dictBodyTestService.addDictBodyTest(dictBodyTestDto, userId);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @PostMapping("/add_body_test_basic")
  public ResponseEntity<String> addBodyTestBasic(
      @RequestBody NewDictBodyTestDto newDictBodyTestDto
  ) {
    var userId = extractUserId();
    var role = extractUserRole();
    dictBodyTestService.addDictBodyTest(newDictBodyTestDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
