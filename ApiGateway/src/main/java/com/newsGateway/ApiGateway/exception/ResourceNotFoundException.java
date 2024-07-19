package com.newsGateway.ApiGateway.exception;





public class ResourceNotFoundException extends RuntimeException{
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

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
