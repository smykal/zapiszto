package com.zapiszto.utilities.emailSender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MailDto(
    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    String to,

    @NotBlank(message = "Subject email is required")
    String subject,

    @NotBlank(message = "Email body is required")
    String body) {
}
