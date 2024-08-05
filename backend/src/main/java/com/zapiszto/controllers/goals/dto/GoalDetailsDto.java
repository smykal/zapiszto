package com.zapiszto.controllers.goals.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

public record GoalDetailsDto (
  UUID clientId,
  String bodyParamName,
  String bodyTestName,
  String unitName,
  String action,
  String value) {
}
