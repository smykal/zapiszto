package com.zapiszto.controllers.invitations.invitationsStatus.entity;

import com.zapiszto.controllers.dictBodyParams.entity.DictBodyParamsEntity;
import com.zapiszto.controllers.invitations.dictInvitationsStatus.entity.DictInvitationsStatusEntity;
import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "invitations_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvitationsStatusEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "invitations_id", referencedColumnName = "id")
  InvitationsEntity invitations_id;

  @Column(name = "insert_date")
  ZonedDateTime insert_date;

  @ManyToOne
  @JoinColumn(name = "dict_invitations_status_id", referencedColumnName = "id")
  DictInvitationsStatusEntity dictInvitationsStatusEntity;

  @Column(name = "dict_invitations_status_id", insertable=false, updatable=false)
  Long dictInvitationsStatusId;
}
