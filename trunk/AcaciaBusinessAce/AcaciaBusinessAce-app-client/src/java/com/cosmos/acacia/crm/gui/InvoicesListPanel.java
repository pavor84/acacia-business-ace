package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.app.AppSession;
import com.cosmos.acacia.crm.bl.impl.InvoicesListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
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

    public InvoicesListPanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
    }

    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Select, false);

        invoicesBindingGroup = new BindingGroup();
        AcaciaTable invoicesTable = getDataTable();
        EntityProperties entityProp = getInvoiceEntityProperties();
        JTableBinding tableBinding = invoicesTable.bind(invoicesBindingGroup, getInvoices(), getInvoiceEntityProperties());
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

        invoicesTable.setEditable(true);
    }

    protected boolean deleteRow(Object rowObject)
    {
        if(rowObject != null)
        {
            deleteInvoice((Invoice)rowObject);
            return true;
        }

        return false;
    }

    protected Object modifyRow(Object rowObject)
    {
//        if(rowObject != null)
//        {
//            ProductPanel productPanel = new ProductPanel((Product)rowObject);
//            DialogResponse response = productPanel.showDialog(this);
//            if(DialogResponse.SAVE.equals(response))
//            {
//                return productPanel.getSelectedValue();
//            }
//        }

        return null;
    }

    protected Object newRow()
    {
//        ProductPanel productPanel = new ProductPanel(getParentDataObject());
//        DialogResponse response = productPanel.showDialog(this);
//        if(DialogResponse.SAVE.equals(response))
//        {
//            return productPanel.getSelectedValue();
//        }

        return null;
    }

    public boolean canCreate()
    {
        return true;
    }

    public boolean canModify(Object rowObject)
    {
        return true;
    }

    public boolean canDelete(Object rowObject)
    {
        return true;
    }


    protected List<Invoice> getInvoices()
    {
        if(invocies == null)
        {
            DataObject parentDataObject = AppSession.getDataObject(getParentDataObjectId());
            invocies = getFormSession().getInvoices(parentDataObject);
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