package com.zapiszto.controllers.dictionaries.dictCategory.entity;

import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryBasic.entity.DictCategoryBasicEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
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
  long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_category_basic_id", referencedColumnName = "id")
  DictCategoryBasicEntity dictCategoryBasicEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dict_category_per_user_id", referencedColumnName = "id")
  DictCategoryPerUserEntity dictCategoryPerUserEntity;
}
