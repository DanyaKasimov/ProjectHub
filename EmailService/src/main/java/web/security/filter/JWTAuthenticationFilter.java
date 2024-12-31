package web.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.security.JWT.JWTService;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Извлечение токена из заголовка Authorization
            String token = getTokenFromRequest(request);
            log.info(token);
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                DecodedJWT decodedJWT = jwtService.validate(token);

                // Извлечение данных пользователя
                String username = decodedJWT.getClaim("username").asString();
                String role = decodedJWT.getClaim("role").asString();

                if (username != null) {
                    // Создаем объект Authentication
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, 
                                    Collections.singletonList(new SimpleGrantedAuthority(role)));

                    // Устанавливаем авторизацию в SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            // Обработка ошибок, например, неправильный токен
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response); // Продолжаем цепочку фильтров
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Убираем "Bearer "
        }
        return null;
    }
}