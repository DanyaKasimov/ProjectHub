package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;
import web.dto.response.EmailSendDto;
import web.dto.response.EmailDto;

import java.util.UUID;

@Service
public interface EmailService {

    EmailDto createEmail(final EmailCreateDto dto);

    void sendEmail(final EmailSendDto dto);

    EmailDto findById(final UUID id);
}
