package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import web.accessor.ManagementService;
import web.dto.request.InfoDto;
import web.exception.NoDataFoundException;
import web.model.Info;
import web.repositories.specifications.InfoRepository;
import web.service.InfoService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;

    private final ManagementService managementService;

    @Override
    public Info addInfo(final InfoDto infoDto) {
        log.info("InfoService: добавление информации о сотруднике.");

        val user = managementService.getEmployee(infoDto.getUserId()).getResult();
        if (user == null) {
            throw new NoDataFoundException("Пользователь не найден.");
        }

        return infoRepository.save(
                Info.builder()
                        .address(infoDto.getAddress())
                        .birthday(infoDto.getBirthday())
                        .phoneNumber(infoDto.getPhoneNumber())
                        .userId(infoDto.getUserId())
                        .updatedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

    }
}
