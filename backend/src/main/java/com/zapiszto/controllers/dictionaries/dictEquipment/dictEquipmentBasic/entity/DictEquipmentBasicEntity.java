package com.zapiszto.controllers.dictionaries.dictEquipment.dictEquipmentBasic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "dict_equipment_basic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictEquipmentBasicEntity {
  @Id
  @GeneratedValue
  @Column(name = "id")
  UUID id;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "name")
  HashMap<String, String> name;

}
