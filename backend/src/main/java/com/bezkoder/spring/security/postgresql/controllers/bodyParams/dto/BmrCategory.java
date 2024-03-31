package com.bezkoder.spring.security.postgresql.controllers.bodyParams.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum BmrCategory {
  SEDENTARY(1.2, "Very low activity level", "Bardzo niski poziom aktywności"),
  LIGHTLY_ACTIVE(1.375, "Low activity level", "Niski poziom aktywności"),
  MODERATELY_ACTIVE(1.55, "Moderate activity level", "Umiarkowany poziom aktywności"),
  VERY_ACTIVE(1.725, "High activity level", "Wysoki poziom aktywności"),
  EXTRA_ACTIVE(1.9, "Very high activity level", "Bardzo wysoki poziom aktywności");

  private final double activityCoefficient;
  private final String descriptionEN;
  private final String descriptionPL;
}
