/**
 * 
 */
package com.cosmos.acacia.crm.gui.purchaseorders;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.sales.SalesInvoiceItem;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	23.07.2008
 * @author	Petar Milev
 *
 */
public class CopyItemsListPanel extends AbstractTablePanel<SalesInvoiceItem> {
    
    private BindingGroup bindGroup;
    private List<?> items;
    
    public CopyItemsListPanel(UUID parentDataObject) {
        super(parentDataObject);
    }
    
    @Override
    protected void initData() {
        super.initData();
        
        entityProps = getFormSession().getItemsListEntityProperties();
        entityProps.getEntityProperty("product").setCustomDisplay("${product.productName}");
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
    private InvoiceListRemote formSession;
    private EntityProperties entityProps;

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return false;
    }
    
    @SuppressWarnings("unchecked")
    protected List<?> getItems() {
        if ( items==null )
            return new ArrayList();
        return items;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(SalesInvoiceItem rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(SalesInvoiceItem rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(SalesInvoiceItem rowObject) {
        return false;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected SalesInvoiceItem modifyRow(SalesInvoiceItem rowObject) {
        return null;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected SalesInvoiceItem newRow() {
        return null;
    }
    
    protected InvoiceListRemote getFormSession() {
        if(formSession == null) {
            formSession = getBean(InvoiceListRemote.class);
        }

        return formSession;
    }

    public void refreshList(List<?> items) {
        this.items = items;
        
        refreshDataTable(entityProps);
    }
}
