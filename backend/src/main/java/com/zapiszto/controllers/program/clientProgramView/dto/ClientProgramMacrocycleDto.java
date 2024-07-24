package com.zapiszto.controllers.program.clientProgramView.dto;

import java.util.List;
import java.util.UUID;

public record ClientProgramMacrocycleDto(UUID id,
                                         List<ClientProgramMesocycleDto> mesocycles) {
}
