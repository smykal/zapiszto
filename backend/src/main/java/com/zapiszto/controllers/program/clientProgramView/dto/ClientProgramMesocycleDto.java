package com.zapiszto.controllers.program.clientProgramView.dto;

import java.util.List;

public record ClientProgramMesocycleDto(int orderId,
                                        List<ClientProgramMicrocycleDto> microcycles) {
}
