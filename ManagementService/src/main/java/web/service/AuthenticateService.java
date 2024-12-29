package web.service;


import web.dto.request.SignInDto;
import web.dto.request.SignUpDto;

public interface AuthenticateService {

    String authenticate(final SignInDto userDto);

}
