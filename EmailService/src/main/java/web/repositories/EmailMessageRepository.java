package web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.EmailMessage;

import java.util.UUID;

@Repository
public interface EmailMessageRepository extends JpaRepository<EmailMessage, UUID> {

    Page<EmailMessage> findAllByToId(UUID id, Pageable pageable);
}