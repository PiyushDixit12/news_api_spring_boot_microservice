package com.userService.UserService.service;

import com.userService.UserService.payload.UserDto;
import com.userService.UserService.responses.PaginationResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,Long userId);

    UserDto getUserById(Long id);

    PaginationResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder);

    void deleteUserById(Long id);

}
