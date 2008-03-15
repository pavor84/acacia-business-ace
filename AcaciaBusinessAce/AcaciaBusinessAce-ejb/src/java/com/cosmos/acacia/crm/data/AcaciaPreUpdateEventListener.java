/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

/**
 *
 * @author Miro
 */
public class AcaciaPreUpdateEventListener
    implements PreUpdateEventListener
{

    public boolean onPreUpdate(PreUpdateEvent event)
    {
        boolean veto = false;
        System.out.println("onPreUpdate: " + this);

        return veto;
    }

}
