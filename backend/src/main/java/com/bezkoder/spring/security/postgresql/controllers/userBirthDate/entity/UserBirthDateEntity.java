package com.bezkoder.spring.security.postgresql.controllers.userBirthDate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_birthdate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBirthDateEntity {

  @Id
  @Column(name = "user_id")
  private long userId;

  @Column(name = "birthdate")
  private Date birthdate;
}
