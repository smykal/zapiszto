package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhaseBasic.entity.DictMesocyclePhaseBasicEntity;
import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dictMesocyclePhasePerUser.entity.DictMesocyclePhasePerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "dict_mesocycle_phase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictMesocyclePhaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne
  @JoinColumn(name = "dict_mesocycle_phase_basic_id", referencedColumnName = "id")
  DictMesocyclePhaseBasicEntity dictMesocyclePhaseBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_mesocycle_phase_per_user_id", referencedColumnName = "id")
  DictMesocyclePhasePerUserEntity dictMesocyclePhasePerUserEntity;
}