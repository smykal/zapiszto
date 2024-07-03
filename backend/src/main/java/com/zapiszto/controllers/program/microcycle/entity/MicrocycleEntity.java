package com.zapiszto.controllers.program.microcycle.entity;

import com.zapiszto.controllers.dictionaries.dictMesocyclePhase.entity.DictMesocyclePhaseEntity;
import com.zapiszto.controllers.program.mesocycle.entity.MesocycleEntity;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  @OneToMany(mappedBy = "microcycleEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<SessionEntity> sessions;


}
