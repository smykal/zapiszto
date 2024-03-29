package com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum BmiCategory {
  UNDERWEIGHT("BMI < 18.5", "Niedowaga"),
  CORRECT_WEIGHT("18.5 <= BMI < 24.9", "Prawidłowa waga"),
  OVERWEIGHT("25 <= BMI < 29.9", "Nadwaga"),
  OBESITY("BMI >= 30", "Otyłość");

  String range;
  String descriptionPL;
}