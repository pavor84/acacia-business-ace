package com.cosmos.acacia.crm.validation;

/**
 * Created	:	17.03.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Common interface to be used as business validation service provider.
 *
 * T - the type of the object to validate
 */
public interface EntityValidator<T>{
    /**
     * If the validation fails the method is expected to throw ValidationException
     * @see ValidationException.
     * @param entity
     * @throws ValidationException
     */
    void validate(T entity) throws ValidationException;
}
