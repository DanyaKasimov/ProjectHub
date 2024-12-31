package web.accessor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final HttpServletRequest httpServletRequest;

    public FeignClientInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwtToken = getJwtFromRequest();
        if (jwtToken != null) {
            log.info("Добавляем JWT токен в заголовок: {}", jwtToken);
            requestTemplate.header(AUTHORIZATION_HEADER, jwtToken);
        } else {
            log.warn("JWT токен отсутствует в заголовке Authorization.");
        }
    }

    private String getJwtFromRequest() {
        String authHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader;
        }
        return null;
    }
}