/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.crm.enums.DeliveryCertificateStatus;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author daniel
 */
public class DeliveryCertificatesListPanel extends AbstractTablePanel<DeliveryCertificate> {

    /**
     *
     */
    private static final long serialVersionUID = 3326774856355545325L;
    @EJB
    private DeliveryCertificatesRemote formSession;
    private EntityProperties entityProps;
    private BindingGroup bidingGroup;
    private List<DeliveryCertificate> deliveryCertificates;

    /**
     * 
     * @param parentDataObjectId - Id of a Warehouse
     */
    public DeliveryCertificatesListPanel(UUID parentDataObjectId) {
        super(parentDataObjectId);
    }

    public DeliveryCertificatesListPanel() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();

        entityProps = getFormSession().getDeliveryCertificateListEntityProperties();

        refreshDataTable(entityProps);

        setVisible(AbstractTablePanel.Button.Classify, false);
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps) {

        if (bidingGroup != null) {
            bidingGroup.unbind();
        }

        bidingGroup = new BindingGroup();
        AcaciaTable deliveryCertTable = getDataTable();

        JTableBinding tableBinding = deliveryCertTable.bind(bidingGroup, getDeliveryCertificates(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bidingGroup.bind();

    }

    @Override
    protected boolean deleteRow(DeliveryCertificate rowObject) {
        if (rowObject != null) {
            getFormSession().deleteDeliveryCertificate(rowObject);
            return true;
        }
        return false;
    }

    @Override
    protected DeliveryCertificate modifyRow(DeliveryCertificate rowObject) {
        if (rowObject != null) {
            DeliveryCertificatePanel deliveryCertificatePanel = new DeliveryCertificatePanel(rowObject);
            DialogResponse response = deliveryCertificatePanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return (DeliveryCertificate) deliveryCertificatePanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected DeliveryCertificate newRow() {
        DeliveryCertificate ds = getFormSession().newDeliveryCertificate(getParentDataObjectId());
        return showDetailForm(ds, true);

    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(DeliveryCertificate rowObject) {
        return DeliveryCertificateStatus.Draft.equals((rowObject).getStatus().getEnumValue());
    }

    @Override
    public boolean canDelete(DeliveryCertificate rowObject) {
        return DeliveryCertificateStatus.Draft.equals((rowObject).getStatus().getEnumValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        refreshDataTable(entityProps);
        return t;
    }

    @Override
    protected void viewRow(DeliveryCertificate rowObject) {
        showDetailForm(rowObject, false);
    }

    protected DeliveryCertificatesRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(DeliveryCertificatesRemote.class);
        }

        return formSession;
    }

    protected List<DeliveryCertificate> getDeliveryCertificates() {
        if (deliveryCertificates == null) {
            deliveryCertificates = getFormSession().getDeliveryCertificates(getParentDataObjectId());
        }

        return deliveryCertificates;
    }

    private DeliveryCertificate showDetailForm(DeliveryCertificate certificate, boolean editable) {
        DeliveryCertificatePanel editPanel = new DeliveryCertificatePanel(certificate);
        DialogResponse response = editPanel.showDialog(this);

        if (!editable) {
            editPanel.setReadonly();
        }

        if (DialogResponse.SAVE.equals(response)) {
            return (DeliveryCertificate) editPanel.getSelectedValue();
        }

        return null;
    }
}
