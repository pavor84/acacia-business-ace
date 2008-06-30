package com.cosmos.acacia.crm.gui.invoice;

import com.cosmos.acacia.crm.bl.invoice.impl.InvoicesListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
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
 * @author Radoslav Lozanov
 */
public class InvoicesListPanel
    extends AbstractTablePanel
{
    @EJB
    private InvoicesListRemote formSession;

    private BindingGroup invoicesBindingGroup;
    private List<Invoice> invocies;

    public InvoicesListPanel(BigInteger parentDataObject)
    {
        super(parentDataObject);
    }

    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Select, false);

        invoicesBindingGroup = new BindingGroup();
        AcaciaTable invoicesTable = getDataTable();
        EntityProperties entityProp = getInvoiceEntityProperties();
        JTableBinding tableBinding = invoicesTable.bind(invoicesBindingGroup, getInvoices(), entityProp);
        tableBinding.setEditable(false);
        
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getCurrencies(), entityProp.getPropertyDetails("currency"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getDeliveryTypes(), entityProp.getPropertyDetails("deliveryType"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getDocumentDeliveryMethods(), entityProp.getPropertyDetails("documentDeliveryMethod"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getInvoiceStatuses(), entityProp.getPropertyDetails("statusId"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getInvoiceTypes(), entityProp.getPropertyDetails("invoiceType"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getPaymentTerms(), entityProp.getPropertyDetails("paymentTerms"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getPaymentTypes(), entityProp.getPropertyDetails("paymentType"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getTransportationMethods(), entityProp.getPropertyDetails("transportationMethod"));
        invoicesTable.bindComboBoxCellEditor(invoicesBindingGroup, getVatConditions(), entityProp.getPropertyDetails("vatCondition"));

        invoicesBindingGroup.bind();

        invoicesTable.setEditable(false);
    }

    @Override
    protected boolean deleteRow(Object rowObject)
    {
        if(rowObject != null)
        {
            deleteInvoice((Invoice)rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject)
    {
        if(rowObject != null)
        {
            InvoicePanel invoicetPanel = new InvoicePanel((Invoice)rowObject);
            DialogResponse response = invoicetPanel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return invoicetPanel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow()
    {
        InvoicePanel invoicetPanel = new InvoicePanel(getParentDataObjectId());
        DialogResponse response = invoicetPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return invoicetPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    public boolean canCreate()
    {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject)
    {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject)
    {
        return true;
    }


    protected List<Invoice> getInvoices()
    {
        if(invocies == null)
        {
            invocies = getFormSession().getInvoices(getParentDataObject());
        }

        return invocies;
    }


    protected EntityProperties getInvoiceEntityProperties()
    {
        return getFormSession().getInvoiceEntityProperties();
    }

    protected InvoicesListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(InvoicesListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    protected int deleteInvoice(Invoice invoice)
    {
        return getFormSession().deleteInvoice(invoice);
    }
    
    protected List<DbResource> getCurrencies() {
        return getFormSession().getCurrencies();
    }
    
    protected List<DbResource> getDeliveryTypes() {
        return getFormSession().getDeliveryTypes();
    }
    
    protected List<DbResource> getDocumentDeliveryMethods() {
        return getFormSession().getDocumentDeliveryMethods();
    }
    
    protected List<DbResource> getInvoiceStatuses() {
        return getFormSession().getInvoiceStatuses();
    }
    
    protected List<DbResource> getInvoiceTypes() {
        return getFormSession().getInvoiceTypes();
    }
    
    protected List<DbResource> getPaymentTerms() {
        return getFormSession().getPaymentTerms();
    }
    
    protected List<DbResource> getPaymentTypes() {
        return getFormSession().getPaymentTypes();
    }
    
    protected List<DbResource> getTransportationMethods() {
        return getFormSession().getTransportationMethods();
    }
    
    protected List<DbResource> getVatConditions() {
        return getFormSession().getVatConditions();
    }

}