/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.beansbinding.BeansBinding;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.JBTable;
import java.util.Arrays;
import java.util.List;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.impl.ListBindingManager;

/**
 *
 * @author miro
 */
public class AcaciaTable
    extends JBTable
{
    private EntityProperties entityProperties;
    private JTableBinding tableBinding;


    public AcaciaTable()
    {
        super();
    }

    public EntityProperties getEntityProperties() {
        return entityProperties;
    }

    public void setEntityProperties(EntityProperties entityProperties)
    {
        if(this.entityProperties != null && entityProperties != null)
        {
            this.entityProperties = null;
            initTableBinding();
        }

        this.entityProperties = entityProperties;
        initTableBinding();
    }

    @Override
    public void setData(List data)
    {
        List oldData = getData();
        if(oldData != null && data != null)
        {
            super.setData(null);
            initTableBinding();
        }
        else if(!(data instanceof ObservableList))
        {
            data = ObservableCollections.observableList(data);
        }

        super.setData(data);
        initTableBinding();
    }

    public JTableBinding getTableBinding()
    {
        return tableBinding;
    }

    protected void initTableBinding()
    {
        List data = getData();
        EntityProperties entityProps = getEntityProperties();
        if(data == null || entityProps == null)
        {
            if(tableBinding != null)
            {
                tableBinding.unbind();
                tableBinding = null;
            }
        }
        else
        {
            tableBinding = BeansBinding.createTableBinding(this, data, entityProps);
        }
    }
}
