package com.ecristobale.authserver.services;

import com.ecristobale.authserver.dtos.TokenDto;
import com.ecristobale.authserver.dtos.UserDto;
import com.ecristobale.authserver.entities.UserEntity;
import com.ecristobale.authserver.helpers.JwtHelper;
import com.ecristobale.authserver.repositories.UserRepository;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    private static final String USER_EXCEPTION_MSG = "Error to auth user";

    // 1st method: Validating the password introduced in Login
    //             401 Unauthorized status code is returned if the password is not valid
    //             Creating signed token
    @Override
    public TokenDto login(UserDto userDto) {
        final var userFromDB = this.userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG));
        this.validPassword(userDto, userFromDB);

        return TokenDto.builder().accessToken(this.jwtHelper.createToken(userFromDB.getUsername())).build();
    }

    @Override
    public TokenDto validateToken(TokenDto token) {
        if (this.jwtHelper.validateToken(token.getAccessToken())) {
            return TokenDto.builder().accessToken(token.getAccessToken()).build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
    }

    // 1st: Method for validating the password introduced in Login
    // 401 Unauthorized status code is returned if the password is not valid
    private void validPassword(UserDto userDto, UserEntity userEntity) {
        if(!this.passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
