package com.zapiszto.controllers.bodyParams.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BodyParamsDto(
    @NotNull
    Integer dict_body_params_id,
    @NotBlank
    String value,
    UUID clientId
) {
}
