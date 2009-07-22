/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.JBCheckBox;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBTextField;
import java.util.List;
import javax.swing.JComponent;
import org.jdesktop.beansbinding.PropertyStateEvent;

/**
 *
 * @author Miro
 */
public class BusinessUnitPanel extends EntityPanel<BusinessUnit> {

    protected static final String ROOT_PROPERTY_NAME = "root";
    protected static final String DISABLED_PROPERTY_NAME = "disabled";
    protected static final String BUSINESS_UNIT_TYPE_PROPERTY_NAME = "businessUnitType";
    protected static final String PARENT_BUSINESS_UNIT_PROPERTY_NAME = "parentBusinessUnit";
    protected static final String BUSINESS_UNIT_NAME_PROPERTY_NAME = "businessUnitName";
    protected static final String DIVISION_NAME_PROPERTY_NAME = "divisionName";
    protected static final String WEB_SITE_PROPERTY_NAME = "webSite";
    protected static final String NOTES_PROPERTY_NAME = "notes";
    protected static final String ORGANIZATION_PROPERTY_NAME = "organization";

    public BusinessUnitPanel(AbstractEntityListPanel entityListPanel, BusinessUnit entity) {
        super(entityListPanel, entity);
    }

    @Override
    protected void initData() {
        super.initData();

        if(getEntity().isRoot()) {
            getJComponentByPropertyName(DISABLED_PROPERTY_NAME, JBCheckBox.class).setEnabled(false);
            getJComponentByPropertyName(BUSINESS_UNIT_TYPE_PROPERTY_NAME, JBComboBox.class).setEnabled(false);
            getJComponentByPropertyName(PARENT_BUSINESS_UNIT_PROPERTY_NAME, JBComboList.class).setEnabled(false);
            getJComponentByPropertyName(BUSINESS_UNIT_NAME_PROPERTY_NAME, JBTextField.class).setEnabled(false);
            getJComponentByPropertyName(DIVISION_NAME_PROPERTY_NAME, JBTextField.class).setEnabled(false);
        }
    }

    @Override
    protected void entityChanged(String propertyName, JComponent jComponent, PropertyStateEvent event) {
    }

    @Override
    public void performSave(boolean closeAfter) {
        boolean isNew = getEntity().getBusinessUnitId() == null;
        super.performSave(closeAfter);
        if(!closeAfter && isNew) {
            refreshAddresses();
        }
    }

    protected void refreshAddresses() {
        getJComponent("addressList", BusinessUnitAddressListPanel.class).refresh();
    }
}
