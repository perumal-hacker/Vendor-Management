package com.vendor.management.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * Typically used when an entity with a given identifier
 * does not exist in the system.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified message.
     *
     * @param message detailed error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}