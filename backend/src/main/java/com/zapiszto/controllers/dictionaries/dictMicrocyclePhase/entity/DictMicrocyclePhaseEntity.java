package com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.entity;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhaseBasic.entity.DictMicrocyclePhaseBasicEntity;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.entity.DictMicrocyclePhasePerUserEntity;
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
@Table(name = "dict_microcycle_phase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictMicrocyclePhaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne
  @JoinColumn(name = "dict_microcycle_phase_basic_id", referencedColumnName = "id")
  DictMicrocyclePhaseBasicEntity dictMicrocyclePhaseBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_microcycle_phase_per_user_id", referencedColumnName = "id")
  DictMicrocyclePhasePerUserEntity dictMicrocyclePhasePerUserEntity;
}