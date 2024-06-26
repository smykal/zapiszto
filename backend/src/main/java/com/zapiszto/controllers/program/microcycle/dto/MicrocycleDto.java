package com.zapiszto.controllers.program.microcycle.dto;

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
public class MicrocycleDto {
  String id;
  String mesocycleId;
  int orderId;
  String dictType;
  int dictId;
  String dictName;


}
