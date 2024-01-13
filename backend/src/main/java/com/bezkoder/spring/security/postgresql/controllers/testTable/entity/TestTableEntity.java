package com.bezkoder.spring.security.postgresql.controllers.testTable.entity;

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
@Table(name = "test_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestTableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "kolumna_1")
  private String kolumna_1;

  @Column(name = "kolumna_2")
  private String kolumna_2;
}
