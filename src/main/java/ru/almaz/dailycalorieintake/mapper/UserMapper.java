package ru.almaz.dailycalorieintake.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.almaz.dailycalorieintake.dto.RegistrationRequest;
import ru.almaz.dailycalorieintake.dto.UserInfo;
import ru.almaz.dailycalorieintake.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUser(RegistrationRequest registrationRequest);

    UserInfo toUserInfo(User user);
}
