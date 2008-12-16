package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;

/**
 * 
 * Created	:	13.12.2008
 * @author	Petar Milev
 *
 */
@Remote
public interface EnumResourceRemote {
    /**
     * Get the values list for a given enumeration type.
     * @param enumClass
     * @return not null list
     * @throws RuntimeException with appropriated cause set, depending on internal implementation.
     */
    List<DbResource> getEnumResources(Class<? extends DatabaseResource> enumClass);
}
