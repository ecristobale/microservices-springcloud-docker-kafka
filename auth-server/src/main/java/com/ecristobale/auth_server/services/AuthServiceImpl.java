package com.ecristobale.auth_server.services;

import com.ecristobale.auth_server.dtos.TokenDto;
import com.ecristobale.auth_server.dtos.UserDto;
import com.ecristobale.auth_server.entities.UserEntity;
import com.ecristobale.auth_server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private static final String USER_EXCEPTION_MSG = "Error to auth user";

    @Override
    public TokenDto login(UserDto userDto) {
        return null;
    }

    @Override
    public TokenDto validateToken(TokenDto token) {
        return null;
    }

    // 1st: Method for validating the password introduced in Login
    // 401 Unauthorized status code is returned if the password is not valid
    private void validPassword(UserDto userDto, UserEntity userEntity) {
        if(!this.passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
