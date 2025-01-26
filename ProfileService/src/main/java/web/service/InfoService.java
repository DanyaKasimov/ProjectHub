package web.service;


import org.springframework.stereotype.Service;
import web.dto.request.InfoDto;
import web.dto.response.UserFullData;
import web.model.Info;

import java.util.UUID;

@Service
public interface InfoService {

    Info addInfo(final InfoDto infoDto);

    Info updateInfo(final InfoDto infoDto);

    UUID getCurrentId();

    UserFullData findById(final UUID id);
}
