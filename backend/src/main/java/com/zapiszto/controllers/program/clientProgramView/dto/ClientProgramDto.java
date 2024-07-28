package com.zapiszto.controllers.program.clientProgramView.dto;

import java.util.List;

public record ClientProgramDto(String name,
                               List<ClientProgramMacrocycleDto> macrocycles) {
}
