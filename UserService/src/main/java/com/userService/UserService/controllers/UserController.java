package com.userService.UserService.controllers;


import com.userService.UserService.exception.ResourceNotFoundException;
import com.userService.UserService.payload.AuthRequest;
import com.userService.UserService.payload.UserDto;
import com.userService.UserService.responses.DeleteApiResponse;
import com.userService.UserService.responses.PaginationResponse;
import com.userService.UserService.service.JwtService;
import com.userService.UserService.service.UserService;
import com.userService.UserService.utils.Blacklist;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Blacklist blacklist;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        return  new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse> getAllUsers(
            @RequestParam(name = "pageNumber",defaultValue = "0")int pageNumber,
            @RequestParam(name ="pageSize",defaultValue = "10" )int pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = "userId")String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = "asc")String sortOrder
    ) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);

    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable long userId, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto, userId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<DeleteApiResponse> deleteUser(@PathVariable long userId) {
        userService.deleteUserById(userId);
        return  new ResponseEntity<>(new DeleteApiResponse("User Deleted Successfully",true), HttpStatus.OK);
    }


//    AUTHENTICATION AND AUTHORIZATION
@PostMapping("/logout")
//@PreAuthorize("hasAuthority('"+ AppConstants.USER_ROLE +"') or hasAuthority('"+ AppConstants.ADMIN_ROLE +"')")
public String logoutUser(HttpServletRequest request){
    String authHeader = request.getHeader("Authorization");
    String token= null;
    if(authHeader !=null && authHeader.startsWith("Bearer ")){
        token = authHeader.substring(7);
    }
//    blacklist.blacKListToken(token);
    return "You have successfully logged out !!";
}
    @PostMapping("/login")
    public String loginUser( @Valid @RequestBody AuthRequest authRequest){

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            System.out.println("Generating Token");
            return jwtService.generateToken(authRequest);

        }else {
            throw new ResourceNotFoundException(
                    "User",
                    "email",
                    authRequest.getEmail()
            );
        }
    }

    @PostMapping("/valid-token/{token}")
    public ResponseEntity<Boolean> isValidToken(@PathVariable("token") String token){
        jwtService.isValidToken(token);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
}
