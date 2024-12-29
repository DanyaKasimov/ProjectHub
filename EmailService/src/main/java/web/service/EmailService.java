package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;

import java.util.UUID;

@Service
public interface EmailService {

    UUID createEmail(final EmailCreateDto dto);
}
