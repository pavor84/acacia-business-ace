/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Miro
 */
@Stateless
public class AcaciaEjbInitBean
    implements AcaciaEjbInitRemote
{
    @EJB DatabaseResourceLocal databaseResource;

    public void init()
    {
        databaseResource.initDatabaseResource();
    }


}
