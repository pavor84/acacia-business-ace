/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

//import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesListRemote;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificate;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author daniel
 */
public class DeliveryCertificatesListPanel extends AbstractTablePanel {

    @EJB
    private DeliveryCertificatesRemote formSession;
    
    private EntityProperties entityProps;
    private BindingGroup bidingGroup;
    private List<DeliveryCertificate> deliveryCertificates;
    
    /**
     * 
     * @param parentDataObjectId - Id of a Warehouse
     */
    public DeliveryCertificatesListPanel(BigInteger parentDataObjectId){
        super(parentDataObjectId);
    }
    
     public DeliveryCertificatesListPanel(){
        this(null);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getDeliveryCertificateListEntityProperties();
        
        refreshDataTable(entityProps);
          
    }
    
    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        
        if ( bidingGroup!=null )
            bidingGroup.unbind();
        
        bidingGroup = new BindingGroup();
        AcaciaTable deliveryCertTable = getDataTable();
        
        JTableBinding tableBinding = deliveryCertTable.bind(bidingGroup, getDeliveryCertificates(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);
                
        bidingGroup.bind();

    }
    
    @Override
    protected boolean deleteRow(Object rowObject) {
        if(rowObject != null)
        {
            DeliveryCertificate deliveryCertificate = (DeliveryCertificate) rowObject;
            getFormSession().deleteDeliveryCertificate(deliveryCertificate);
            return true;
        }
        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            DeliveryCertificatePanel deliveryCertificatePanel = new DeliveryCertificatePanel((DeliveryCertificate)rowObject);
            DialogResponse response = deliveryCertificatePanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return deliveryCertificatePanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        DeliveryCertificate ds = getFormSession().newDeliveryCertificate(getParentDataObjectId());
        return onEditEntity(ds);
       
    }

    private Object onEditEntity(DeliveryCertificate ds) {
        DeliveryCertificatePanel editPanel = new DeliveryCertificatePanel(ds);
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
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
    
       
  
    protected DeliveryCertificatesRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getBean(DeliveryCertificatesRemote.class);
        }

        return formSession;
    }
    
    protected List<DeliveryCertificate> getDeliveryCertificates()
    {
        if(deliveryCertificates == null)
        {
            deliveryCertificates = getFormSession().getDeliveryCertificates(getParentDataObjectId());
        }

        return deliveryCertificates;
    }
    
}
