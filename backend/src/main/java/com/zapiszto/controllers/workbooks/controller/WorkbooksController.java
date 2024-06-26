package com.zapiszto.controllers.workbooks.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.workbooks.dto.AddWorkbookDto;
import com.zapiszto.controllers.workbooks.dto.PatchWorkbookSchemaDto;
import com.zapiszto.controllers.workbooks.dto.WorkbooksDto;
import com.zapiszto.controllers.workbooks.service.WorkbooksService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/v1")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')  or hasRole('TRAINER')")
public class WorkbooksController implements ControllerCommon {

  @Autowired
  private WorkbooksService workbooksService;

  @PostMapping("/add_workbook")
  public ResponseEntity<String> addWorkbook(
      @RequestBody AddWorkbookDto addWorkbookDto
  ) {
    var userId = extractUserId();
    workbooksService.addWorkbook(
        addWorkbookDto,
        userId
    );
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/get_workbooks")
  public ResponseEntity<List<WorkbooksDto>> getWorkbooks() {
    var userId = extractUserId();
    try {
      var result = workbooksService.getWorkbooksForUser(userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("/get_workbooks/{userId}")
  public ResponseEntity<List<WorkbooksDto>> getWorkbooks(
      @PathVariable("userId") Long userId
  ) {
    var requestorId = extractUserId();
    try {
      var result = workbooksService.getWorkbooksForUser(userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }

  @DeleteMapping("/delete_workbook/{id}")
  public ResponseEntity<String> deleteWorkbook(@PathVariable Long id) {
    try {
      var userId = extractUserId();
      workbooksService.deleteWorkbookById(id, userId);
      return new ResponseEntity<>("Workbook deleted successfully", HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>("Workbook not found", HttpStatus.NOT_FOUND);
    }
  }

  @PatchMapping("/put_workbook_schema_id")
  public ResponseEntity<String> patchWorkbook(
      @RequestBody PatchWorkbookSchemaDto patchWorkbookSchemaDto
      ){
    try {
      workbooksService.updateWorkbookById(patchWorkbookSchemaDto);
      return new ResponseEntity<>("Workbook updated successfully", HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>("Workbook not found", HttpStatus.NOT_FOUND);
    }
  }
}
