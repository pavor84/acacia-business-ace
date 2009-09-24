/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.contactbook;

import com.cosmos.acacia.crm.data.location.Country;
import javax.ejb.Local;

/**
 *
 * @author Bozhidar Bozhanov
 */
@Local
public interface LocationsListLocal extends LocationsListRemote {

    Long getCountriesCount();

    Long getCitiesCount(Country country);
}
