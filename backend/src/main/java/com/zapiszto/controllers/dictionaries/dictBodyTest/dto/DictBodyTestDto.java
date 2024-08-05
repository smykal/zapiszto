package com.zapiszto.controllers.dictionaries.dictBodyTest.dto;

import java.util.UUID;
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
  UUID id;
  String dict;
  String dict_id;
  String name;
  String description;
  String purpose;
}
