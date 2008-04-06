package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	25.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface PatternMaskListRemote {

    /**
     * List all available patterns ordered by name
     * @return
     */
    List<PatternMaskFormat> listPatternsByName();

    /**
     * Entity properties for the mask type
     * @return
     */
    EntityProperties getPatternMaskEntityProperties();

    /**
     * Create new empty instance
     * @return
     */
    PatternMaskFormat newPatternMaskFormat();

    /**
     * Save a pattern mask format instance
     * @param format
     * @return
     */
    PatternMaskFormat savePatternMaskFormat(PatternMaskFormat format);
    
    /**
     * Get list of the possible business partner that can be assigned
     * as owners of a given mask format
     * @return 
     */
    List<BusinessPartner> getOwnersList();

    /**
     * Removes the specified format from the database
     * @param rowObject
     * @return
     */
    boolean deletePatternMaskFormat(PatternMaskFormat formatObject);
}
