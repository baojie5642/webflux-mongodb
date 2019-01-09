package org.spring.springboot.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(Throwable t) {
        super(t);
    }

}
