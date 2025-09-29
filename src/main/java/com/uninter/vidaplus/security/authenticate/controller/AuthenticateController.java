package com.uninter.vidaplus.security.authenticate.controller;

import com.uninter.vidaplus.security.authenticate.controller.json.request.LoginRequestJson;
import com.uninter.vidaplus.security.authenticate.controller.json.response.LoginResponseJson;
import com.uninter.vidaplus.security.authenticate.usecase.AuthenticateUserUseCase;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseJson> login(@RequestBody @Valid LoginRequestJson loginRequestJson) {
        String token = authenticateUserUseCase.authenticate(loginRequestJson);
        return ResponseEntity.ok(new LoginResponseJson(token));
    }
}
