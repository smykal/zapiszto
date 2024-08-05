package com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestBasic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "dict_body_test_basic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictBodyTestBasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  UUID id;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "name")
  HashMap<String, String> name;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "description")
  HashMap<String, String> description;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "purpose")
  HashMap<String, String> purpose;
}
