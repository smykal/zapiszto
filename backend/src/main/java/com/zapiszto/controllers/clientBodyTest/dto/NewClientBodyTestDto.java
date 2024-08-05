package com.zapiszto.controllers.clientBodyTest.dto;

import java.util.UUID;

public record NewClientBodyTestDto(
    UUID id,
    UUID clientId,
    UUID dictBodyTestId,
    String result) {}
