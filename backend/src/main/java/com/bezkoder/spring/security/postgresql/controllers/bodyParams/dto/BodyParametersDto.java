package com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto;

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
public class BodyParametersDto {
  private String kolumna_1;
  private String kolumna_2;
  private int userId;
}
