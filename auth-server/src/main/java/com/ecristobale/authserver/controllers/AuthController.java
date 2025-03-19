package com.ecristobale.authserver.controllers;

import com.ecristobale.authserver.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
}
