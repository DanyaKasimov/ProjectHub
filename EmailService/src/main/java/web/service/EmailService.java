package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;
import web.dto.response.EmailSendDto;
import web.dto.response.EmailDto;

@Service
public interface EmailService {

    EmailDto createEmail(final EmailCreateDto dto);

    void sendEmail(final EmailSendDto dto);
}
