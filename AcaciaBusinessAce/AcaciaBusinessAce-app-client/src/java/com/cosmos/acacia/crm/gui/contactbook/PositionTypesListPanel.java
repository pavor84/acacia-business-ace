/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.app.AppSession;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.contactbook.impl.PositionTypesListRemote;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class PositionTypesListPanel extends AbstractTablePanel {

    protected static Logger log = Logger.getLogger(PositionTypesListPanel.class);

    /** Creates new form AddresssListPanel */
    public PositionTypesListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
        try
        {
            DataObject parentDataObject = AppSession.getDataObject(parentDataObjectId);
            this.ownerClass = Class.forName(
                parentDataObject.getDataObjectType().getDataObjectType());
        } catch (Exception ex) {
            log.error("constructor", ex);
        }
        postInitData();
    }

    public PositionTypesListPanel(Class ownerClass)
    {
        super(null);
        this.ownerClass = ownerClass;
        postInitData();
    }

    @EJB
    private PositionTypesListRemote formSession;

    private BindingGroup positionTypesBindingGroup;
    private List<PositionType> positionTypes;
    private ContactPerson contactPerson;
    private Class ownerClass;

    @Override
    protected void initData(){

        super.initData();

        setVisible(Button.Select, false);
        positionTypesBindingGroup = new BindingGroup();
        positionTypesBindingGroup.bind();
    }

    protected void postInitData()
    {
        String key = ownerClass == Person.class ? "title.positions.person" : "title.positions.organization";

        setTitle(getResourceMap().getString(key));
        
        AcaciaTable positionTypesTable = getDataTable();
         try {
            JTableBinding tableBinding = positionTypesTable.bind(positionTypesBindingGroup, getPositionTypes(), getPositionTypeEntityProperties());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        positionTypesTable.setEditable(false);
    }
    protected List<PositionType> getPositionTypes() throws Exception
    {
        if(positionTypes == null)
        {
            positionTypes = getFormSession().getPositionTypes(ownerClass);
        }

        return positionTypes;
    }

    protected EntityProperties getPositionTypeEntityProperties()
    {
        return getFormSession().getPositionTypeEntityProperties();
    }

    protected PositionTypesListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(PositionTypesListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deletePositionType(PositionType positionType)
    {
        return getFormSession().deletePositionType(positionType);
    }

     public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
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
            deletePositionType((PositionType) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            PositionTypePanel positionTypePanel =
                    new PositionTypePanel((PositionType) rowObject, ownerClass);
            DialogResponse response = positionTypePanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return positionTypePanel.getSelectedValue();
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        if (positionTypesBindingGroup != null)
            positionTypesBindingGroup.unbind();
        
        positionTypes = null;
        
        initData();
        postInitData();
        
        return t;
    }
        
    @Override
    protected Object newRow() {
        PositionTypePanel positionTypePanel = new PositionTypePanel(getParentDataObjectId(),
                    ownerClass);

        DialogResponse response = positionTypePanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return positionTypePanel.getSelectedValue();
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