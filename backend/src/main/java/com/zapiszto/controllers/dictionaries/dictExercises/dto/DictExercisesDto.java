package com.zapiszto.controllers.dictionaries.dictExercises.dto;

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
public class DictExercisesDto {
  UUID id;
  String dict;
  UUID dict_id;
  String name;
  String category_type;
  long category_id;
  String category_name;
}
