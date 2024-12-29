package web.security.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;


public interface JWTService {

    String generateToken(Authentication authentication);

    DecodedJWT validate(String token);
}
