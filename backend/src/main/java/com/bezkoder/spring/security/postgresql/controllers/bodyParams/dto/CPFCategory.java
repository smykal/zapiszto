package com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum CPFCategory {

  WEIGHT_REDUCTION(new CPFRange(5, 10), new CPFRange(1.6, 2.2), new CPFRange(0.5, 1.5)),
  WEIGHT_GAIN(new CPFRange(5, 10), new CPFRange(1.4, 2.2), new CPFRange(20, 33));


  private final CPFRange carbohydrate;
  private final CPFRange protein;
  private final CPFRange fat;
}
