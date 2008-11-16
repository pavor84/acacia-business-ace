/*
 * DeliveryCertificateSerialNumbers.java
 *
 * Created on Неделя, 2008, Ноември 9, 15:37
 */

package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * @author  daniel
 */
public class DeliveryCertificateSerialNumbersListPanel extends AbstractTablePanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1226839178863899302L;

	@EJB
    private DeliveryCertificatesRemote formSession;
    
    private EntityProperties entityProps;
    private BindingGroup bidingGroup;
    
    /**
     * Creates new form DeliveryCertificateSerialNumbers
     * @param parentDataObjectId - ID of a DeliveryCertificateItem
     */
    public DeliveryCertificateSerialNumbersListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
        this.setVisibleButtons(8);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getDeliveryCertificateSerialNumberListEntityProperties();
        
        refreshDataTable(entityProps);
          
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        
        if ( bidingGroup!=null )
            bidingGroup.unbind();
        
        bidingGroup = new BindingGroup();
        AcaciaTable deliveryCertTable = getDataTable();
        
        JTableBinding tableBinding = deliveryCertTable.bind(bidingGroup, getDeliveryCertificateItemSerialNumbers(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);
                
        bidingGroup.bind();

    }
    
    private List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers() {
        return getFormSession().getDeliveryCertificateItemSerialNumbers(this.getParentDataObjectId());
    }
        
    @Override
    public boolean canCreate() {
        return false;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return false;
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
    
    @Override
    protected boolean deleteRow(Object rowObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Object newRow() {
        DeliveryCertificateSerialNumber dcsn = getFormSession().newDeliveryCertificateSerialNumber(null);
        return onEditEntity(dcsn);
       
    }

    private Object onEditEntity(DeliveryCertificateSerialNumber dcsn) {
    	throw new UnsupportedOperationException("Not supported yet.");
    }

}
