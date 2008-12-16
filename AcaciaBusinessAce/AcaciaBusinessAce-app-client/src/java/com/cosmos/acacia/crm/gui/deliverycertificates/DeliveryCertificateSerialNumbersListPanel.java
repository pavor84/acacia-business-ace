/*
 * DeliveryCertificateSerialNumbers.java
 *
 * Created on Неделя, 2008, Ноември 9, 15:37
 */

package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

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
    private List<DeliveryCertificateSerialNumber> serialNumbers;
    
    /**
     * Creates new form DeliveryCertificateSerialNumbers
     * @param parentDataObjectId - ID of a DeliveryCertificateItem
     */
    public DeliveryCertificateSerialNumbersListPanel(BigInteger deliveryCertificateItemId) {
    	super(deliveryCertificateItemId);
        this.setVisibleButtons(8 + 32 + 256);
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
        
        this.serialNumbers = getDeliveryCertificateItemSerialNumbers();	
        JTableBinding tableBinding = deliveryCertTable.bind(bidingGroup, this.serialNumbers, entProps, UpdateStrategy.READ, false);
        tableBinding.setEditable(true);
        deliveryCertTable.setEditable(true);
        
        
        bidingGroup.bind();

    }
    
    private List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers() {
    	
    	BigInteger certificateItemId = this.getParentDataObjectId();
    	List<DeliveryCertificateSerialNumber> list = getFormSession().getDeliveryCertificateItemSerialNumbers(certificateItemId);
    	return list;
    }
    
    @Override
	@Action
	@SuppressWarnings("unchecked")
    public void specialAction(){
		this.serialNumbers = new ArrayList<DeliveryCertificateSerialNumber>(getDataTable().getData());
    	getFormSession().saveDeliveryCertificateItemSerialNumbers(serialNumbers);
    	
    	setDialogResponse(DialogResponse.SAVE);
    	close();
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
        if(rowObject != null){
    		getFormSession().deleteDeliveryCertificateItemSerialNumber((DeliveryCertificateSerialNumber)rowObject);
    		return true;
        }else{
        	return false;
        }
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    protected Object newRow() {
    	throw new UnsupportedOperationException("Not supported.");
    }

}
