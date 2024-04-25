package com.zapiszto.controllers.bodyParams.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum BmiCategory {
  STARVATION(new double[]{0, 16}, "Wygłodzenie"),
  EMACIATION(new double[]{16, 17}, "Wychudzenie"),
  UNDERWEIGHT(new double[]{17, 18.5}, "Niedowaga"),
  CORRECT_WEIGHT(new double[]{18.5, 25}, "Prawidłowa waga"),
  OVERWEIGHT(new double[]{25, 30}, "Nadwaga"),
  OBESITY_1st_STAGE(new double[]{30, 35}, "Otyłość 1 stopnia"),
  OBESITY_2st_STAGE(new double[]{35, 40}, "Otyłość 2 stopnia"),
  OBESITY_3st_STAGE(new double[]{40, Double.MAX_VALUE}, "Otyłość 3 stopnia");

  double[] range;
  String descriptionPL;
}