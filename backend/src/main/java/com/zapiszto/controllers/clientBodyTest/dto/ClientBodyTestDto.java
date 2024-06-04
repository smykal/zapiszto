package com.zapiszto.controllers.clientBodyTest.dto;

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
public class ClientBodyTestDto {
  int id;
  int dict_body_test_id;
  String dict;
  Long dict_id;
  String name;
  String result;
  String description;
  String purpose;

}
