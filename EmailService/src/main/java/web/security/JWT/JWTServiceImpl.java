package web.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${auth.jwt.secret}")
    private String SECRET;

    public DecodedJWT validate(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

    }
}