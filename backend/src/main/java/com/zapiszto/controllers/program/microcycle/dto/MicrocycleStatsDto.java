package com.zapiszto.controllers.program.microcycle.dto;

public record MicrocycleStatsDto(
    String category,
    Long repetitions,
    Long sets
) {}
