package web.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.dto.response.ResponseDto;

import java.util.UUID;

@FeignClient(name = "email-service")
@Component
public interface EmailService {

    @GetMapping("api/v1/email/get-name/{id}")
    ResponseDto getEmail(final @PathVariable UUID id);
}
