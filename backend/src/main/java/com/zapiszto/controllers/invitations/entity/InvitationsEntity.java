package com.zapiszto.controllers.invitations.entity;

import com.zapiszto.controllers.account.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "invitations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvitationsEntity {

  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "inviter", referencedColumnName = "id", insertable = false, updatable = false)
  User inviterEntity;

  @Column(name = "inviter", nullable = false)
  Long inviter;

  @ManyToOne
  @JoinColumn(name = "invitee", referencedColumnName = "id", insertable = false, updatable = false)
  User inviteeEntity;

  @Column(name = "invitee", nullable = false)
  Long invitee;
}
