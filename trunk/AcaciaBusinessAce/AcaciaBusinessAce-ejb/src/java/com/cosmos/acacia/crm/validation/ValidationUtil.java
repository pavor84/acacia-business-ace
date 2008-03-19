package com.cosmos.acacia.crm.validation;

import java.util.List;

/**
 * Created	:	18.03.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Useful class to store common logic related to data validation of
 * business entities.
 * All exposed methods are meant to be static.
 */
public abstract class ValidationUtil {

    /**
     * Check for empty string.
     * Empty string is either null or contains only white characters.
     * @param productName
     * @return true if the argument is empty string
     */
    public static boolean isEmpty(String value) {
        if (value==null)
            return true;
        if ( "".equals(value.trim()))
            return true;
        return false;
    }

    /**
     * The method returns true if the list is empty (or null) or
     * contains only one object which is the same as the entity.
     * @param list
     * @param entity
     * @return
     */
    public static boolean checkUnique(List<?> list, Object entity) {
        if ( entity==null )
            throw new IllegalArgumentException("Please provide not null entity!");
        
        //nothing in the list - the entity is unique
        if ( list==null || list.isEmpty() )
            return true;
        //list with more than 1 element - the entity is not unique
        if ( list.size()>1 )
            return false;
        Object listElement = list.get(0);
        //null element in the list - then the entity is not here and is unique
        if ( listElement==null )
            return true;
        //only one element in the list - but its the entity - then it has no duplication - and is unique
        if ( listElement.equals(entity))
            return true;
        //one element in the list, but other - so not unique
        return false;
    }

}
