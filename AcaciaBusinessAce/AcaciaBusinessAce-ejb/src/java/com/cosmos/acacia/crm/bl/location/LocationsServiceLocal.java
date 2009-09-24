/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.location;

import com.cosmos.acacia.crm.data.location.City;
import com.cosmos.acacia.crm.data.location.Country;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface LocationsServiceLocal extends LocationsServiceRemote {

    List<Country> getCountries();

    Long getCountriesCount();

    List<City> getCities(Country country);

}
