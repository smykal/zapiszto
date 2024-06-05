package com.zapiszto.controllers.dictionaries.dictBodyParams.dto;

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
public class NewDictBodyParamDto {
  private String name;
  private String description;
}
