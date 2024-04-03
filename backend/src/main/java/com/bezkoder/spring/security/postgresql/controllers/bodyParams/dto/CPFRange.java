package com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CPFRange {
  private final double min;
  private final double max;

  public CPFRange(
      double min,
      double max
  ) {
    this.min = min;
    this.max = max;
  }
}
