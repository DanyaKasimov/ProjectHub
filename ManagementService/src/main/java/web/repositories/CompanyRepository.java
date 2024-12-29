package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Company;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsCompanyByInn(String inn);

    boolean existsCompanyByDomain(String domain);

    @Query("select c.domain from Company c where c.id = :id")
    Optional<String> getDomainById(@Param("id") UUID id);
}