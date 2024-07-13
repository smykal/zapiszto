package com.zapiszto.controllers.dictionaries.dictQuantityType.entity;

import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypeBasic.entity.DictQuantityTypeBasicEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "dict_quantity_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictQuantityTypeEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_quantity_type_basic_id", referencedColumnName = "id")
  DictQuantityTypeBasicEntity dictQuantityTypeBasicEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_quantity_type_per_user_id", referencedColumnName = "id")
  DictQuantityTypePerUserEntity dictQuantityTypePerUserEntity;
}
