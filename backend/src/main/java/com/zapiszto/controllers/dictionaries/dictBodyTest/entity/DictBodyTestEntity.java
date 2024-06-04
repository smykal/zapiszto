package com.zapiszto.controllers.dictionaries.dictBodyTest.entity;

import com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestBasic.entity.DictBodyTestBasicEntity;
import com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestPerUser.entity.DictBodyTestPerUserEntity;
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
@Table(name = "dict_body_test")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictBodyTestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @ManyToOne
  @JoinColumn(name = "dict_body_test_basic_id", referencedColumnName = "id")
  DictBodyTestBasicEntity dictBodyTestBasicEntity;

  @ManyToOne
  @JoinColumn(name = "dict_body_test_per_user_id", referencedColumnName = "id")
  DictBodyTestPerUserEntity dictBodyTestPerUserEntity;
}
