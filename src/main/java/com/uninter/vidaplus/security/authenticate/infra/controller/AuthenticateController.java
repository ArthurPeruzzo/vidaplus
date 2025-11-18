package com.uninter.vidaplus.security.authenticate.infra.controller;

import com.uninter.vidaplus.security.authenticate.core.usecase.AuthenticateUserUseCase;
import com.uninter.vidaplus.security.authenticate.infra.controller.dto.LoginInputDto;
import com.uninter.vidaplus.security.authenticate.infra.controller.json.request.LoginRequestJson;
import com.uninter.vidaplus.security.authenticate.infra.controller.json.response.LoginResponseJson;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/authenticate")
public class AuthenticateController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @Operation(
            summary = "Endpoint de login"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseJson> login(@RequestBody @Valid LoginRequestJson loginRequestJson) {
        LoginInputDto loginInputDto = new LoginInputDto(loginRequestJson.email(), loginRequestJson.password());
        String token = authenticateUserUseCase.authenticate(loginInputDto);

        return ResponseEntity.ok(new LoginResponseJson(token));
    }
}
