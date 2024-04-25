package com.zapiszto.controllers.dictBodyParams.controller;

import com.zapiszto.controllers.dictBodyParams.dto.DictBodyParamsDto;
import com.zapiszto.controllers.dictBodyParams.service.DictBodyParamsService;
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
public class DictBodyParamsController {

  @Autowired
  private DictBodyParamsService dictBodyParamsService;

  @GetMapping("/dictBodyParams")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<List<DictBodyParamsDto>> getDictBodyParams() {
    var dictBodyParams = dictBodyParamsService.getDictBodyParams();
    return new ResponseEntity<>(dictBodyParams,
                                HttpStatus.OK
    );
  }
}
