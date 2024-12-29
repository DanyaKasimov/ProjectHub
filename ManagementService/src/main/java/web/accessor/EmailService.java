package web.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import web.dto.response.EmailCreateDto;
import web.dto.response.ResponseDto;

@FeignClient("email-service")
@Component
public interface EmailService {

    @PostMapping("api/v1/email/create")
    ResponseDto createEmail(final @RequestBody EmailCreateDto dto);
}
