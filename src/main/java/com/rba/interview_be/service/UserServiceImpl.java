package com.rba.interview_be.service;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserCardStatusEntity;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.enums.CardStatusEnum;
import com.rba.interview_be.exceptions.NotFoundException;
import com.rba.interview_be.mapper.UserMapper;
import com.rba.interview_be.repository.UserRepository;
import com.rba.interview_be.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rba.interview_be.service.queryspecifications.UsersQueryPredicateService.getUsersQueryPredicates;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCardStatusService userCardStatusService;
    private final NewCardRequestApiClient newCardRequestApiClient;


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
    public void deleteUserByOib(String oib) {

        userRepository.findByOib(oib).ifPresent(u -> {
            userCardStatusService.deleteAllForUser(u);
            userRepository.deleteById(u.getId());
        });
    }

    @Override
    public UserEntity submitNewCardRequestForUser(Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", userId)));
        newCardRequestApiClient.submitNewCardRequestForUser(user);
        UserCardStatusEntity newCardStatusForUser = userCardStatusService.createNewCardStatusForUser(user, CardStatusEnum.PENDING);
        UserUtils.addCardStatusToUser(user, newCardStatusForUser);
        return user;
    }
}
