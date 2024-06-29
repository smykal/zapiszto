package com.zapiszto.controllers.program.microcycle.entity;

import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.entity.DictMicrocyclePhaseEntity;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "microcycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MicrocycleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mesocycle_id", referencedColumnName = "id", insertable = false, updatable = false)
  MesocycleEntity mesocycleEntity;

  @Column(name = "mesocycle_id", nullable = false)
  UUID mesocycleId;

  @Column(name = "order_id", nullable = false)
  int orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_microcycle_phase_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictMicrocyclePhaseEntity dictMicrocyclePhase;

  @Column(name = "dict_microcycle_phase_id", nullable = false)
  int dictMicrocyclePhaseId;

}
