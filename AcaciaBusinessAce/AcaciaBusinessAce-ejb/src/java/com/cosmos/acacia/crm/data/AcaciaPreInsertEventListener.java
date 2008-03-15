/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import org.hibernate.Session;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;

/**
 *
 * @author Miro
 */
public class AcaciaPreInsertEventListener
    implements PreInsertEventListener
{

    public boolean onPreInsert(PreInsertEvent event)
    {
        boolean veto = false;
        System.out.println("onPreInsert: " + this);

        return veto;
    }

}
