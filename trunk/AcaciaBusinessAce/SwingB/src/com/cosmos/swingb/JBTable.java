/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Miro
 */
public class JBTable
    extends JXTable
{
    private ObservableList data;
    private EntityProperties entityProperties;

    public JBTable()
    {
        internalInitialization();
    }

    protected void internalInitialization()
    {
        setSelectionModel(new TableSelectionModel());
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setColumnControlVisible(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setHorizontalScrollEnabled(true);
        setEditable(false);
    }

    public void addListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.addListSelectionListener(listener);
    }
    
    public void removeListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.removeListSelectionListener(listener);
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        List oldData = getData();
        if(oldData != null && data != null)
        {
            this.data.clear();
            this.data.addAll(data);
        }
        else if(!(data instanceof ObservableList))
        {
            data = ObservableCollections.observableList(data);
        }
        else
        {
            data = (ObservableList)data;
        }
    }

    public int addRow(Object bean)
    {
        if(data == null)
            return -1;

        data.add(bean);
        return data.size() - 1;
    }

    public void setRow(int rowIndex, Object bean)
    {
        if(data != null)
            data.set(convertRowIndexToModel(rowIndex), bean);
    }

    public void replaceSelectedRow(Object bean)
    {
        int rowIndex = getSelectedRow();
        setRow(rowIndex, bean);
    }

    public int getRowIndex(Object bean)
    {
        if(data != null)
        {
            int rowIndex = data.indexOf(bean);
            if(rowIndex >= 0)
            {
                return convertRowIndexToView(rowIndex);
            }
        }

        return -1;
    }

    public void setSelectedRowObject(Object bean)
    {
        int rowIndex = getRowIndex(bean);
        if(rowIndex >= 0)
            getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
    }

    public int getSelectedModelRowIndex()
    {
        int viewRowIndex;
        if((viewRowIndex = getSelectedRow()) != -1)
        {
            return convertRowIndexToModel(viewRowIndex);
        }
        
        return -1;
    }

    public int[] getSelectedModelRowIndexes()
    {
        int[] selectedRowIndexes = getSelectedRows();
        int size;
        if(selectedRowIndexes != null && (size = selectedRowIndexes.length) > 0)
        {
            for(int i = 0; i < size; i++)
            {
                selectedRowIndexes[i] = convertRowIndexToModel(selectedRowIndexes[i]);
            }
        }
        
        return selectedRowIndexes;
    }

    public Object getSelectedRowObject()
    {
        int rowIndex = getSelectedModelRowIndex();
        if(rowIndex >= 0 && data != null && data.size() > rowIndex)
        {
            return data.get(rowIndex);
        }
        
        return null;
    }

    public List getSelectedRowObjects()
    {
        int[] rowIndexes = getSelectedModelRowIndexes();
        int size;
        if(rowIndexes != null && (size = rowIndexes.length) > 0 && data != null)
        {
            ArrayList rows = new ArrayList(size);
            for(int rowIndex : rowIndexes)
            {
                rows.add(data.get(rowIndex));
            }
            return rows;
        }
        
        return Collections.EMPTY_LIST;
    }

    public Object removeSelectedRow()
    {
        int rowIndex = getSelectedModelRowIndex();
        if(rowIndex >= 0 && data != null && data.size() > rowIndex)
        {
            return data.remove(rowIndex);
        }

        return null;
    }

    public JTableBinding bind(
            BindingGroup bindingGroup,
            List data,
            EntityProperties entityProperties) {
        AutoBinding.UpdateStrategy updateStrategy = entityProperties.getUpdateStrategy();
        if(updateStrategy == null)
            updateStrategy = AutoBinding.UpdateStrategy.READ;
        return bind(bindingGroup, data, entityProperties, updateStrategy);
    }

    public JTableBinding bind(
            BindingGroup bindingGroup,
            List data,
            EntityProperties entityProperties,
            AutoBinding.UpdateStrategy updateStrategy) {
        if(!(data instanceof ObservableList))
            this.data = ObservableCollections.observableList(data);
        else
            this.data = (ObservableList)data;
        this.entityProperties = entityProperties;

        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy, data, this);
        createColumnsBinding(tableBinding, entityProperties);

        bindingGroup.addBinding(tableBinding);

        return tableBinding;
    }


    protected void createColumnsBinding(JTableBinding tableBinding, EntityProperties entityProperties)
    {
        createColumnsBinding(tableBinding, entityProperties.getValues());
    }

    protected void createColumnsBinding(JTableBinding tableBinding, Collection<PropertyDetails> properties)
    {
        for(PropertyDetails property : properties)
        {
            if(!property.isHiden())
                createColumnBinding(tableBinding, property);
        }
    }

    protected ColumnBinding createColumnBinding(JTableBinding tableBinding, PropertyDetails propertyDetails)
    {
        String expression = "${" + propertyDetails.getPropertyName() + "}";
        ELProperty elProperty = ELProperty.create(expression);
        ColumnBinding columnBinding = tableBinding.addColumnBinding(elProperty);
        columnBinding.setColumnName(propertyDetails.getPropertyTitle());
        columnBinding.setColumnClass(propertyDetails.getPropertyClass());

        Boolean b = propertyDetails.isEditable();
        if(b != null)
            columnBinding.setEditable(b.booleanValue());

        b = propertyDetails.isVisible();
        if(b != null)
            columnBinding.setVisible(b.booleanValue());

        Object obj = propertyDetails.getSourceUnreadableValue();
        if(obj != null)
            columnBinding.setSourceUnreadableValue(obj);

        //columnBinding.setConverter()
        //columnBinding.setValidator(arg0)

        return columnBinding;
    }

    public EntityProperties getEntityProperties() {
        return entityProperties;
    }

}
