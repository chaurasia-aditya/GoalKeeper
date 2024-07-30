package com.chauri.GoalKeeper.exception;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s given input data %s: %s already exists", resourceName, fieldName, fieldValue));
    }
}
