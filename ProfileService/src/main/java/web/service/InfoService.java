package web.service;


import org.springframework.stereotype.Service;
import web.dto.request.InfoDto;
import web.model.Info;

@Service
public interface InfoService {

    Info addInfo(final InfoDto infoDto);
}
