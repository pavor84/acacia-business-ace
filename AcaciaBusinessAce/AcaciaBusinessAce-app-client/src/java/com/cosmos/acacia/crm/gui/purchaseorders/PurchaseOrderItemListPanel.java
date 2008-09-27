/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.purchaseorders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.contactbook.PersonsListRemote;
import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.bl.purchaseorder.PurchaseOrderListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PurchaseOrderItem;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * Created	:	20.07.2008
 * @author	Petar Milev
 *
 */
public class PurchaseOrderItemListPanel extends AbstractTablePanel {

    private PurchaseOrderListRemote formSession = getBean(PurchaseOrderListRemote.class, false);
    private ProductsListRemote productsListRemote = getBean(ProductsListRemote.class, false);
    private PersonsListRemote personsListRemote = getBean(PersonsListRemote.class, false);

    private List<SimpleProduct> products;
    private List<DbResource> measureUnits;
    private List<PurchaseOrderItem> items;

    private BindingGroup bindGroup;
    private EntityProperties entityProps;

    private int sequence = 1;
    private Random r = new Random();

    /** Creates new form AddresssListPanel */
    public PurchaseOrderItemListPanel(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }

    public PurchaseOrderItemListPanel(DataObjectBean parent) {
        super(parent);
    }

    @Override
    protected void initData() {
        super.initData();

        setSpecialCaption("button.insertFromDocument");
        setVisible(Button.Special, true);
        setSpecialButtonBehavior(true);

        entityProps = getFormSession().getItemsListEntityProperties();

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

    protected List<PurchaseOrderItem> getItems() {
        if ( items==null ){
            if ( getParentDataObjectId()!=null )
                items = getFormSession().getOrderItems(getParentDataObjectId());
            else
                items = new ArrayList<PurchaseOrderItem>();
        }
        return items;
    }

    protected PurchaseOrderListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(PurchaseOrderListRemote.class);
        return formSession;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
        if (rowObject != null) {
            getFormSession().deleteOrderItem((PurchaseOrderItem)rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {

        if (rowObject != null && isEditable()) {
            PurchaseOrderItemForm formPanel = new PurchaseOrderItemForm((PurchaseOrderItem) rowObject);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @Override
    protected Object newRow() {
        if (canNestedOperationProceed()) {
            log.info(getParentDataObjectId());
            PurchaseOrderItem item = getFormSession().newOrderItem(getParentDataObjectId());
            PurchaseOrderItemForm formPanel = new PurchaseOrderItemForm(item);
            DialogResponse response = formPanel.showDialog(this);
            if (DialogResponse.SAVE.equals(response)) {
                return formPanel.getSelectedValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        if (bindGroup != null)
            bindGroup.unbind();

        items = null;
        initData();

        return t;
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

    @Override
    protected void viewRow(Object rowObject) {
        PurchaseOrderItemForm formPanel = new PurchaseOrderItemForm((PurchaseOrderItem) rowObject);
        formPanel.setReadonly();
        formPanel.showDialog(this);
    }

    @Override
    public void specialAction() {
        if ( canNestedOperationProceed() ){
            List<DummyInvoice> dummyInvoices = null;
            try{
                dummyInvoices = createDummyInvoices();
            }catch ( Exception e ){
                e.printStackTrace();
                dummyInvoices = new ArrayList<DummyInvoice>();
            }

            PurchaseOrderItemListPanel orderItemsList = new PurchaseOrderItemListPanel(getParentDataObjectId());
            orderItemsList.getButtonsPanel().setVisible(false);
            OrderItemsCopyForm copyForm = new OrderItemsCopyForm(getParentDataObjectId(), orderItemsList, dummyInvoices);
            DialogResponse response = copyForm.showDialog();
            if ( DialogResponse.SAVE.equals(response) ){
                refreshAction();
            }
        }
    }

    private List<DummyInvoice> createDummyInvoices() {
        List<Person> recipients = personsListRemote.getPersons(null);
        int currentIdx = 0;
        if ( currentIdx>=recipients.size() ) currentIdx=0;

        List<DummyInvoice> result = new ArrayList<DummyInvoice>();

        Random r = new Random();

        //invoice
        Invoice i = new Invoice();
        i.setId(new BigInteger(""+sequence++));
        DummyInvoice di = new DummyInvoice();
        di.invoice = i;
        if ( !recipients.isEmpty() )
            i.setRecipient(recipients.get(currentIdx++));
        if ( currentIdx>=recipients.size() ) currentIdx=0;
        i.setInvoiceNumber(r.nextInt(100000000));
        addDummyInvoiceItems(di);

        result.add(di);

        //invoice
        i = new Invoice();
        i.setId(new BigInteger(""+sequence++));
        di = new DummyInvoice();
        di.invoice = i;
        if ( !recipients.isEmpty() )
            i.setRecipient(recipients.get(currentIdx++));
        if ( currentIdx>=recipients.size() ) currentIdx=0;
        i.setInvoiceNumber(r.nextInt(100000000));
        addDummyInvoiceItems(di);

        result.add(di);

        //invoice
        i = new Invoice();
        i.setId(new BigInteger(""+sequence++));
        di = new DummyInvoice();
        di.invoice = i;
        if ( !recipients.isEmpty() )
            i.setRecipient(recipients.get(currentIdx++));
        if ( currentIdx>=recipients.size() ) currentIdx=0;
        i.setInvoiceNumber(r.nextInt(100000000));
        addDummyInvoiceItems(di);

        result.add(di);

        return result;
    }

    public static class DummyInvoice{
        public Invoice invoice;
        public List<InvoiceItem> items;
    }

    private void addDummyInvoiceItems(DummyInvoice i) {

        int howMuch = r.nextInt(5);
        howMuch++;

        i.items = new ArrayList<InvoiceItem>();

        Set<SimpleProduct> addedProductsForInvoice = new HashSet<SimpleProduct>();

        for (int j = 0; j < howMuch; j++) {
            InvoiceItem item = new InvoiceItem();
            item.setId(new BigInteger(""+sequence++));
            item.setMeasureUnit(getRandomMeasureUnit());
            int ordered = r.nextInt(10)+1;
            ordered*=10;
            if ( ordered<0 )
                ordered = -ordered;
            item.setOrderedQuantity(new BigDecimal(""+ordered));
            SimpleProduct randomProduct = null;
            for (int k = 0; k < 5; k++) {
                randomProduct = getRandomProduct();
                if ( addedProductsForInvoice.contains(randomProduct)){
                    if ( k==4 )
                        return;
                    else
                        continue;
                }
                else
                    break;
            }
            addedProductsForInvoice.add(randomProduct);
            item.setProduct(randomProduct);
            if ( r.nextBoolean() && ordered>10 )
                item.setShippedQuantity(new BigDecimal(ordered-10));
            else
                item.setShippedQuantity(new BigDecimal(0));
            int unitPrice = r.nextInt(10)+1;
            unitPrice*=10;
            if ( unitPrice<0 )
                unitPrice = -unitPrice;

            item.setExtendedPrice(new BigDecimal(""+(unitPrice*ordered)));

            item.setUnitPrice(new BigDecimal(unitPrice));

            i.items.add(item);
        }
    }

    private SimpleProduct getRandomProduct() {
        if ( products==null )
            products = productsListRemote.getProducts(getOrganizationDataObjectId());
        return products.get(r.nextInt(products.size()));
    }

    private DbResource getRandomMeasureUnit() {
        if ( measureUnits==null )
            measureUnits = productsListRemote.getMeasureUnits();
        return measureUnits.get(r.nextInt(measureUnits.size()));
    }

    public void refreshList(List<PurchaseOrderItem> orderItems) {
        this.items = orderItems;

        refreshDataTable(entityProps);
    }

    /**
     * Makes the list panel as read-only.
     * This means that all buttons for modify operations will be hidden
     */
    public void setReadonly() {
        super.setReadonly();
        getButton(Button.Special).setVisible(false);
    }
}