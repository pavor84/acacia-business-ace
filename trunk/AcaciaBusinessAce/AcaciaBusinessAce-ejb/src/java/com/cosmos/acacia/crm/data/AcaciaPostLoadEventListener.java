/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.math.BigInteger;
import org.hibernate.event.EventSource;
import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.def.DefaultPostLoadEventListener;

/**
 *
 * @author Miro
 */
public class AcaciaPostLoadEventListener
    extends DefaultPostLoadEventListener
    //extends EJB3PostLoadEventListener
{

    @Override
    public void onPostLoad(PostLoadEvent event) {
        super.onPostLoad(event);

        Object entity = event.getEntity();
        if(entity instanceof DataObjectLink)
        {
            DataObjectLink doLink = (DataObjectLink)entity;
            DataObject linkedDO = doLink.getLinkedDataObject();
            String linkedObjectName = linkedDO.getDataObjectType().getDataObjectType();
            BigInteger linkedObjectId = linkedDO.getDataObjectId();
            EventSource session = event.getSession();
            DataObjectBean linkedDOB = (DataObjectBean)session.get(linkedObjectName, linkedObjectId);
            doLink.setLinkedDataObjectBean(linkedDOB);
        }
    }
}
