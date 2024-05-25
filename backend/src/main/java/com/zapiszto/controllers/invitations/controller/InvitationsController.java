package com.zapiszto.controllers.invitations.controller;

import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.invitations.dto.InvitationDto;
import com.zapiszto.controllers.invitations.dto.NewInvitation;
import com.zapiszto.controllers.invitations.service.InvitationsService;
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
public class InvitationsController implements ControllerCommon {

  @Autowired
  InvitationsService invitationsService;

  @PostMapping("/add_invitation")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<String> addInvitation(
      @RequestBody NewInvitation newInvitation
      ) {
    var userId = extractUserId();
    var userRole = extractUserRole();
    String response = invitationsService.addInvitation(userId, userRole, newInvitation.getEmail());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/approve_invitation")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<String> approveInvitation(
      @RequestBody InvitationDto invitationDto
  ){
    var userId = extractUserId();
    String response = invitationsService.approveInvitation(invitationDto, userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/reject_invitation")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<String> rejectInvitation(
      @RequestBody InvitationDto invitationDto
  ){
    var userId = extractUserId();
    String response = invitationsService.rejectInvitation(invitationDto, userId);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping("/get_invitations")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<List<InvitationDto>> getInvitations(
  ) {
    var userId = extractUserId();
    try {
      List<InvitationDto> result = invitationsService.getInvitations(userId);
      return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (NullPointerException e) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
  }



}
