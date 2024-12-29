package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.DeletionRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeletionRequestRepository extends JpaRepository<DeletionRequest, UUID> {

    List<DeletionRequest> findByEntityTypeAndCanceled(String entityType, boolean canceled);

    Optional<DeletionRequest> findByEntityIdAndEntityType(UUID entityId, String entityType);
}