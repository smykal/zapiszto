package com.zapiszto.controllers.dictQuantityType.entity;

import com.zapiszto.controllers.dictQuantityType.dictQuantityTypeBasic.entity.DictQuantityTypeBasicEntity;
import com.zapiszto.controllers.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
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
@Table(name = "dict_quantity_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictQuantityTypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne
  @JoinColumn(name = "dict_quantity_type_basic_id", referencedColumnName = "id")
  DictQuantityTypeBasicEntity dictQuantityTypeBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_quantity_type_per_user_id", referencedColumnName = "id")
  DictQuantityTypePerUserEntity dictQuantityTypePerUserEntity;
}
