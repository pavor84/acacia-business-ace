package com.cosmos.acacia.crm.gui.deliverycertificates;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.DeliveryCertificateSerialNumber;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

public class DeliveryCertificateItemListPanel extends AbstractTablePanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4986063572371186468L;

	@EJB
    private DeliveryCertificatesRemote formSession;
	
	public List<DeliveryCertificateItem> items;
	
	private BindingGroup bindGroup;
	
	private EntityProperties entityProps;
	AcaciaTable table = null;
	
	public DeliveryCertificateItemListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }
	
	@Override
    protected void initData() {

        super.initData();

        entityProps = getFormSession().getDeliveryCertificateItemsEntityProperties();

        refreshDataTable(entityProps);
      
    }
	
	public void refreshDataTable(EntityProperties entityProps) {
        if (bindGroup != null)
            bindGroup.unbind();

        bindGroup = new BindingGroup();
        table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps, true);

        bindGroup.bind();
        table.setEditable(false);
    }
	
	public void refreshList(List<DeliveryCertificateItem> items) {
        this.items = items;
        refreshDataTable(entityProps);
    }
	
	protected List<DeliveryCertificateItem> getItems() {
        if ( items ==null ){
            if ( getParentDataObjectId()!=null ){
            	List<DeliveryCertificateItem> l = getFormSession().getDeliveryCertificateItems(getParentDataObjectId());
            	return l;
            }
            else{
            	return new ArrayList<DeliveryCertificateItem>();
            }
        }
        return items;
    }
	
	protected DeliveryCertificatesRemote getFormSession(){
        if(formSession == null){
            formSession = getBean(DeliveryCertificatesRemote.class);
        }

        return formSession;
    }
	
	@Override
	@Action
    public void specialAction(){
		try{
			DeliveryCertificateItem item = (DeliveryCertificateItem)getDataTable().getSelectedRowObject();
			BigDecimal quantity = item.getQuantity();
			
			//keep the index of the selected item
			int index = getDataTable().getSelectedRow();
			
			//if the quantity is not integer, an exception will be thrown. Some products do not have serial numbers 
			quantity.intValueExact();
			
			if(canNestedOperationProceed()){
				//ID of the delivery certificate
				BigInteger deliveryCertificateId = getParentDataObjectId();
				
				if(item.getCertificateItemId() == null){
					//new certificate is just created and get the fully instantiated certificate item (with ID etc)
					item = getFormSession().getDeliveryCertificateItems(deliveryCertificateId).get(index);
				}
				
				DeliveryCertificateSerialNumbersListPanel panel = new DeliveryCertificateSerialNumbersListPanel(item.getCertificateItemId());
				DialogResponse response = panel.showDialog(this);
				if(DialogResponse.SAVE.equals(response)){
					List<DeliveryCertificateSerialNumber> serialNumbers = panel.getSerialNumbers();
				}
			}
		}
		catch(ArithmeticException e){
			//the exception is thrown when quantity has a nonzero fractional part, or will not fit in an int.
			JOptionPane.showMessageDialog(this, "The selected product do not have serial numbers.");
		}

    }
	
	@Override
    public boolean canModify(Object rowObject) {
        return true;
    }

	@Override
    public boolean canDelete(Object rowObject) {
        return false;
    }
	
	@Override
    public boolean canCreate() {
        return false;
    }
	
	@Override
	protected Object modifyRow(Object rowObject) {
		
		//selected index from the list of delivery certificate items
		int index = getDataTable().getSelectedRow();
		if (rowObject != null && canNestedOperationProceed()) {
			DeliveryCertificateItem item = (DeliveryCertificateItem) rowObject;
			if(item.getCertificateItemId() == null){
				//new certificate is just created and get the fully instantiated certificate item (with ID etc)
				item = getFormSession().getDeliveryCertificateItems(getParentDataObjectId()).get(index);
			}
            DeliveryCertificateItemForm formPanel = new DeliveryCertificateItemForm(item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return formPanel.getSelectedValue();
            }
        }
        return null;
	}
	
	@Override
	protected boolean deleteRow(Object rowObject) {
		return false;
	}
	
	@Override
	protected Object newRow() {
		return null;
	}
	
}
