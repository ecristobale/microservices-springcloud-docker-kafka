package com.ecristobale.authserver.services;

import com.ecristobale.authserver.dtos.TokenDto;
import com.ecristobale.authserver.dtos.UserDto;

public interface AuthService {

    TokenDto login(UserDto userDto);
    TokenDto validateToken(TokenDto token);
}
