/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.contacts.Organization;
import javax.ejb.Local;

/**
 *
 * @author miro
 */
@Local
public interface DatabaseResourceLocal {

    void initDatabaseResource();

    void initPrivilegeCategory(Organization organization);
}
