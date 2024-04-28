package com.zapiszto.controllers.exercises.entity;

import com.zapiszto.controllers.dictExercises.entity.DictExercisesEntity;
import com.zapiszto.controllers.dictQuantityType.entity.DictQuantityTypeEntity;
import com.zapiszto.controllers.dictUnits.entity.DictUnitsEntity;
import com.zapiszto.controllers.trainings.entity.TrainingEntity;
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
  int id;

  @ManyToOne
  @JoinColumn(name = "training_id", referencedColumnName = "id", insertable = false, updatable = false)
  TrainingEntity trainingEntity;

  @Column(name = "training_id", nullable = false)
  int trainingId;

  @ManyToOne
  @JoinColumn(name = "dict_exercise_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictExercisesEntity dictExercisesEntity;

  @Column(name = "dict_exercise_id", nullable = false)
  int dictExerciseId;

  @Column(name = "quantity", nullable = false)
  int quantity;

  @ManyToOne
  @JoinColumn(name = "dict_quantity_type_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictQuantityTypeEntity dictQuantityTypeEntity;

  @Column(name = "dict_quantity_type_id", nullable = false)
  int dictQuantityTypeId;

  @Column(name = "volume", nullable = false)
  int volume;

  @ManyToOne
  @JoinColumn(name = "dict_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
  DictUnitsEntity dictUnitsEntity;

  @Column(name = "dict_unit_id", nullable = false)
  int dictUnitId;

  @Column(name = "notes")
  String notes;

}
