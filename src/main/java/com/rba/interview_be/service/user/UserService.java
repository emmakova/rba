package com.rba.interview_be.service.user;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(Integer userId);

    List<UserEntity> searchUsers(SearchUserFilter searchUserFilter);

    UserEntity createUser(UserDto userDto);

    void deleteUserByOib(String oib);

}
