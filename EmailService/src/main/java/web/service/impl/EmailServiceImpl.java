package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;
import web.exception.InvalidDataException;
import web.model.Email;
import web.repositories.EmailRepository;
import web.service.EmailService;
import web.utils.Generator;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    @Override
    public UUID createEmail(final EmailCreateDto dto) {
        val emailName =
                Generator.generateEmail(dto.getName(), dto.getSurname(), dto.getPatronymic(), dto.getDomain());

        if (emailRepository.existsByName(emailName)) {
            throw new InvalidDataException("Название электронной почты занято. Повторите попытку.");
        }

        val email = emailRepository.saveAndFlush(
                Email.builder()
                        .name(emailName)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return email.getId();
    }
}
