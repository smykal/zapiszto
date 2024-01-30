package com.bezkoder.spring.security.postgresql.controllers.dictBodyParams.dto;

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
public class DictBodyParamsDto {
  private int id;
  private String name;
  private String description;
}
