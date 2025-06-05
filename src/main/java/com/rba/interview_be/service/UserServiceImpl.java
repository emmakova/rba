package com.rba.interview_be.service;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.mapper.UserMapper;
import com.rba.interview_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rba.interview_be.service.queryspecifications.UsersQueryPredicateService.getUsersQueryPredicates;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserCardStatusService userCardStatusService;


    @Override
    public List<UserEntity> searchUsers(SearchUserFilter searchUserFilter) {
        return userRepository.findAll(
                (root, query, builder) -> getUsersQueryPredicates(root, query, builder, searchUserFilter)
        );

    }

    @Override
    @Transactional
    public UserEntity createUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.toEntity(userDto);
        userRepository.save(userEntity);

        UserCardStatusEntity userCardStatusEntity = userCardStatusService.createNewCardStatusForUser(userEntity, userDto.cardStatus());
        userEntity.setCardStatuses(List.of(userCardStatusEntity));

        return userEntity;
    }

    @Override
    @Transactional
    public void deleteUserByOib(String oib) {

        userRepository.findByOib(oib).ifPresent(u -> {
            userCardStatusService.deleteAllForUser(u);
            userRepository.deleteById(u.getId());
        });
    }
}
