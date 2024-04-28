package com.zapiszto.controllers.dictWorkbookSchema.controller;

import com.zapiszto.controllers.dictWorkbookSchema.dto.DictWorkbookSchemaDto;
import com.zapiszto.controllers.dictWorkbookSchema.service.DictWorkbookSchemaService;
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
public class DictWorkbookSchemaController {

  @Autowired
  private DictWorkbookSchemaService dictWorkbookSchemaService;

  @GetMapping("/get_dict_workbook_schema")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictWorkbookSchemaDto>> getDictWorkbookSchema() {
    var result = dictWorkbookSchemaService.getAll();
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
