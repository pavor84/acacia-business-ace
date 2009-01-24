/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ClassifierGroupsListPanel extends AbstractTablePanel {

    /** Creates new form ClassifierGroupsListPanel */
    public ClassifierGroupsListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }

    @EJB
    private ClassifiersRemote formSession;

    private BindingGroup classifierGroupsBindingGroup;
    private List<ClassifierGroup> classifierGroups;

    @Override
    protected void initData() {

        super.initData();
        this.setVisible(Button.Classify, false);

        classifierGroupsBindingGroup = new BindingGroup();
        AcaciaTable classifierGroupsTable = getDataTable();
        JTableBinding tableBinding = classifierGroupsTable.bind(classifierGroupsBindingGroup, getClassifierGroups(), getClassifierGroupEntityProperties());

        classifierGroupsBindingGroup.bind();

        classifierGroupsTable.setEditable(false);
    }

    protected List<ClassifierGroup> getClassifierGroups()
    {
        if(classifierGroups == null)
        {
            classifierGroups = getFormSession().getClassifierGroups();
        }

        return classifierGroups;
    }

    protected EntityProperties getClassifierGroupEntityProperties()
    {
        return getFormSession().getClassifierGroupEntityProperties();
    }

    protected ClassifiersRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(ClassifiersRemote.class);

        return formSession;
    }

    protected int deleteClassifierGroup(ClassifierGroup classifierGroup)
    {
        return getFormSession().deleteClassifierGroup(classifierGroup);
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
            deleteClassifierGroup((ClassifierGroup) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            ClassifierGroupPanel classifierGroupPanel = new ClassifierGroupPanel((ClassifierGroup)rowObject);
            DialogResponse response = classifierGroupPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return classifierGroupPanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (classifierGroupsBindingGroup != null)
            classifierGroupsBindingGroup.unbind();

        classifierGroups = null;

        initData();

        return t;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed())
        {
            ClassifierGroupPanel classifierGroupPanel = new ClassifierGroupPanel(getParentDataObjectId());

            DialogResponse response = classifierGroupPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return classifierGroupPanel.getSelectedValue();
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

        ClassifierGroup classifierGroup = (ClassifierGroup)rowObject;
        if(classifierGroup.getIsSystemGroup())
            return false;

        return true;
    }

}