package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Company;
import web.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    boolean existsUserById(UUID id);

    boolean existsByEmail(String email);

    @Query("select u.id from User u where u.company = :company")
    List<UUID> findAllByCompany(@Param("company") Company company);

    Optional<User> findByUsername(String username);
}
