package com.rba.interview_be.service.user;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.mapper.UserMapper;
import com.rba.interview_be.repository.UserRepository;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rba.interview_be.service.queryspecifications.UsersQueryPredicateService.getUsersQueryPredicates;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCardStatusService userCardStatusService;


    @Override
    public Optional<UserEntity> findById(Integer userId) {
        return userRepository.findById(userId);
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
    public void deleteUserByOib(String oib) {
        userRepository.findByOib(oib).ifPresent(u -> {
            userCardStatusService.deleteAllForUser(u);
            userRepository.deleteById(u.getId());
        });
    }

}
