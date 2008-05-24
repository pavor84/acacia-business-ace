package com.cosmos.acacia.crm.bl.validation;

import javax.ejb.Local;

import com.cosmos.acacia.crm.validation.EntityValidator;

/**
 * A Generic validator for unique names.
 * In order this class to be used, the entity class should conform to the following:
 * - there should be a named query {EntityName}.findBy{ParameterName}
 * - the named query should have a parameter "parameterName"
 * - should have a getter method get{ParameterName}
 * 
 * @author Bozhidar Bozhanov
 */
@Local
public interface GenericUniqueValidatorLocal<E> extends EntityValidator<E>{

    /**
    * Validates the uniqueness of the specified entity by the parameter supplied
    * 
    * @param entity
    * @param param the parameter name. Should start with lower case
    */
    void validate(E entity, String param);
}
