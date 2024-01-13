package com.bezkoder.spring.security.postgresql.controllers.testTable.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestTableDto {
  private String kolumna_1;
  private String kolumna_2;
}
