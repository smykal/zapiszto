package com.zapiszto.controllers.program.periodization.entity;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity.DictMesocyclePhaseEntity;
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
@Table(name = "periodization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeriodizationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @Column(name = "name")
  String name;

  @Column(name = "description")
  String description;

  @Column(name = "duration")
  int duration;

  @Column(name = "order_id")
  int orderId;

  @ManyToOne
  @JoinColumn(name = "dict_mesocycle_phase_id", referencedColumnName = "id")
  DictMesocyclePhaseEntity dictMesocyclePhase;

  @Column(name = "color")
  String color;
}
