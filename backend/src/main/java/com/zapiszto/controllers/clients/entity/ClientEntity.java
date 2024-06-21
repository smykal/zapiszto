package com.zapiszto.controllers.clients.entity;

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
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "trainer_id", referencedColumnName = "id", insertable = false, updatable = false)
  User trainerEntity;

  @Column(name = "trainer_id", nullable = false)
  Long trainerId;

  @Column(name = "client_name")
  String clientName;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  User userEntity;

  @Column(name = "user_id", nullable = true)
  Long userId;
}
