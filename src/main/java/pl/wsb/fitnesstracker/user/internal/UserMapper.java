package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDtoByEmail;
import pl.wsb.fitnesstracker.user.api.UserDtoOlderThan;
import pl.wsb.fitnesstracker.user.api.UserDtoSimple;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    UserDtoSimple toDtoSimple(User user) {
        return new UserDtoSimple(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    UserDtoByEmail toDtoByEmail(User user) {
        return new UserDtoByEmail(user.getId(),
                user.getEmail());
    }

    UserDtoOlderThan toDtoOlderThan(User user) {
        return new UserDtoOlderThan(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate());
    }

}
