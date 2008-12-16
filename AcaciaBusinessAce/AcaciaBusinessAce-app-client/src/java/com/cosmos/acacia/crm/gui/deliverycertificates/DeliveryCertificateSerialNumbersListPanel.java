/*
 * DeliveryCertificateSerialNumbers.java
 *
 * Created on Неделя, 2008, Ноември 9, 15:37
 */

package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ejb.EJB;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.crm.gui.ProductPanel;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.JBErrorPane;

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
        this.setVisibleButtons(8 + 32);
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
        JTableBinding tableBinding = deliveryCertTable.bind(bidingGroup, this.serialNumbers, entProps, UpdateStrategy.READ, true);
        tableBinding.setEditable(true);
        deliveryCertTable.setEditable(true);
        
        tableBinding.addBindingListener(new AbstractBindingListener(){
        	@SuppressWarnings("unchecked")
            @Override
            public void targetChanged(Binding binding, PropertyStateEvent event) {
        		String serialNumber = (String)event.getNewValue();
        		System.out.println("Saving Serial Number: " + serialNumber);
        		DeliveryCertificateSerialNumber entity = (DeliveryCertificateSerialNumber)binding.getSourceObject();
        		entity.setSerialNumber(serialNumber);
        		save(entity);
            }
        });
        
        bidingGroup.bind();

    }
    
    private void save(DeliveryCertificateSerialNumber entity){
    	try{
    		getFormSession().saveDeliveryCertificateItemSerialNumber(entity);
    	}
    	catch(ValidationException e){
    		ValidationException ve = extractValidationException(e);
            if ( ve!=null ){
                String message = getValidationErrorsMessage(ve);
                JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
            }else{
                e.printStackTrace();
                // TODO: Log that error
                String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", e.getMessage());
                JBErrorPane.showDialog(this, createSaveErrorInfo(basicMessage, e));
            }
    	}
    }
    
    private ErrorInfo createSaveErrorInfo(String basicMessage, Exception ex) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("saveAction.Action.error.title");

        String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
        String category = DeliveryCertificateSerialNumbersListPanel.class.getName() + ": saveAction.";
        Level errorLevel = Level.WARNING;

        Map<String, String> state = new HashMap<String, String>();
        //state.put("serialNumber", String.valueOf(entity.));
        
        ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
        return errorInfo;
    }
    
    private List<DeliveryCertificateSerialNumber> getDeliveryCertificateItemSerialNumbers() {
    	
    	BigInteger certificateItemId = this.getParentDataObjectId();
    	List<DeliveryCertificateSerialNumber> list = getFormSession().getDeliveryCertificateItemSerialNumbers(certificateItemId);
    	return list;
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
