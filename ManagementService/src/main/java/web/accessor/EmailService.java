package web.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web.dto.response.email.EmailCreateDto;
import web.dto.response.ResponseDto;

import java.util.UUID;

@FeignClient(name = "email-service")
@Component
public interface EmailService {

    @PostMapping("api/v1/email/create")
    ResponseDto createEmail(final @RequestBody EmailCreateDto dto);

    @GetMapping("api/v1/email/get/{id}")
    ResponseDto getEmail(final @PathVariable UUID id);
}
