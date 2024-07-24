package com.zapiszto.controllers.program.clientProgramView.dto;

import java.util.List;

public record ClientProgramMicrocycleDto(int orderId,
                                         List<ClientProgramSessionDto> sessions) {
}
