package com.comment.CommentService.exception;

import lombok.Data;

@Data

public class ResourceNotFoundException extends RuntimeException{

    private String message;
    private String resourceName;
    private String resourceType;


    public ResourceNotFoundException(String message, String resourceName, String resourceType) {
        super(message+" with "+resourceName+" : "+resourceType);
        this.message = message+" with "+resourceName+" : "+resourceType;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }
}
