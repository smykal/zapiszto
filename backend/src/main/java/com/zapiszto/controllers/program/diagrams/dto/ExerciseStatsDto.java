package com.zapiszto.controllers.program.diagrams.dto;

public record ExerciseStatsDto(
    int orderId,
    String category,
    int repetitions,
    int sets
) {}
