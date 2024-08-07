package com.zapiszto.controllers.goals.entity;

import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import com.zapiszto.controllers.dictionaries.dictBodyTest.entity.DictBodyTestEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.entity.DictUnitsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GoalEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
  ClientEntity clientEntity;

  @Column(name = "client_id", nullable = false)
  UUID clientId;

  @ManyToOne
  @JoinColumn(name = "dict_body_param_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictBodyParamsEntity dictBodyParamsEntity;

  @Column(name = "dict_body_param_id")
  Integer dictBodyParamsId;

  @ManyToOne
  @JoinColumn(name = "dict_body_test_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictBodyTestEntity dictBodyTestEntity;

  @Column(name = "dict_body_test_id")
  UUID dictBodyTestId;

  @ManyToOne
  @JoinColumn(name = "dict_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictUnitsEntity dictUnitsEntity;

  @Column(name = "dict_unit_id")
  UUID dictUnitsId;

  @Column(name = "action")
  String action;

  @Column(name = "value")
  String value;

  @Column(name = "goal_date")
  LocalDate goalDate;

  @Column(name = "insert_date")
  ZonedDateTime insertDate;
}
