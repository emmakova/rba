package com.rba.interview_be.service.user;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.exceptions.NotFoundException;
import com.rba.interview_be.mapper.UserMapper;
import com.rba.interview_be.repository.UserRepository;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rba.interview_be.service.queryspecifications.UsersQueryPredicateService.getUsersQueryPredicates;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCardStatusService userCardStatusService;


    @Override
    public UserEntity findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", userId)));
    }

    @Override
    public List<UserEntity> searchUsers(SearchUserFilter searchUserFilter) {
        return userRepository.findAll(
                (root, query, builder) -> getUsersQueryPredicates(root, query, builder, searchUserFilter)
        );
    }

    @Override
    public UserEntity createUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.toEntity(userDto);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity updateUser(Integer userId, UserDto userDto) {
        UserEntity user = findById(userId);
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setOib(userDto.oib());
        return userRepository.save(user);
    }

    @Override
    public void deleteUserByOib(String oib) {
        userRepository.findByOib(oib).ifPresent(u -> {
            userCardStatusService.deleteAllForUser(u);
            userRepository.deleteById(u.getId());
        });
    }

}
