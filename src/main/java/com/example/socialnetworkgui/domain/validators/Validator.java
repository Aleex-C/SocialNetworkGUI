package com.example.socialnetworkgui.domain.validators;

/**
 * Validation operation interface.
 * @param <T> - type of entities that are to be validated.
 */
public interface Validator<T> {
    /**
     *
     * @param entity - the entity to be validated.
     * @throws ValidationException
     *              if some requirements are not met!
     *
     */
    void validate(T entity) throws ValidationException;
}
