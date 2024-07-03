package com.zapiszto.controllers.program.macrocycle.entity;

import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
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
@Table(name = "macrocycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MacrocycleEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id", referencedColumnName = "id", insertable = false, updatable = false)
  ProgramEntity programEntity;

  @Column(name = "program_id", nullable = false)
  UUID programId;

  @Column(name = "duration")
  int duration;

  @Column(name = "duration_left")
  int durationLeft;

  @OneToMany(mappedBy = "macrocycleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  List<MesocycleEntity> mesocycles;

}
