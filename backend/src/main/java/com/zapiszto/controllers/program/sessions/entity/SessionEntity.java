package com.zapiszto.controllers.program.sessions.entity;

import com.zapiszto.controllers.exercises.entity.ExerciseEntity;
import com.zapiszto.controllers.program.microcycle.entity.MicrocycleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
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
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "microcycle_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false )
  MicrocycleEntity microcycleEntity;

  @Column(name = "microcycle_id")
  UUID microcycleId;

  @Column(name = "order_id", nullable = false)
  int orderId;

  @Column(name = "label", length = 20)
  String label;

  @Column(name = "date_time")
  ZonedDateTime dateTime;

  @OneToMany(mappedBy = "sessionEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ExerciseEntity> exercises;
}
