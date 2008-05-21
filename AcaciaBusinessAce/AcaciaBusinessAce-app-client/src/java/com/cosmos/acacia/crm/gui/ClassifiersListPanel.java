/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ClassifiersListPanel extends AbstractTablePanel {

    /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(DataObject dataObject)
    {
        super(null);
        classifiedDataObject = dataObject;
        postInitData();
    }

      /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(DataObject parentDataObject, DataObjectType dot)
    {
        super(parentDataObject);
        this.dataObjectType = dot;
        postInitData();
    }
    
    @EJB
    private ClassifiersRemote formSession;

    private BindingGroup classifiersBindingGroup;
    private List<Classifier> classifiers;
    private DataObjectType dataObjectType;
    private DataObject classifiedDataObject;
    
    @Override
    protected void initData() {

        super.initData();
        setVisible(Button.Select, false);
        
    }
    
    protected void postInitData() {
        classifiersBindingGroup = new BindingGroup();
        AcaciaTable classifiersTable = getDataTable();
        JTableBinding tableBinding = classifiersTable.bind(classifiersBindingGroup, getClassifiers(), getClassifierEntityProperties());

        classifiersBindingGroup.bind();

        classifiersTable.setEditable(false);
    }

    protected List<Classifier> getClassifiers()
    {
        if(classifiers == null)
        {   
            if (classifiedDataObject != null)
                classifiers = getFormSession().getClassifiers(classifiedDataObject);
            else
                classifiers = getFormSession().getClassifiers(getParentDataObject(), dataObjectType);
        }
        return classifiers;
    }

    public DataObject getClassifiedDataObject() {
        return classifiedDataObject;
    }

    public void setClassifiedDataObject(DataObject classifiedDataObject) {
        this.classifiedDataObject = classifiedDataObject;
    }

    public DataObjectType getDataObjectType() {
        return dataObjectType;
    }

    public void setDataObjectType(DataObjectType dataObjectType) {
        this.dataObjectType = dataObjectType;
    }

    protected EntityProperties getClassifierEntityProperties()
    {
        return getFormSession().getClassifierEntityProperties();
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

    protected int deleteClassifier(Classifier classifier)
    {
        return getFormSession().deleteClassifier(classifier);
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
            deleteClassifier((Classifier) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            ClassifierPanel classifierPanel = new ClassifierPanel((Classifier)rowObject);
            DialogResponse response = classifierPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return classifierPanel.getSelectedValue();
            }
        }
         
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        if (classifiersBindingGroup != null)
            classifiersBindingGroup.unbind();
        
        classifiers = null;
        
        postInitData();
        
        return t;
    }
        
    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            ClassifierPanel classifierPanel = new ClassifierPanel(getParentDataObject());

            DialogResponse response = classifierPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return classifierPanel.getSelectedValue();
            }
        }
        
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