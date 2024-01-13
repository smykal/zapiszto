package com.bezkoder.spring.security.postgresql.controllers.testTable.controller;

import com.bezkoder.spring.security.postgresql.controllers.testTable.dto.TestTableDto;
import com.bezkoder.spring.security.postgresql.controllers.testTable.service.TestTableService;
import java.util.List;
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
public class TestTableController {

  @Autowired
  private TestTableService testTableService;

  @PostMapping("/test_post")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<String> saveTestItem(
      @RequestBody TestTableDto testTableDto
      ) {
    testTableService.saveTestItem(testTableDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/test_get")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<TestTableDto>> getTestTable() {
    var response = testTableService.getTestTable();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
