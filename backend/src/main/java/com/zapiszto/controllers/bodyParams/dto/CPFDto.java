package com.zapiszto.controllers.bodyParams.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CPFDto {
  String activity_level;
  String tittle;
  double kcalMin;
  double kcalMax;
  double carbohydrateMin;
  double carbohydrateMax;
  double proteinMin;
  double proteinMax;
  double fatMin;
  double fatMax;
}
