package com.zapiszto.controllers.dictionaries.dictMesocyclePhase.dto;

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
public class DictMesocyclePhaseDto {
  int id;
  String dict;
  long dict_id;
  String name;
  String shortcut;
}
