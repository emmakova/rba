package com.rba.interview_be.mapper;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;

import java.util.Optional;

import static com.rba.interview_be.utils.UserUtils.extractLastCardStatus;

public class UserMapper {

    public static UserDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }

        CardStatusEnum lastCardStatus = Optional.ofNullable(extractLastCardStatus(user.getCardStatuses())).map(UserCardStatusEntity::getStatus).orElse(null);

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getOib(), lastCardStatus);
    }

    public static UserEntity toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDto.firstName());
        userEntity.setLastName(userDto.lastName());
        userEntity.setOib(userDto.oib());

        return userEntity;
    }

}
