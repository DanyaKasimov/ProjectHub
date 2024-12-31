package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Email;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {

    boolean existsByName(String name);

    Optional<Email> findByName(String name);
}