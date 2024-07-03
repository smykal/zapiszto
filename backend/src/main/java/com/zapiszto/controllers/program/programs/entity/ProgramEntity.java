package com.zapiszto.controllers.program.programs.entity;

import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.program.macrocycle.entity.MacrocycleEntity;
import com.zapiszto.controllers.program.programsDetails.entity.ProgramDetailsEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramEntity {
  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "trainer_id", referencedColumnName = "id", insertable = false, updatable = false)
  User trainerEntity;

  @Column(name = "trainer_id", nullable = false)
  Long trainerId;

  @Column(name = "create_date")
  ZonedDateTime insert_date;

  @Column(name = "name")
  String name;

  @OneToMany(mappedBy = "programEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  List<MacrocycleEntity> macrocycles;

  @OneToMany(mappedBy = "programEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  List<ProgramDetailsEntity> programDetails;
}
