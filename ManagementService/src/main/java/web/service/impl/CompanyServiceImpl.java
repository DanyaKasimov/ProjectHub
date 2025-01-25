package web.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.dto.request.CompanyDto;
import web.exception.InvalidDataException;
import web.exception.NoDataFoundException;
import web.model.Company;
import web.repositories.CompanyRepository;
import web.service.CompanyService;
import web.service.DeletionRequestService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final DeletionRequestService deletionRequestService;


    @Override
    @Transactional
    public UUID saveCompany(final CompanyDto dto) {
        if (companyRepository.existsCompanyByInn(dto.getInn())) {
            throw new InvalidDataException(String.format("ИНН (%s) уже зарегистрирован.", dto.getInn()));
        }

        if (companyRepository.existsCompanyByDomain(dto.getDomain().toLowerCase())) {
            throw new InvalidDataException(String.format("Домен (%s) уже зарегистрирован.", dto.getDomain()));
        }

        val domain = dto.getDomain().toLowerCase();

        Company company = companyRepository.save(
                Company.builder()
                        .name(dto.getName())
                        .domain(domain)
                        .inn(dto.getInn())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return company.getId();
    }


    @Override
    @Transactional
    public void deleteCompany(final UUID id) {
        findById(id);
        companyRepository.deleteById(id);
    }


    @Override
    public String getDomainById(final UUID id) {
        return companyRepository.getDomainById(id).orElseThrow(
                () -> new NoDataFoundException(String.format("Компания с ID = (%s) не найдена.", id))
        );
    }

    @Override
    public Company findById(final UUID id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new NoDataFoundException(String.format("Компания с ID = (%s) не найдена.", id)));
    }

    @Override
    public void requestCompanyDeletion(final UUID id) {
        findById(id);
        deletionRequestService.requestDeletion(id, "COMPANY");
    }

    @Override
    public void cancelCompanyDeletion(final UUID id) {
        findById(id);
        deletionRequestService.cancelDeletion(id, "COMPANY");
    }
}
