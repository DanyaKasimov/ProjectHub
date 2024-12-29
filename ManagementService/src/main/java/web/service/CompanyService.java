package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.CompanyDto;
import web.model.Company;

import java.util.UUID;

@Service
public interface CompanyService {

    UUID saveCompany(final CompanyDto dto);

    String getDomainById(final UUID id);

    Company findById(final UUID id);

    void requestCompanyDeletion(UUID id);

    void cancelCompanyDeletion(UUID id);
}
