package web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.dto.request.EmailMessageDto;
import web.model.EmailMessage;

import java.util.UUID;

@Service
public interface EmailMessageService {

    void sendWorkEmail(final EmailMessageDto dto);

    Page<EmailMessage> getEmailList(final UUID id, final Pageable pageable);
}
