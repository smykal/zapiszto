package com.zapiszto.controllers.program.mesocycle.entity;

import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
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

  @ManyToOne
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
}
