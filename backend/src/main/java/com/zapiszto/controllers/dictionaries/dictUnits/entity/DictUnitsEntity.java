package com.zapiszto.controllers.dictionaries.dictUnits.entity;

import com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsBasic.entity.DictUnitsBasicEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsPerUser.entity.DictUnitsPerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "dict_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictUnitsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_units_basic_id", referencedColumnName = "id")
  DictUnitsBasicEntity dictUnitsBasicEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_units_per_user_id", referencedColumnName = "id")
  DictUnitsPerUserEntity dictUnitsPerUserEntity;
}
