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
public class BodyParamsDto {
  private int dict_body_params_id;
  private String value;
  private int userId;
}
