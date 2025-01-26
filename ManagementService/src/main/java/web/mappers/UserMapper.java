package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import web.dto.response.UserDto;
import web.dto.response.UserDataDto;
import web.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "company", source = "user.company")
    @Mapping(target = "email", source = "user.emailId")
    UserDataDto toDtoLite(User user);

    UserDto toDto(User user);

    List<UserDataDto> toDtosLite(List<User> user);

}