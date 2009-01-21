/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.util.AcaciaProperties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author Miro
 */
@Stateless
public class AcaciaEjbInitBean
    implements AcaciaEjbInitRemote
{
    private static final Logger logger = Logger.getLogger(AcaciaEjbInitBean.class);

    @EJB
    private DatabaseResourceLocal databaseResource;

    @Override
    public void init()
    {
        logger.info("AcaciaEjbInitBean.init().databaseResource.initDatabaseResource()");
        databaseResource.initDatabaseResource();
    }


}
