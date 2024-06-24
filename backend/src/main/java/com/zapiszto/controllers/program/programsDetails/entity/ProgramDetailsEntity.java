package com.zapiszto.controllers.program.programsDetails.entity;

import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import com.zapiszto.controllers.clients.entity.ClientEntity;
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
@Table(name = "programs_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgramDetailsEntity {

  @Id
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
  ProgramEntity programEntity;

  @ManyToOne
  @JoinColumn(name = "assigned_client", referencedColumnName = "id", insertable = false, updatable = false)
  ClientEntity clientEntity;

  @Column(name = "assigned_client", nullable = false)
  UUID assignedClientId;
}