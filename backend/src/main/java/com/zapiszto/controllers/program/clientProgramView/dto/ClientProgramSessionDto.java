package com.zapiszto.controllers.program.clientProgramView.dto;

import java.util.List;

public record ClientProgramSessionDto(int orderId,
                                      List<ClientProgramExerciseDto> exercises) {
}
