package web.repositories.specifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Info;

import java.util.UUID;

@Repository
public interface InfoRepository  extends JpaRepository<Info, UUID> {
}
