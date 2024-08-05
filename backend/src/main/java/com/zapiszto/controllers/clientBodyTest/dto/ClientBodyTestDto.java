package com.zapiszto.controllers.clientBodyTest.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

public record ClientBodyTestDto (
  UUID id,
  UUID dict_body_test_id,
  String dict,
  UUID dict_id,
  String name,
  String result,
  String description,
  String purpose) {}
