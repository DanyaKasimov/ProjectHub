package web.accessor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.dto.response.ResponseDto;

import java.util.UUID;

@FeignClient(name = "management-service")
@Component
public interface ManagementService {

    @GetMapping("api/v1/user-management/get-user/{id}")
    ResponseDto getEmployee(final @PathVariable UUID id);
}