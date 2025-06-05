package com.rba.interview_be.mapper;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.rba.interview_be.utils.UserUtils.extractLastCardStatus;

public class UserMapper {

    public static UserDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }

        String lastCardStatus = extractLastCardStatus(user.getCardStatuses());

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
