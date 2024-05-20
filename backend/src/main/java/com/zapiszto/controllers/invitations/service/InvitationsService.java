package com.zapiszto.controllers.invitations.service;

import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import com.zapiszto.controllers.invitations.repository.InvitationsRepository;
import com.zapiszto.repository.UserRepository;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InvitationsService {

  @Autowired
  InvitationsRepository invitationsRepository;

  @Autowired
  UserRepository userRepository;

  @Transactional
  public String addInvitation(Long inviterId, String inviterRole, String inviteeEmail) {
    var checkIfEmailExists = checkIfEmailExists(inviteeEmail);
    var checkIfEmailNotBelongsToInviteer = checkIfEmailNotBelongsToInviter(inviteeEmail, inviterId);
    var checkIfRolesAreDifferent = checkIfRolesAreDifferent(inviterRole, inviteeEmail);

    if (checkIfEmailExists &&
        checkIfEmailNotBelongsToInviteer &&
        checkIfRolesAreDifferent) {
      InvitationsEntity invitationsEntity = InvitationsEntity.builder()
          .id(UUID.randomUUID())
          .inviter(inviterId)
          .invitee(userRepository.idByEmail(inviteeEmail))
          .build();
      invitationsRepository.save(invitationsEntity);
      return invitationsEntity.getId().toString();
    }


    return null;
  }

  private boolean checkIfRolesAreDifferent(String inviterRole, String inviteeEmail) {
    Long userIdByEmail = userRepository.idByEmail(inviteeEmail);
    String inviteeRole = userRepository.roleById(userIdByEmail);
    inviterRole = inviterRole.replace("[", "").replace("]","");
    return !Objects.equals(inviterRole, inviteeRole);
  }

  private boolean checkIfEmailNotBelongsToInviter(String inviteeEmail, Long inviterId) {
    Long userIdByEmail = userRepository.idByEmail(inviteeEmail);
    return userIdByEmail == null || !userIdByEmail.equals(inviterId);
  }

  private boolean checkIfEmailExists(String inviteeEmail) {
    return userRepository.existsByEmail(inviteeEmail);
  }
}
