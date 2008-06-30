/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectLink;
import com.cosmos.resource.TextResource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sound.sampled.DataLine;

/**
 *
 * @author rlozanov
 */
@Stateless
public class DataObjectLinkBean implements DataObjectLinkLocal {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;
    @EJB
    private DatabaseResourceLocal databaseResource;

    public DataObjectLink newDataObjectLink(DataObjectBean linkedObject) {
        DataObjectLink objectLink = new DataObjectLink();
        objectLink.setLinkedDataObjectBean(linkedObject);
        
        if (linkedObject instanceof TextResource) {
            objectLink.setLinkName(((TextResource)linkedObject).toShortText());
        } else {
             objectLink.setLinkName(linkedObject.toString()); 
        }
        
        return objectLink;
    }

    public DataObjectLink saveDataObjectLink(DataObjectLink dataObjectLink) {
        esm.persist(em, dataObjectLink);
        return dataObjectLink;
    }
    
}
