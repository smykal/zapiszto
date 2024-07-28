package com.zapiszto.controllers.account.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
    @NotBlank
    String oldPassword,
    @NotBlank
    String newPassword
) {
}
