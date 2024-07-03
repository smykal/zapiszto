package com.zapiszto.controllers.invitations.entity;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.invitations.invitationsStatus.entity.InvitationsStatusEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inviter", referencedColumnName = "id", foreignKey = @ForeignKey(name = "inviter_fk", foreignKeyDefinition = "FOREIGN KEY (inviter) REFERENCES users (id) ON DELETE SET NULL"), insertable = false, updatable = false)
  User inviterEntity;

  @Column(name = "inviter", nullable = false)
  Long inviter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invitee", referencedColumnName = "id", foreignKey = @ForeignKey(name = "invitee_fk", foreignKeyDefinition = "FOREIGN KEY (invitee) REFERENCES users (id) ON DELETE SET NULL"), insertable = false, updatable = false)
  User inviteeEntity;

  @Column(name = "invitee", nullable = false)
  Long invitee;

  @OneToMany(mappedBy = "invitationsEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<InvitationsStatusEntity> invitationsStatuses = new HashSet<>();
}
