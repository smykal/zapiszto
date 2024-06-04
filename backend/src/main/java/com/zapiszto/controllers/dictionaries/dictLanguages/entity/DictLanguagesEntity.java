package com.zapiszto.controllers.dictionaries.dictLanguages.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dict_languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictLanguagesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "code")
  private String code;

  @Column(name = "label")
  private String label;

  @Column(name = "name")
  private String name;

}
