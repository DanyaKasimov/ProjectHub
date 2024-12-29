package web.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DeletionRequestService {

    void requestDeletion(UUID entityId, String entityType);

    void cancelDeletion(UUID entityId, String entityType);
}
