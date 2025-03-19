package com.ecristobale.authserver.controllers;

import com.ecristobale.authserver.dtos.TokenDto;
import com.ecristobale.authserver.dtos.UserDto;
import com.ecristobale.authserver.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(this.authService.login(userDto));
    }

    @PostMapping(path = "jwt")
    public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken) {
        return ResponseEntity.ok(
                this.authService.validateToken(TokenDto.builder().accessToken(accessToken).build()));
    }
}
