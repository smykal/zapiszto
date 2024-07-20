package com.zapiszto.controllers.exercises.entity;

import com.zapiszto.controllers.dictionaries.dictEquipment.entity.DictEquipmentEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictionaries.dictSessionPart.entity.DictSessionPartEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.controllers.program.sessions.entity.SessionEntity;
import com.zapiszto.controllers.trainings.entity.TrainingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "training_id", referencedColumnName = "id", insertable = false, updatable = false)
  TrainingEntity trainingEntity;

  @Column(name = "training_id", nullable = true)
  Integer trainingId;

  @ManyToOne
  @JoinColumn(name = "session_id", referencedColumnName = "id", insertable = false, updatable = false)
  SessionEntity sessionEntity;

  @Column(name = "session_id", nullable = true)
  UUID sessionId;

  @ManyToOne
  @JoinColumn(name = "dict_exercise_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictExercisesEntity dictExercisesEntity;

  @Column(name = "dict_exercise_id", nullable = false)
  UUID dictExerciseId;

  @Column(name = "quantity", nullable = false)
  int quantity;

  @ManyToOne
  @JoinColumn(name = "dict_quantity_type_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictQuantityTypeEntity dictQuantityTypeEntity;

  @Column(name = "dict_quantity_type_id", nullable = false)
  UUID dictQuantityTypeId;

  @Column(name = "volume", nullable = false)
  Float volume;

  @ManyToOne
  @JoinColumn(name = "dict_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictUnitsEntity dictUnitsEntity;

  @Column(name = "dict_unit_id", nullable = false)
  UUID dictUnitId;

  @Column(name = "notes")
  String notes;

  @Column(name = "order_number")
  int orderNumber;

  @Column(name = "rest_time")
  Integer restTime;

  @Column(name = "tempo")
  String tempo;

  @ManyToOne
  @JoinColumn(name = "dict_session_part_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictSessionPartEntity dictSessionPartEntity;

  @Column(name = "dict_session_part_id")
  UUID dictSessionPartId;

  @Column(name = "sets")
  int sets;

  @ManyToOne
  @JoinColumn(name = "dict_equipment_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictEquipmentEntity dictEquipmentEntity;

  @Column(name = "dict_equipment_id")
  UUID dictEquipmentId;

  @Column(name = "equipment_attribute")
  String equipmentAttribute;

  @Column(name = "weight_per_side", precision = 2)
  Float weightPerSide;

}
