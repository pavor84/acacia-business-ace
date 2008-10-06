package com.cosmos.acacia.crm.gui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.gui.invoice.InvoiceItemForm;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
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
        AcaciaTable table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps);

        bindGroup.bind();
        table.setEditable(false);
    }
	
	public void refreshList(List<DeliveryCertificateItem> items) {
        this.items = items;
        refreshDataTable(entityProps);
    }
	
	protected List<DeliveryCertificateItem> getItems() {
        if ( items==null ){
            if ( getParentDataObjectId()!=null )
                return getFormSession().getDeliveryCertificateItems(getParentDataObjectId());
            else
                return new ArrayList<DeliveryCertificateItem>();
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
    	System.out.println("Entering Serial Numbers for selected products");
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
		
		if (rowObject != null) {
            DeliveryCertificateItemForm formPanel = new DeliveryCertificateItemForm((DeliveryCertificateItem) rowObject);
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
