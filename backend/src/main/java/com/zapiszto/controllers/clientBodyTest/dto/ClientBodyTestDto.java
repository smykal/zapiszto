package com.zapiszto.controllers.clientBodyTest.dto;

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
public class ClientBodyTestDto {
  UUID id;
  int dict_body_test_id;
  String dict;
  UUID dict_id;
  String name;
  String result;
  String description;
  String purpose;

}
