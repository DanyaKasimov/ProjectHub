package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.exception.InvalidDataException;
import web.model.DeletionRequest;
import web.repositories.CompanyRepository;
import web.repositories.DeletionRequestRepository;
import web.repositories.UserRepository;
import web.service.DeletionRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class DeletionRequestServiceImpl implements DeletionRequestService {

    private final DeletionRequestRepository deletionRequestRepository;

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    @Transactional
    public void requestDeletion(UUID entityId, String entityType) {
        if (deletionRequestRepository.findByEntityIdAndEntityType(entityId, entityType).isPresent()) {
            throw new InvalidDataException("Запрос на удаление уже существует для этого объекта.");
        }

        deletionRequestRepository.save(DeletionRequest.builder()
                .entityId(entityId)
                .entityType(entityType)
                .requestedAt(LocalDateTime.now())
                .canceled(false)
                .build());
    }

    @Transactional
    public void cancelDeletion(UUID entityId, String entityType) {
        DeletionRequest request = deletionRequestRepository.findByEntityIdAndEntityType(entityId, entityType)
                .orElseThrow(() -> new InvalidDataException("Запрос на удаление для этого объекта не найден."));
        request.setCanceled(true);
        deletionRequestRepository.save(request);
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void processEntityDeletions() {
        processUserDeletions();
        processCompanyDeletions();
    }

    private void processUserDeletions() {
        List<DeletionRequest> userRequests = deletionRequestRepository.findByEntityTypeAndCanceled("USER", false);

        for (DeletionRequest request : userRequests) {
            if (request.getRequestedAt().plusMinutes(1).isBefore(LocalDateTime.now())) {
                userRepository.deleteById(request.getEntityId());
                deletionRequestRepository.delete(request);
                log.info("Пользователи удалены: {}", request.getEntityId());
            }
        }
    }

    private void processCompanyDeletions() {
        List<DeletionRequest> companyRequests = deletionRequestRepository.findByEntityTypeAndCanceled("COMPANY", false);

        for (DeletionRequest request : companyRequests) {
            if (request.getRequestedAt().plusMinutes(1).isBefore(LocalDateTime.now())) {
                companyRepository.deleteById(request.getEntityId());
                deletionRequestRepository.delete(request);
                log.info("Компания удалена: {}", request.getEntityId());
            }
        }
    }
}