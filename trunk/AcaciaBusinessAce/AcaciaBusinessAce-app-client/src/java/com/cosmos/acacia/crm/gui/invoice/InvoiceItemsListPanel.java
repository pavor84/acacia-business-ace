package com.cosmos.acacia.crm.gui.invoice;

import com.cosmos.acacia.crm.bl.invoice.impl.InvoiceItemsListRemote;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author rlozanov
 */
public class InvoiceItemsListPanel extends AbstractTablePanel {
    
    public InvoiceItemsListPanel(BigInteger parentDataObject) {
        super(parentDataObject);
    }
    
    @EJB
    private InvoiceItemsListRemote formSession;

    private BindingGroup invoiceItemsBindingGroup;
    private List<InvoiceItem> invoiceItems;
    
    @Override
    protected void initData() {
        super.initData();
        
        setVisible(Button.Select, false);

        invoiceItemsBindingGroup = new BindingGroup();
        AcaciaTable invoicesTable = getDataTable();
        EntityProperties entityProp = getFormSession().getInvoiceItemsEntityProperties();
        
        JTableBinding tableBinding = invoicesTable.bind(invoiceItemsBindingGroup, getFormSession().getInvoiceItems(
                                                        getParentDataObjectId()), entityProp);
        tableBinding.setEditable(false);
        
        invoicesTable.bindComboBoxCellEditor(invoiceItemsBindingGroup, getFormSession().getMeasureUnits(), entityProp.getPropertyDetails("measureUnit"));

        invoiceItemsBindingGroup.bind();

        invoicesTable.setEditable(false);
        
    }
    
    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null && rowObject instanceof InvoiceItem) {
            getFormSession().deleteInvoiceItem(rowObject);
            return true;
        }
        
        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
            InvoiceItemPanel invoiceItemPanel = new InvoiceItemPanel((InvoiceItem)rowObject);
            DialogResponse response = invoiceItemPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return invoiceItemPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        InvoiceItemPanel invoiceItemPanel = new InvoiceItemPanel(getParentDataObjectId());
        DialogResponse response = invoiceItemPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return invoiceItemPanel.getSelectedValue();
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

    protected InvoiceItemsListRemote getFormSession() {
        if(formSession == null) {
            try {
                formSession = InitialContext.doLookup(InvoiceItemsListRemote.class.getName());
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

}
