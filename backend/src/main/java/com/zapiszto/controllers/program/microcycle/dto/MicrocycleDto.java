package com.zapiszto.controllers.program.microcycle.dto;

import java.util.UUID;

public record MicrocycleDto(
    UUID id,
    UUID mesocycleId,
    int orderId,
    boolean share
) {}
