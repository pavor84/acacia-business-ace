/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Miro
 */
public class JBTable
    extends JXTable
{
    private List data;

    public JBTable()
    {
        internalInitialization();
    }

    protected void internalInitialization()
    {
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
        if(this.data != null)
        {
            this.data.clear();
            this.data.addAll(data);
        }
        else
        {
            this.data = data;
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


}
