package com.cosmos.acacia.crm.bl.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.ejb.Stateless;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DatabaseResource;

/**
 * Created	:	25.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class EnumResourceBean implements EnumResourceLocal, EnumResourceRemote {
    
    public List<DbResource> getEnumResources(Class<? extends DatabaseResource> enumClass) {
        try {
            Method getDbResources = enumClass.getMethod("getDbResources");
            return (List<DbResource>) getDbResources.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}
