package com.category.CategoryService.exception;

import lombok.Data;

@Data

public class ResourceNotFoundException extends RuntimeException{

    private String message;
    private String resourceName;
    private String resourceType;


    public ResourceNotFoundException(String message, String resourceName, String resourceType) {
        super(message+" with "+resourceName+" : "+resourceType);
        this.message = message+"Not Found with "+resourceName+" : "+resourceType;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }
}
