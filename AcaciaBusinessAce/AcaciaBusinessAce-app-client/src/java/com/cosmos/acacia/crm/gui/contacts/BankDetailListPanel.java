/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contacts;

import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.contacts.BankDetail;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class BankDetailListPanel extends DetailEntityListPanel<Address, BankDetail> {

    public BankDetailListPanel(EntityPanel<Address> mainEntityPanel) {
        super(mainEntityPanel, BankDetail.class, null);
    }

    public BankDetailListPanel(Address mainEntity) {
        super(mainEntity, BankDetail.class, null);
    }

    @Override
    protected EntityPanel getEntityPanel(BankDetail entity) {
        BankDetailPanel entityPanel = new BankDetailPanel(this, entity);
        return entityPanel;
    }
}
