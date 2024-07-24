package com.zapiszto.controllers.program.clientProgramView.dto;

public record ClientProgramExerciseDto(
    int orderId,
    String purpose,
    String category,
    String exercise,
    Float weight,
    String weightUnit,
    Integer repetitions,
    String repetitionsUnit
) {
}
