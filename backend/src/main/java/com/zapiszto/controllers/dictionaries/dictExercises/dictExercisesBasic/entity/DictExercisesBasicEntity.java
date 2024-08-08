package com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.entity;

import com.zapiszto.controllers.dictionaries.dictCategory.entity.DictCategoryEntity;
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
import java.util.HashMap;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "dict_exercises_basic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictExercisesBasicEntity {

  @Id
  @GeneratedValue
  @Column(name = "id")
  UUID id;

  @JdbcTypeCode(SqlTypes.JSON)
  HashMap<String, String> name;

  @ManyToOne
  @JoinColumn(name = "dict_category_id", referencedColumnName = "id")
  DictCategoryEntity dictCategoryEntity;

}
