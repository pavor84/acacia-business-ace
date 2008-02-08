/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 *
 * @author miro
 */
@Stateless
public class DataObjectsListeners {

    @EJB
    private EntityStoreManagerRemote esm;


    @PrePersist
    public void prePersist(Object entity)
    {
        System.out.println("PrePersist, esm: " + esm);
        try
        {
            if(esm == null)
            {
                esm = InitialContext.doLookup(EntityStoreManagerRemote.class.getName());
                System.out.println("esm: " + esm);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @PostPersist
    public void postPersist(Object entity)
    {
        System.out.println("PostPersist");
    }

    @PreUpdate
    public void preUpdate(Object entity)
    {
        System.out.println("PreUpdate");
    }

    @PostUpdate
    public void postUpdate(Object entity)
    {
        System.out.println("PostUpdate");
    }

    @PreRemove
    public void preRemove(Object entity)
    {
        System.out.println("PreRemove");
    }

    @PostRemove
    public void postRemove(Object entity)
    {
        System.out.println("PostRemove");
    }
}
