package com.zapiszto.controllers.clientBodyTest.dictBodyTest.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DictBodyTestDto {
  Long id;
  String dict;
  Long dict_id;
  String name;
  String description;
  String purpose;
}