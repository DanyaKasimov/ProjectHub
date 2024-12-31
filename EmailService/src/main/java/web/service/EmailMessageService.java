package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.EmailMessageDto;

@Service
public interface EmailMessageService {

    void sendWorkEmail(final EmailMessageDto dto);
}
