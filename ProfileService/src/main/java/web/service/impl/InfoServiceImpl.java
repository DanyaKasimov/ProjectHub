package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.accessor.ManagementService;
import web.dto.request.InfoDto;
import web.exception.NoDataFoundException;
import web.model.Info;
import web.repositories.specifications.InfoRepository;
import web.service.InfoService;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Override
    @CacheEvict(value = "info.data", key = "#infoDto.id")
    public Info updateInfo(final InfoDto infoDto) {
        log.info("InfoService: обновление информации о сотруднике.");

        val user = managementService.getEmployee(infoDto.getUserId()).getResult();
        if (user == null) {
            throw new NoDataFoundException("Пользователь не найден.");
        }

        val info = infoRepository.findById(infoDto.getId()).orElseThrow(
                () -> new NoDataFoundException("Данные не найдены.")
        );

        info.setUserId(info.getUserId());
        info.setPhoneNumber(infoDto.getPhoneNumber());
        info.setBirthday(infoDto.getBirthday());
        info.setAddress(infoDto.getAddress());
        info.setCreatedAt(info.getCreatedAt());
        info.setUpdatedAt(LocalDateTime.now());

        return infoRepository.save(info);
    }


    @Override
    @Cacheable(value = "info.data", key = "#id")
    public Info findById(final UUID id) {
        return infoRepository.findById(id).orElseThrow(
                () -> new NoDataFoundException("Данные не найдены.")
        );
    }
}
