package com.zapiszto.controllers.dictCategory.entity;

import com.zapiszto.controllers.dictCategory.dictCategoryBasic.entity.DictCategoryBasicEntity;
import com.zapiszto.controllers.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import com.zapiszto.controllers.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import com.zapiszto.controllers.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
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
@Table(name = "dict_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne
  @JoinColumn(name = "dict_category_basic_id", referencedColumnName = "id")
  DictCategoryBasicEntity dictCategoryBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_category_per_user_id", referencedColumnName = "id")
  DictCategoryPerUserEntity dictCategoryPerUserEntity;
}
