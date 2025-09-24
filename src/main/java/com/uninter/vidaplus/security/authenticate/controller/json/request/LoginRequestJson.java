package com.uninter.vidaplus.security.authenticate.controller.json.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestJson(
        @NotBlank(message = "O email deve ser preenchido")
        String email,
        @NotBlank(message = "A senha deve ser preenchida")
        String password) {}
