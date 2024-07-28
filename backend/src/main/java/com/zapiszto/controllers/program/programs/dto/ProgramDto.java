package com.zapiszto.controllers.program.programs.dto;

import java.util.UUID;

public record ProgramDto(
    UUID id,
    String programName,
    String createDate,
    Long createdBy,
    String clientName
) {}
