package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;
import web.dto.response.email.EmailSendDto;
import web.dto.response.email.EmailDto;

import java.util.UUID;

@Service
public interface EmailService {

    EmailDto createEmail(final EmailCreateDto dto);

    void sendEmail(final EmailSendDto dto);

    EmailDto findById(final UUID id);

    EmailDto findByName(final String name);
}
