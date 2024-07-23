package com.zapiszto.controllers.program.diagrams.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseStatsDto {
  private int orderId;
  private String category;
  private int repetitions;
  private int sets;
}
