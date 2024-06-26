package com.zapiszto.controllers.bodyParams.dto;

import java.time.ZonedDateTime;
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
public class BasalMetabolicRateDto {
  private double value;
  private ZonedDateTime date;
  private String descriptionEn;
  private String descriptionPl;
}
