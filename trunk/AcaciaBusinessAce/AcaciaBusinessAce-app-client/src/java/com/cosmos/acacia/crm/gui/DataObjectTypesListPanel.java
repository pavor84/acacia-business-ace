/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.observablecollections.ObservableCollections;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class DataObjectTypesListPanel extends AbstractTablePanel {

    /** Creates new form DataObjectTypesListPanel */
    public DataObjectTypesListPanel()
    {
        super(null);
        postInitData();
    }

      /** Creates new form DataObjectTypesListPanel */
    public DataObjectTypesListPanel(DataObject parentDataObject, Classifier classifier)
    {
        super(parentDataObject);
        this.classifier = classifier;
        postInitData();
    }
    
    @EJB
    private ClassifiersRemote formSession;

    private BindingGroup dataObjectTypesBindingGroup;
    private List<DataObjectType> dataObjectTypes;
    private DataObjectType dataObjectType;
    private Classifier classifier;
    
    @Override
    protected void initData() {

        super.initData();
        setVisible(Button.Select, false);
        
    }
    
    protected void postInitData() {
        dataObjectTypesBindingGroup = new BindingGroup();
        AcaciaTable dataObjectTypesTable = getDataTable();
        JTableBinding tableBinding = dataObjectTypesTable.bind(dataObjectTypesBindingGroup, getDataObjectTypes(), getDataObjectTypeEntityProperties());
        
        List<DataObjectType> l = ObservableCollections.observableList(getDataObjectTypes());
        
        dataObjectTypesBindingGroup.bind();

        dataObjectTypesTable.setEditable(false);
    }

    protected List<DataObjectType> getDataObjectTypes()
    {
        if(dataObjectTypes == null)
        {   
            dataObjectTypes = getFormSession().getDataObjectTypes(classifier);
        }
        return dataObjectTypes;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    protected EntityProperties getDataObjectTypeEntityProperties()
    {
        return getFormSession().getDataObjectTypeEntityProperties();
    }

    protected ClassifiersRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(ClassifiersRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected void deleteDataObjectType(DataObjectType dataObjectType)
    {
        getFormSession().removeDataObjectTypeConstraint(classifier, dataObjectType);
    }

    @Override
    @Action
    public void selectAction(){
        super.selectAction();
        //
    }
    
    @Override
    protected boolean deleteRow(Object rowObject) {
         if(rowObject != null)
        {
            deleteDataObjectType((DataObjectType) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
       return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        if (dataObjectTypesBindingGroup != null)
            dataObjectTypesBindingGroup.unbind();
        
        dataObjectTypes = null;
        
        postInitData();
        
        return t;
    }
        
    @Override
    protected Object newRow() {
        return null;
    }
    
    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }

}