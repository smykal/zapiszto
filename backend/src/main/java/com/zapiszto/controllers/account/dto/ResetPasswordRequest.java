package com.zapiszto.controllers.account.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
    @NotBlank
    String token,

    @NotBlank
    String newPassword
) {
}
