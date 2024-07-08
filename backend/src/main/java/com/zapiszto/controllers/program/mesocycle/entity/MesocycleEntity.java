package com.zapiszto.controllers.program.mesocycle.entity;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity.DictMesocyclePhaseEntity;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "mesocycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MesocycleEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "macrocycle_id", referencedColumnName = "id", insertable = false, updatable = false)
  MacrocycleEntity macrocycleEntity;

  @Column(name = "macrocycle_id", nullable = false)
  UUID macrocycleId;

  @Column(name = "duration")
  int duration;

  @Column(name = "order_id")
  int orderId;

  @Column(name = "comments")
  String comments;

  @Column(name = "label")
  String label;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_mesocycle_phase_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictMesocyclePhaseEntity dictMesocyclePhaseEntity;

  @Column(name = "dict_mesocycle_phase_id", nullable = false)
  int dictMesocyclePhaseId;

  @OneToMany(mappedBy = "mesocycleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  List<MicrocycleEntity> microcycles;
}
