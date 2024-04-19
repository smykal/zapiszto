package com.bezkoder.spring.security.postgresql.controllers.workbooks.controller;

import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.AddWorkbookDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.PatchWorkbookSchemaDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.dto.WorkbooksDto;
import com.bezkoder.spring.security.postgresql.controllers.workbooks.service.WorkbooksService;
import com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class WorkbooksController {

  @Autowired
  private WorkbooksService workbooksService;

  @PostMapping("/add_workbook")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<WorkbooksDto>> getWorkbooks() {
    var userId = extractUserId();
    var result = workbooksService.getWorkbooksForUser(userId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping("/delete_workbook/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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


  private Long extractUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl user) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getId();
      //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }
}