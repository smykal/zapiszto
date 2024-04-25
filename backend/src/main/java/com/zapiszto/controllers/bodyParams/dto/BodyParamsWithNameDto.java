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
public class BodyParamsWithNameDto {
  private String dict_body_params_name;
  private String value;
  private int userId;
}
