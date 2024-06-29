package com.zapiszto.controllers.dictionaries.dictExercises.entity;

import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "dict_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictExercisesEntity {

  @Id
  @SequenceGenerator(name = "dict_exercises_id_seq", sequenceName ="dict_exercises_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dict_exercises_id_seq")
  @Column(name = "id")
  int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_exercises_basic_id", referencedColumnName = "id")
  DictExercisesBasicEntity dictExercisesBasicEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_exercises_per_user_id", referencedColumnName = "id")
  DictExercisesPerUserEntity dictExercisesPerUserEntity;
}
