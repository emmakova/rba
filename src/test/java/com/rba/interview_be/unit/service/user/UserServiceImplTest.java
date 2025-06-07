package com.rba.interview_be.unit.service.user;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;
import com.rba.interview_be.entities.UserEntity;
import com.rba.interview_be.repository.UserRepository;
import com.rba.interview_be.service.cardstatus.UserCardStatusService;
import com.rba.interview_be.service.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCardStatusService userCardStatusService;


    @Test
    void searchUsers_delegatesToRepository() {
        SearchUserFilter filter = new SearchUserFilter(null);
        List<UserEntity> expectedUsers = List.of(new UserEntity(), new UserEntity());

        when(userRepository.findAll(any(Specification.class))).thenReturn(expectedUsers);

        List<UserEntity> actualUsers = userService.searchUsers(filter);

        assertThat(actualUsers).isEqualTo(expectedUsers);
        verify(userRepository).findAll(any(Specification.class));
    }

    @Test
    void createUser_mapsAndSavesUser() {
        UserDto userDto = new UserDto(null, "John", "Doe", "123456789", null);
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setOib("123456789");


        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity savedUser = userService.createUser(userDto);

        assertThat(savedUser.getFirstName()).isEqualTo("John");
        assertThat(savedUser.getOib()).isEqualTo("123456789");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void deleteUserByOib_deletesUserAndCardStatuses_whenUserExists() {
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setOib("123456789");

        when(userRepository.findByOib("123456789")).thenReturn(Optional.of(user));

        userService.deleteUserByOib("123456789");

        verify(userCardStatusService).deleteAllForUser(user);
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void deleteUserByOib_doesNothing_whenUserDoesNotExist() {
        when(userRepository.findByOib("nonexistent")).thenReturn(Optional.empty());

        userService.deleteUserByOib("nonexistent");

        verify(userCardStatusService, never()).deleteAllForUser(any());
        verify(userRepository, never()).deleteById(anyInt());
    }
}
