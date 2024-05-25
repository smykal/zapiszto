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
      log.info(
          "add invitation with id {} sent by user {}, to {}",
          invitationsEntity.getId(),
          invitationsEntity.getInviter(),
          invitationsEntity.getInvitee()
      );

      DictInvitationsStatusEntity status = dictInvitationsStatusRepository.getByName(SENT);

      InvitationsStatusEntity invitationsStatusEntity = InvitationsStatusEntity.builder()
          .id(UUID.randomUUID())
          .invitationsEntity(invitationsEntity)
          .insert_date(ZonedDateTime.now())
          .dictInvitationsStatusEntity(status)
          .build();
      invitationsStatusRepository.save(invitationsStatusEntity);
      log.info(
          "add invitation Status with id: {}, invitation_id: {}, status: {}",
          invitationsStatusEntity.getId(),
          invitationsStatusEntity.getInvitationsEntity(),
          invitationsStatusEntity.getDictInvitationsStatusEntity()
              .getName()
      );

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
  @Transactional
  public String approveInvitation(InvitationDto invitationDto, Long userId) {
    var checkIfInvitationIsAddressToUser = checkIfInvitationIsAddressToUser(invitationDto.getInviteeId(), userId);
    var checkIfLastStatusIsSent = checkIfLastStatusIsSent(invitationDto);
    if (checkIfInvitationIsAddressToUser &&
        checkIfLastStatusIsSent) {
      DictInvitationsStatusEntity status = dictInvitationsStatusRepository.getByName(APPROVED);
      InvitationsEntity invitation = invitationsRepository.getById(invitationDto.getId());
      InvitationsStatusEntity invitationsStatusEntity = InvitationsStatusEntity.builder()
          .id(UUID.randomUUID())
          .invitationsEntity(invitation)
          .insert_date(ZonedDateTime.now())
          .dictInvitationsStatusEntity(status)
          .build();

      invitationsStatusRepository.save(invitationsStatusEntity);
      log.info(
          "add invitation Status with id: {}, invitation_id: {}, status: APPROVED",
          invitationsStatusEntity.getId(),
          invitation.getId());
    }
    return null;
  }

  @Transactional
  public String rejectInvitation(InvitationDto invitationDto, Long userId) {
    var checkIfInvitationIsAddressToUser = checkIfInvitationIsAddressToUser(invitationDto.getInviteeId(), userId);
    var checkIfLastStatusIsSent = checkIfLastStatusIsSent(invitationDto);
    if (checkIfInvitationIsAddressToUser &&
        checkIfLastStatusIsSent) {
      DictInvitationsStatusEntity status = dictInvitationsStatusRepository.getByName(REJECTED);
      InvitationsEntity invitation = invitationsRepository.getById(invitationDto.getId());
      InvitationsStatusEntity invitationsStatusEntity = InvitationsStatusEntity.builder()
          .id(UUID.randomUUID())
          .invitationsEntity(invitation)
          .insert_date(ZonedDateTime.now())
          .dictInvitationsStatusEntity(status)
          .build();

      invitationsStatusRepository.save(invitationsStatusEntity);
      log.info(
          "add invitation Status with id: {}, invitation_id: {}, status: REJECTED",
          invitationsStatusEntity.getId(),
          invitation.getId());
    }
    return null;
  }

  private boolean checkIfLastStatusIsSent(InvitationDto invitationDto) {
    UUID invitationId = invitationDto.getId();
    return SENT.equals(invitationsStatusRepository.getLastStatus(invitationId));
  }

  private boolean checkIfInvitationIsAddressToUser(long inviteeId, Long userId) {
    return inviteeId == userId;
  }
}
