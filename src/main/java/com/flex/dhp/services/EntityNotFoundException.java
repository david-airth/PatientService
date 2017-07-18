package com.flex.dhp.services;

/**
 * Created by david.airth on 7/18/17.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String name, long id) {
        super(String.format("%s with id '%s' not found", name, id));
    }
}
