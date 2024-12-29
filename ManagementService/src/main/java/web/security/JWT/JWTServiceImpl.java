package web.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import web.security.detail.UserDetailsImpl;

import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${auth.jwt.secret}")
    private String SECRET;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create()
                .withClaim("username", userDetails.getUsername())
                .withClaim("name", userDetails.getName())
                .withClaim("surname", userDetails.getSurname())
                .withClaim("id", String.valueOf(userDetails.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public DecodedJWT validate(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

    }
}
