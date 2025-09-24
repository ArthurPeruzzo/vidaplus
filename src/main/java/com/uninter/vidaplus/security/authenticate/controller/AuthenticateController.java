package com.uninter.vidaplus.security.authenticate.controller;

import com.uninter.vidaplus.security.authenticate.controller.json.request.LoginRequestJson;
import com.uninter.vidaplus.security.authenticate.controller.json.response.LoginResponseJson;
import com.uninter.vidaplus.security.authenticate.usecase.AuthenticateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public String test() {
        return "oi";
    }

    @GetMapping("/test1")
    public String test1() {
        return "oi1";
    }
}
