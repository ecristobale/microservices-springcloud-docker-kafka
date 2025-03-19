package com.ecristobale.auth_server.services;

import com.ecristobale.auth_server.dtos.TokenDto;
import com.ecristobale.auth_server.dtos.UserDto;

public interface AuthService {

    TokenDto login(UserDto userDto);
    TokenDto validateToken(TokenDto token);
}
