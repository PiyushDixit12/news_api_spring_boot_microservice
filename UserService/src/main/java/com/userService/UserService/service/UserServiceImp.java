package com.userService.UserService.service;

import com.userService.UserService.entities.User;
import com.userService.UserService.exception.ResourceNotFoundException;
import com.userService.UserService.payload.UserDto;
import com.userService.UserService.repositories.UserRepo;
import com.userService.UserService.responses.PaginationResponse;
import com.userService.UserService.utils.UserInfoDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepo.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        Optional<User> findedUser = Optional.ofNullable(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found ", "userId", "" + userId)));

        User user = findedUser.get();

        if (userDto.getName() != null && !userDto.getName().equals(user.getName()) && !(userDto.getName().trim() == "")) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail()) && !(userDto.getEmail().trim() == "")) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().equals(user.getPassword()) && !(userDto.getPassword().trim() == "")) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getPhone() != null && !userDto.getPhone().equals(user.getPhone())) {
            user.setPhone(userDto.getPhone());
        }

        return modelMapper.map(userRepo.save(user), UserDto.class);

    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> findedUser = Optional.ofNullable(userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found ", "userId", "" + id)));

        return modelMapper.map(findedUser.get(), UserDto.class);
    }

    @Override
    public PaginationResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable =null;
        if (sortBy.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }

        Page<User> findedUsers = userRepo.findAll(pageable);
        PaginationResponse<UserDto> users = new PaginationResponse<>(
                findedUsers.getNumber(),
                findedUsers.getSize(),
                findedUsers.getNumberOfElements(),
                findedUsers.getTotalPages(),
                findedUsers.hasNext(),
                findedUsers.stream().map((user -> modelMapper.map(user, UserDto.class))).collect(Collectors.toList())
        );

        return users;
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> user = Optional.ofNullable(userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found ", "userId", "" + id)));
        userRepo.delete(user.get());
    }



//    ======================= Authentication Code =====================

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = Optional.ofNullable(userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "userName not found " + username, "")));
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found "+username));
    }

}
