package com.zapiszto.controllers.invitations.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvitationDto {

  UUID id;
  long inviterId;
  String inviterName;
  String inviterEmail;
  long inviteeId;
  String inviteeName;
  String inviteeEmail;
  String status;

}
