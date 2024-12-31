package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.dto.response.EmailDto;
import web.model.Email;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    EmailDto toDto(Email email);
}