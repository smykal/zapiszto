package com.zapiszto.controllers.invitations.service;

import com.zapiszto.controllers.invitations.dictInvitationsStatus.entity.DictInvitationsStatusEntity;
import com.zapiszto.controllers.invitations.dictInvitationsStatus.repository.DictInvitationsStatusRepository;
import com.zapiszto.controllers.invitations.dto.InvitationDto;
import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import com.zapiszto.controllers.invitations.invitationsStatus.entity.InvitationsStatusEntity;
import com.zapiszto.controllers.invitations.invitationsStatus.repository.InvitationsStatusRepository;
import com.zapiszto.controllers.invitations.repository.InvitationsRepository;
import com.zapiszto.controllers.invitations.serializer.InvitationsSerializer;
import com.zapiszto.repository.UserRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class InvitationsService {

  @Autowired
  private DictInvitationsStatusRepository dictInvitationsStatusRepository;

  @Autowired
  InvitationsRepository invitationsRepository;

  @Autowired
  InvitationsStatusRepository invitationsStatusRepository;

  @Autowired
  UserRepository userRepository;

  private final String SENT = "SENT";
  private final String APPROVED = "APPROVED";
  private final String REJECTED = "REJECTED";
  private final String RESENT = "RESENT";
  private final String TERMINATED = "TERMINATED";

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
      log.info("add invitation with id {} sent by user {}, to {}",
          invitationsEntity.getId(),
          invitationsEntity.getInviter(),
          invitationsEntity.getInvitee());

      DictInvitationsStatusEntity status = dictInvitationsStatusRepository.getByName(SENT);

      InvitationsStatusEntity invitationsStatusEntity = InvitationsStatusEntity.builder()
          .id(UUID.randomUUID())
          .invitations_id(invitationsEntity)
          .insert_date(ZonedDateTime.now())
          .dictInvitationsStatusEntity(status)
          .build();
      invitationsStatusRepository.save(invitationsStatusEntity);
      log.info("add invitation Status with id: {}, invitation_id: {}, status: {}",
          invitationsStatusEntity.getId(),
          invitationsStatusEntity.getInvitations_id(),
          invitationsStatusEntity.getDictInvitationsStatusEntity().getName());

      return invitationsEntity.getId()
          .toString();
    }

    return null;
  }

  private boolean checkIfRolesAreDifferent(String inviterRole, String inviteeEmail) {
    Long userIdByEmail = userRepository.idByEmail(inviteeEmail);
    String inviteeRole = userRepository.roleById(userIdByEmail);
    inviterRole = inviterRole.replace("[", "")
        .replace("]", "");
    return !Objects.equals(inviterRole, inviteeRole);
  }

  private boolean checkIfEmailNotBelongsToInviter(String inviteeEmail, Long inviterId) {
    Long userIdByEmail = userRepository.idByEmail(inviteeEmail);
    return userIdByEmail == null || !userIdByEmail.equals(inviterId);
  }

  private boolean checkIfEmailExists(String inviteeEmail) {
    return userRepository.existsByEmail(inviteeEmail);
  }

  public List<InvitationDto> getInvitations(Long userId) {
    List<InvitationsStatusEntity> invitationsEntity = invitationsStatusRepository.getInvitations(userId);

    return invitationsEntity.stream()
        .map(InvitationsSerializer::convert)
        .map(invitationDto -> InvitationsSerializer.setRecivedAndSent(invitationDto, userId))
        .collect(Collectors.toList());
  }
}
