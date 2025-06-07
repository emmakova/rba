package com.rba.interview_be.mapper;

import com.rba.interview_be.controller.dto.CardStatusDto;
import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;

import java.util.Optional;

import static com.rba.interview_be.utils.UserUtils.extractLastCardStatus;

public class UserMapper {

    public static UserDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }

        CardStatusDto lastCardStatus = Optional.ofNullable(extractLastCardStatus(user.getCardStatuses()))
                .map(CardStatusMapper::toDto).orElse(null);

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getOib(), lastCardStatus);
    }

    public static UserDto toDto(UserCardStatusEntity userCardStatusEntity) {
        if (userCardStatusEntity == null) {
            return null;
        }

        return new UserDto(userCardStatusEntity.getUser().getId(), userCardStatusEntity.getUser().getFirstName(),
                userCardStatusEntity.getUser().getLastName(), userCardStatusEntity.getUser().getOib(), CardStatusMapper.toDto(userCardStatusEntity));

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
