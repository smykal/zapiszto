package com.zapiszto.controllers.dictionaries.dictEquipment.entity;

import com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentBasic.entity.DictEquipmentBasicEntity;
import com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentPerUser.entity.DictEquipmentPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesBasic.entity.DictExercisesBasicEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "dict_equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictEquipmentEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "dict_equipment_basic_id", referencedColumnName = "id")
  DictEquipmentBasicEntity dictEquipmentBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_equipment_per_user_id", referencedColumnName = "id")
  DictEquipmentPerUserEntity dictEquipmentPerUserEntity;
}
