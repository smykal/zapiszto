package com.zapiszto.controllers.invitations.serializer;

import com.zapiszto.controllers.common.SerializerCommon;
import com.zapiszto.controllers.invitations.dto.InvitationDto;
import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import com.zapiszto.controllers.invitations.invitationsStatus.entity.InvitationsStatusEntity;

public class InvitationsSerializer implements SerializerCommon {
  public static InvitationDto convert(InvitationsStatusEntity invitationsStatusEntity) {
    InvitationsEntity invitation = invitationsStatusEntity.getInvitations_id();

    return InvitationDto.builder()
        .id(invitation.getId())
        .inviterId(invitation.getInviter())
        .inviterName(invitation.getInviterEntity().getUsername())
        .inviterEmail(invitation.getInviterEntity().getEmail())
        .inviteeId(invitation.getInvitee())
        .inviteeName(invitation.getInviteeEntity().getUsername())
        .inviteeEmail(invitation.getInviteeEntity().getEmail())
        .status(invitationsStatusEntity.getDictInvitationsStatusEntity().getName())
        .build();

  }

  public static InvitationDto setRecivedAndSent(InvitationDto invitationDto, long userId) {
    if (invitationDto.getInviterId() != userId) {
      invitationDto.setStatus(RECIVED);
    }
    return invitationDto;
  }
}
