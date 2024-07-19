package com.userService.UserService.responses;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Date timestamp;

    public ErrorResponse(int status, String message, Date timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }



    // Getters and setters
}