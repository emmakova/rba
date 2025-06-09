package com.rba.interview_be.controller;

import com.rba.interview_be.controller.dto.UserDto;
import com.rba.interview_be.controller.filter.SearchUserFilter;

import com.rba.interview_be.mapper.UserMapper;
import com.rba.interview_be.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> searchUsers(@ModelAttribute SearchUserFilter searchUserFilter) {
        return ResponseEntity.ok(
                userService.searchUsers(searchUserFilter)
                        .stream().map(UserMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                UserMapper.toDto(userService.createUser(userDto))
        );
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                UserMapper.toDto(userService.updateUser(userId, userDto))
        );
    }

    @RequestMapping(value = "/by-oib/{oib}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable String oib) {
        userService.deleteUserByOib(oib);
        return ResponseEntity.ok().build();
    }

}
