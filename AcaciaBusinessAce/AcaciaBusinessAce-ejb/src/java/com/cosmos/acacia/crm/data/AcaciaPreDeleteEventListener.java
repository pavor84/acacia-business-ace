/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;

/**
 *
 * @author Miro
 */
public class AcaciaPreDeleteEventListener
    implements PreDeleteEventListener
{
    public boolean onPreDelete(PreDeleteEvent event) {
        boolean veto = false;
        if(true)
            return veto;

        System.out.println("onPreDelete: " + this);
        Object entity = event.getEntity();

        if(entity instanceof DataObjectBean)
        {
            //Session session = event.getPersister().getFactory().getCurrentSession();
            StatelessSession session = event.getPersister().getFactory().openStatelessSession();
            System.out.println("session: " + session);
            Transaction tx = session.beginTransaction();
            System.out.println("tx: " + tx);
            tx.begin();
            System.out.println("tx.begin");
            try
            {
                DataObjectBean doBean = (DataObjectBean)entity;
                DataObject dataObject = doBean.getDataObject();
                if(dataObject == null)
                {
                    System.out.println("load");
                    dataObject = (DataObject)session.get(DataObject.class, doBean.getId());
                    //dataObject = (DataObject)session.load(DataObject.class, doBean.getId());
                }
                else
                {
                    //System.out.println("merge");
                    //dataObject = (DataObject)session.merge(dataObject);
                }
                int version = dataObject.getDataObjectVersion() + 1;
                dataObject.setDataObjectVersion(version);
                dataObject.setDeleted(true);
                System.out.println("persist");
                //session.persist(dataObject);
                session.update(dataObject);
                System.out.println("commit");
                tx.commit();
            }
            catch(Exception ex)
            {
                tx.rollback();
                session.close();
                throw new RuntimeException(ex);
            }
        }

        return veto;
    }

}
