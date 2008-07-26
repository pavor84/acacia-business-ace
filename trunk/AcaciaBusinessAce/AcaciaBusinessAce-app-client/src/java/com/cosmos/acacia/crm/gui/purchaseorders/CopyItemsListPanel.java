/**
 * 
 */
package com.cosmos.acacia.crm.gui.purchaseorders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.invoice.impl.InvoiceItemsListRemote;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	23.07.2008
 * @author	Petar Milev
 *
 */
public class CopyItemsListPanel extends AbstractTablePanel {
    
    private BindingGroup bindGroup;
    private List<InvoiceItem> invoiceItems;
    
    public CopyItemsListPanel(BigInteger parentDataObject, List<InvoiceItem> items) {
        super(parentDataObject);
        this.invoiceItems = items;
    }
    
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getInvoiceItemsEntityProperties();
        entityProps.getPropertyDetails("product").setCustomDisplay("${product.productName}");
        refreshDataTable(entityProps);

        setVisible(Button.Select, false);
    }
    
    protected void refreshDataTable(EntityProperties entityProps) {
        if (bindGroup != null)
            bindGroup.unbind();

        bindGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        table.bind(bindGroup, getItems(), entityProps);

        bindGroup.bind();
        table.setEditable(false);
    }
    
    @EJB
    private InvoiceItemsListRemote formSession;
    private EntityProperties entityProps;

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return false;
    }
    
    protected List<InvoiceItem> getItems() {
        if ( invoiceItems==null ){
            if ( getParentDataObjectId()!=null )
                invoiceItems = getFormSession().getInvoiceItems(getParentDataObject());
            else
                invoiceItems = new ArrayList<InvoiceItem>();
        }
        return invoiceItems;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(Object rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        return null;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        return null;
    }
    
    protected InvoiceItemsListRemote getFormSession() {
        if(formSession == null) {
            formSession = getBean(InvoiceItemsListRemote.class);
        }

        return formSession;
    }

    public void refreshList(List<InvoiceItem> items) {
        invoiceItems = items;
        
        refreshDataTable(entityProps);
    }
}
