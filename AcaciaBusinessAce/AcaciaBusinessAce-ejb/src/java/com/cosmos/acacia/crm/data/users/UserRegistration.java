/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.City;
import com.cosmos.acacia.crm.data.contacts.Country;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Miro
 */
public class UserRegistration {//extends DataObjectBean {

    private String emailAddress;
    private String username;
    private String password;
    private String rePassword;
    //
    private DbResource gender;
    private String firstName;
    private String secondName;
    private String lastName;
    private String extraName;
    private String personalUniqueId;
    private Date birthDate;
    private Country birthPlaceCountry;
    private City birthPlaceCity;
}
