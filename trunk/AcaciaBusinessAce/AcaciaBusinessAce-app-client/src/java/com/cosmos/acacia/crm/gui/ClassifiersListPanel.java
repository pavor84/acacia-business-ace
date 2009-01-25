/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
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
public class ClassifiersListPanel extends AbstractTablePanel {

    /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(DataObject dataObject)
    {
        super();
        classifiedDataObject = dataObject;
        postInitData();
    }

      /** Creates new form ClassifiersListPanel */
    public ClassifiersListPanel(BigInteger parentDataObjectId, DataObjectType dot)
    {
        super(parentDataObjectId);
        this.dataObjectType = dot;
        postInitData();
    }

    public ClassifiersListPanel(BigInteger parentId) {
        super(parentId);
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
                classifiers = getFormSession().getClassifiers(classifiedDataObject);
            else
                //classifiers = getFormSession().getClassifiers(getParentDataObjectId(), dataObjectType);
                classifiers = getFormSession().getClassifiers(null, dataObjectType);
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
        return getFormSession().getClassifierEntityProperties();
    }

    public ClassifiersRemote getFormSession() {
        if(formSession == null)
            formSession = getBean(ClassifiersRemote.class);

        return formSession;
    }

    protected int deleteClassifier(Classifier classifier) {
        return getFormSession().deleteClassifier(classifier);
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
            boolean allowCafdModifications = dataObjectType == null;

            ClassifierPanel classifierPanel = new ClassifierPanel(
                    (Classifier)rowObject,
                    allowCafdModifications);

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
        if (classifiersBindingGroup != null)
            classifiersBindingGroup.unbind();

        classifiers = null;

        postInitData();
        filterByClassifier();

        Task t = super.refreshAction();
        return t;
    }

    @Override
    protected Object newRow() {
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
        if(isSystemAdministrator())
            return true;

        Classifier classifier = (Classifier)rowObject;
        ClassifierGroup classifierGroup = classifier.getClassifierGroup();
        if(classifierGroup.getIsSystemGroup())
            return false;

        return true;
    }

}