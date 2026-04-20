package com.vendor.management.exception;

/**
 * Exception thrown when attempting to create a resource
 * that already exists.
 *
 * Typically used for cases like duplicate email,
 * category name, or other unique constraints.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructs a new DuplicateResourceException with the specified message.
     *
     * @param message detailed error message
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}