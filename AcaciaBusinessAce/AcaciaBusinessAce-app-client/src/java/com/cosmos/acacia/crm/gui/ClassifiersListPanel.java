/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ClassifiersListPanel extends AbstractTablePanel<Classifier> {

    /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(DataObject dataObject)
    {
        super();
        classifiedDataObject = dataObject;
        postInitData();
    }

      /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(UUID parentDataObjectId, DataObjectType dot)
    {
        super(parentDataObjectId);
        this.dataObjectType = dot;
        postInitData();
    }

    public ClassifiersListPanel(UUID parentId) {
        super(parentId);
        postInitData();
    }

    private BindingGroup classifiersBindingGroup;
    private List<Classifier> classifiers;
    private DataObjectType dataObjectType;
    private DataObject classifiedDataObject;

    @Override
    protected void initData() {

        super.initData();
        setVisible(Button.Select, false);
        setVisible(Button.Classify, false);

    }

    protected void postInitData() {

        classifiersBindingGroup = new BindingGroup();
        AcaciaTable classifiersTable = getDataTable();
        JTableBinding tableBinding = classifiersTable.bind(classifiersBindingGroup, getClassifiers(), getClassifierEntityProperties());

        classifiersBindingGroup.bind();

        classifiersTable.setEditable(false);
    }

    public List<Classifier> getClassifiers()
    {
        if(classifiers == null)
        {
            if (classifiedDataObject != null)
                classifiers = getClassifiersManager().getClassifiers(classifiedDataObject);
            else
                //classifiers = getClassifiersManager().getClassifiers(getParentDataObjectId(), dataObjectType);
                classifiers = getClassifiersManager().getClassifiers(null, dataObjectType);
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

    protected EntityProperties getClassifierEntityProperties() {
        return getClassifiersManager().getClassifierEntityProperties();
    }

    protected int deleteClassifier(Classifier classifier) {
        return getClassifiersManager().deleteClassifier(classifier);
    }


    public void filter(List<Classifier> filterList) {

        List<Classifier> classifiersMirror = new ArrayList<Classifier>(classifiers);
        for (Classifier classifier : classifiersMirror) {
            if (filterList.contains(classifier))
                classifiers.remove(classifier);
        }
        postInitData();
    }

    @Override
    @Action
    public void selectAction(){
        super.selectAction();
        //
    }

    @Override
    protected boolean deleteRow(Classifier rowObject) {
         if(rowObject != null)
        {
            deleteClassifier(rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Classifier modifyRow(Classifier rowObject) {
        if(rowObject != null)
        {
            boolean allowCafdModifications = dataObjectType == null;

            ClassifierPanel classifierPanel = new ClassifierPanel(
                    rowObject,
                    allowCafdModifications);

            DialogResponse response = classifierPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return (Classifier) classifierPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        if (classifiersBindingGroup != null)
            classifiersBindingGroup.unbind();

        classifiers = null;

        postInitData();
        filterByClassifier();

        Task t = super.refreshAction();
        return t;
    }

    @Override
    protected Classifier newRow() {
        if (canNestedOperationProceed())
        {
            ClassifierPanel classifierPanel = null;
            if (dataObjectType == null)
                classifierPanel = new ClassifierPanel(getParentDataObjectId());
            else
                classifierPanel = new ClassifierPanel();

            DialogResponse response = classifierPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return (Classifier) classifierPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Classifier rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Classifier rowObject) {
        if(isSystemAdministrator())
            return true;

        Classifier classifier = rowObject;
        ClassifierGroup classifierGroup = classifier.getClassifierGroup();
        if(classifierGroup.getIsSystemGroup())
            return false;

        return true;
    }
}