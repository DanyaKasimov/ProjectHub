package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import web.dto.request.SignInDto;
import web.exception.AuthInvalidException;
import web.service.AuthenticateService;
import web.security.JWT.JWTService;
import web.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final UserService userService;

    public String authenticate(final String username, final String password) {
        try {
            Authentication authentication;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateToken(authentication);
        } catch (BadCredentialsException e) {
            throw new AuthInvalidException("Ошибка входа: Неверные учетные данные.");
        }
    }

    @Override
    public String authenticate(final SignInDto userDto) {
        userService.findByUsername(userDto.getUsername());
        return authenticate(userDto.getUsername(), userDto.getPassword());
    }

}
