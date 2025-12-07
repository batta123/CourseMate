package com.coursemate.exception;

/**
 * Custom exception for resource not found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ResourceNotFoundException of(String resourceName, String fieldName, Object fieldValue) {
        return new ResourceNotFoundException(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
