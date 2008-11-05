/*
 * InvoiceItemsCopyFormDraft.java
 *
 * Created on Вторник, 2008, Септември 2, 11:54
 */

package com.cosmos.acacia.crm.gui.invoice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import com.cosmos.acacia.crm.bl.invoice.InvoiceListRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.acacia.crm.data.InvoiceItem;
import com.cosmos.acacia.crm.data.InvoiceItemLink;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.gui.purchaseorders.CopyItemsListPanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	01.10.2008
 * @author	Petar Milev
 *
 */
public class InvoiceItemsCopyForm extends AcaciaPanel {

    private InvoiceItemListPanel invoiceItemsListPanel;
    private List<?> templateDocuments;
    private Object selectedDocument;
    
    @EJB
    private InvoiceListRemote formSession = getBean(InvoiceListRemote.class);
    private Invoice invoice;
    
    private boolean proform = false;

    /** Creates new form InsertFromDocumentForm 
     * @param invoice 
     * @param templateDocuments */
    public InvoiceItemsCopyForm(BigInteger parentId, InvoiceItemListPanel invoiceItemsPanel, Invoice invoice) {
        super(parentId);
        if ( invoiceItemsPanel==null )
            throw new IllegalArgumentException("cant be null: 'invoiceItemsPanel'");
        if ( invoice==null )
            throw new IllegalArgumentException("invoice can not be null");
        this.invoice = invoice;
        proform = Boolean.TRUE.equals(invoice.getProformaInvoice());
        this.invoiceItemsListPanel = invoiceItemsPanel;
        this.templateDocuments = new ArrayList<Object>();
        initComponents();
        initComponentsCustom();
        initData();
    }

    private void initComponentsCustom() {
        //hide the checkbox for proforma or sales offer, if this is a proforma
        if ( proform ){
            jBLabel4.setVisible(false);
            proformaInvoicesCheckField.setVisible(false);
            salesOffersCheckField.setVisible(false);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        copySelectedButton = new com.cosmos.swingb.JBButton();
        closeButton = new com.cosmos.swingb.JBButton();
        proformaInvoicesCheckField = new com.cosmos.swingb.JBCheckBox();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        documentField = new com.cosmos.acacia.gui.AcaciaComboBox();
        itemsToCopyTableHolder = new com.cosmos.acacia.gui.TableHolderPanel();
        invoiceItemsTableHolder = new com.cosmos.acacia.gui.TableHolderPanel();
        salesOffersCheckField = new com.cosmos.swingb.JBCheckBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(InvoiceItemsCopyForm.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        copySelectedButton.setText(resourceMap.getString("copySelectedButton.text")); // NOI18N
        copySelectedButton.setName("copySelectedButton"); // NOI18N

        closeButton.setIcon(resourceMap.getIcon("closeButton.icon")); // NOI18N
        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        proformaInvoicesCheckField.setText(resourceMap.getString("proformaInvoicesCheckField.text")); // NOI18N
        proformaInvoicesCheckField.setName("proformaInvoicesCheckField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        documentField.setName("documentField"); // NOI18N

        itemsToCopyTableHolder.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("itemsToCopyTableHolder.border.title"))); // NOI18N
        itemsToCopyTableHolder.setName("itemsToCopyTableHolder"); // NOI18N

        javax.swing.GroupLayout itemsToCopyTableHolderLayout = new javax.swing.GroupLayout(itemsToCopyTableHolder);
        itemsToCopyTableHolder.setLayout(itemsToCopyTableHolderLayout);
        itemsToCopyTableHolderLayout.setHorizontalGroup(
            itemsToCopyTableHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
        );
        itemsToCopyTableHolderLayout.setVerticalGroup(
            itemsToCopyTableHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );

        invoiceItemsTableHolder.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("invoiceItemsTableHolder.border.title"))); // NOI18N
        invoiceItemsTableHolder.setName("invoiceItemsTableHolder"); // NOI18N

        javax.swing.GroupLayout invoiceItemsTableHolderLayout = new javax.swing.GroupLayout(invoiceItemsTableHolder);
        invoiceItemsTableHolder.setLayout(invoiceItemsTableHolderLayout);
        invoiceItemsTableHolderLayout.setHorizontalGroup(
            invoiceItemsTableHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
        );
        invoiceItemsTableHolderLayout.setVerticalGroup(
            invoiceItemsTableHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        salesOffersCheckField.setText(resourceMap.getString("salesOffersCheckField.text")); // NOI18N
        salesOffersCheckField.setName("salesOffersCheckField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(invoiceItemsTableHolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(itemsToCopyTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(proformaInvoicesCheckField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(salesOffersCheckField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(documentField, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(373, 373, 373)
                        .addComponent(copySelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(790, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proformaInvoicesCheckField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesOffersCheckField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(documentField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemsToCopyTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(copySelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(invoiceItemsTableHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBButton copySelectedButton;
    private com.cosmos.acacia.gui.AcaciaComboBox documentField;
    private com.cosmos.acacia.gui.TableHolderPanel invoiceItemsTableHolder;
    private com.cosmos.acacia.gui.TableHolderPanel itemsToCopyTableHolder;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBCheckBox proformaInvoicesCheckField;
    private com.cosmos.swingb.JBCheckBox salesOffersCheckField;
    // End of variables declaration//GEN-END:variables
    
    private CopyItemsListPanel copyItemsListPanel;
    
    boolean pendingRadioSelected = true;
    
    @Override
    protected void initData() {
        
        setDialogResponse(DialogResponse.CANCEL);
        
        proformaInvoicesCheckField.setSelected(true);
        salesOffersCheckField.setSelected(true);
        
        ActionListener updateTemplateDocsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTemplateDocumentsList();
            }
        };
        
        //salesOffersCheckField.addActionListener(updateTemplateDocsListener);//TODO when sale offers are ready
        proformaInvoicesCheckField.addActionListener(updateTemplateDocsListener);
        
        updateTemplateDocumentsList();
        
        documentField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if ( ItemEvent.SELECTED == e.getStateChange() && e.getItem() instanceof DataObjectBean )
                    onTemplateDocumentSelected((DataObjectBean)e.getItem());
                else if ( ItemEvent.DESELECTED==e.getStateChange() )
                    onTemplateDocumentSelected(null);
            }
        });
        
        copyItemsListPanel = new CopyItemsListPanel(null);
        copyItemsListPanel.setVisibleButtons(0);
        copyItemsListPanel.getDataTable().setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        itemsToCopyTableHolder.add(copyItemsListPanel);
        
        invoiceItemsTableHolder.add(invoiceItemsListPanel);
        
        onTemplateDocumentSelected(documentField.getSelectedItem());
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        
        copySelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCopySelectedButton();
            }
        });
    }
    
    

    protected void updateTemplateDocumentsList() {
        templateDocuments = getTemplateDocuments(true, proformaInvoicesCheckField.isSelected(), salesOffersCheckField.isSelected());
        
        //and then bind again
        documentField.bind(new BindingGroup(), templateDocuments, this, 
            new PropertyDetails("selectedDocument", "Selected Document", Object.class.getName()),
            new ObjectToStringConverter() {
                @Override
                public String getPreferredStringForItem(Object templateDocument) {
                    return getTemplateDocumentDisplay(templateDocument);
                }
            }).bind();
    }

    protected void onTemplateDocumentSelected(Object document) {
        List<?> items = null;
        if ( document==null ){
            items = new ArrayList<Object>();
            copySelectedButton.setEnabled(false);
        }
        else{
            items = getFormSession().getDocumentItems((DataObjectBean)document);
            copySelectedButton.setEnabled(true);
        }
        copyItemsListPanel.refreshList(items);
    }

    protected String getTemplateDocumentDisplay(Object templateDocument) {
        String result = "";
        if ( templateDocument instanceof Invoice ){
            Invoice invoice = (Invoice) templateDocument;
            result = invoice.getInvoiceNumber()==null?"":(invoice.getInvoiceNumber()+"-") +
                    invoice.getRecipientName();
            if ( Boolean.TRUE.equals(invoice.getProformaInvoice()) ) 
                result+=" ("+getResourceMap().getString("documentType.proforma")+")";
            else
                result+=" ("+getResourceMap().getString("documentType.invoice")+")";
        }
        //handle other types of documents ( for ex. sales offers )
        //else if ( templateDocument instanceof ){}
        else if (templateDocument instanceof DataObjectBean){
            result = "unknown document: "+templateDocument;
        }else if ( templateDocument instanceof String ){
            result = (String) templateDocument;
        }
        
        return result;
    }

    private List<InvoiceItemLink> allItemsLinks = null;  

    @SuppressWarnings("unchecked")
    protected void copyItems(List<?> selectedItems){
        List<?> itemsToCopy = new ArrayList<Object>(selectedItems);
        if ( itemsToCopy==null || itemsToCopy.isEmpty() )
            return;
        
        if ( allItemsLinks==null )
            allItemsLinks = getAllItemsLinks();
        
        Set<BigInteger> alreadyAddedIds = new HashSet<BigInteger>();
        for (InvoiceItemLink itemLink : allItemsLinks) {
            alreadyAddedIds.add(itemLink.getTemplateItemId());
        }
        boolean alreadyAddedPresent = false;
        for (Iterator iterator = itemsToCopy.iterator(); iterator.hasNext();) {
            Object item = (Object) iterator.next();
            BigInteger selectedItemId = ((DataObjectBean) item).getId();
            if (alreadyAddedIds.contains(selectedItemId)) {
                alreadyAddedPresent = true;
                iterator.remove();
            }
        }
        
        try{
            if ( alreadyAddedPresent ){
                if ( showAlreadyAddedWarning() ){
                    addItems(itemsToCopy);
                }
            }else{
                addItems(itemsToCopy);
            }
            
            setDialogResponse(DialogResponse.SAVE);
        }catch (Exception ex){
            handleBusinessException(ex);
        }
    }
    
    private List<InvoiceItemLink> getAllItemsLinks() {
        List<InvoiceItemLink> result = new ArrayList<InvoiceItemLink>();
        List<InvoiceItem> items = invoiceItemsListPanel.getItems();
        for (InvoiceItem invoiceItem : items) {
            List<InvoiceItemLink> itemLinks = getFormSession().getInvoiceItemLinks(invoiceItem);
            result.addAll(itemLinks);
        }
        return result;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected void onCopySelectedButton() {
        List<?> selectedItems = copyItemsListPanel.getDataTable().getData();
        copyItems(selectedItems);
        updateTemplateDocumentsList();
    }
    
    private InvoiceListRemote invoiceListRemote = getBean(InvoiceListRemote.class);

    private void addItems(List<?> items) {
        List<InvoiceItemLink> itemLinks = new ArrayList<InvoiceItemLink>();
        for (Object item : items) {
            if ( !( item instanceof DataObjectBean ) ){
                throw new IllegalStateException("A document item should be DataObjectBean!");
            }
            InvoiceItem clonedItem = getClonedItemForItem(item);
            InvoiceItemLink invoiceItemLink = 
                new InvoiceItemLink(((DataObjectBean)item).getId(),
                    ((DataObjectBean)item).getParentId(),
                    clonedItem);
            BigDecimal itemQuantity = getItemQuantity(item); 
            //add only the needed quantity (the quantity that is still not shipped)
            clonedItem.setOrderedQuantity(itemQuantity);
            itemLinks.add(invoiceItemLink);
        }
        
        invoiceListRemote.addInvoiceItems(itemLinks);
        invoiceItemsListPanel.refreshAction();
        
        allItemsLinks.addAll(itemLinks);
    }

    private BigDecimal getItemQuantity(Object item) {
        if ( item instanceof InvoiceItem ){
            return ((InvoiceItem)item).getOrderedQuantity();
        }
        throw new IllegalArgumentException("Unknown item type: "+item);
    }

    @SuppressWarnings("unchecked")
    private InvoiceItem getClonedItemForItem(Object documentItem) {
        @SuppressWarnings("unused")
        List<InvoiceItem> currentItems = invoiceItemsListPanel.getDataTable().getData();
        InvoiceItem item = null; //currently - always create new item
        Product documentItemProduct = getProductForItem(documentItem);
//        for (InvoiceItem currentItem : currentItems) {
//            Product product = currentItem.getProduct();
//            //find for this product
//            if ( product.equals(documentItemProduct) ){
//                item = currentItem;
//                break;
//            }
//        }
        
        //create new item
        if ( item==null ){
            item = invoiceListRemote.newInvoiceItem(getParentDataObjectId());
            if ( item.getOrderedQuantity()==null )
                item.setOrderedQuantity(new BigDecimal(0));
            item.setProduct(documentItemProduct);
            
            //get the measurement unit from the invoice item
            DbResource measureMentUnit = 
                (DbResource) ELProperty.create("${measureUnit}").getValue(documentItem);
            item.setMeasureUnit(measureMentUnit);
            item.setDiscountAmount((BigDecimal) getItemProperty(documentItem, "discountAmount"));
            item.setDiscountPercent((BigDecimal) getItemProperty(documentItem, "discountPercent"));
            item.setExtendedPrice((BigDecimal) getItemProperty(documentItem, "extendedPrice"));
            item.setUnitPrice((BigDecimal) getItemProperty(documentItem, "unitPrice"));
            
            //get the sale price from product and warehouse product
//            BigDecimal salePrice = null;
//            if ( documentItemProduct instanceof SimpleProduct ){
//                WarehouseProduct warehouseProduct = invoiceListRemote.getWarehouseProduct((SimpleProduct) documentItemProduct);
//                salePrice = documentItemProduct.getSalePrice();
//                if ( warehouseProduct!=null ){
//                    if ( warehouseProduct.getSalePrice()!=null )
//                        salePrice = warehouseProduct.getSalePrice();
//                }
//            }else if ( documentItemProduct instanceof ComplexProduct ){
//                salePrice = ((ComplexProduct)documentItemProduct).getSalePrice();
//            }
//            item.setUnitPrice(salePrice);
        //already an item for this product - nothing to do
        }else{
            //nothing specific
        }
        
        return item;
    }

    private Object getItemProperty(Object documentItem, String propertyName) {
        Object result = null;
        
        try{
            result = ELProperty.create("${"+propertyName+"}").getValue(documentItem);
        }catch ( Exception e ){
            //nothing to do
        }
        
        return result;
    }

    private Product getProductForItem(Object documentItem) {
        if ( documentItem instanceof InvoiceItem ){
            return ((InvoiceItem)documentItem).getProduct();
        }
        throw new IllegalArgumentException("Not supported document item: "+documentItem);
    }

    private boolean showAlreadyAddedWarning() {
        int result = JOptionPane.showConfirmDialog(
            this.getParent(),
            getResourceMap().getString("copyAction.alreadyAdded.message"),
            getResourceMap().getString("copyAction.alreadyAdded.title"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if(JOptionPane.YES_OPTION == result)
    {
        return true;
    }
    return false;
    }
    
    @SuppressWarnings("unchecked")
    private List<?> getTemplateDocuments(boolean includeInvoices, boolean includeProformas, boolean includeSalesOffers ) {
        templateDocuments = getFormSession().getTemplateDocuments(invoice, includeInvoices, includeProformas);
        
        return templateDocuments;
    }

    protected void onInvoiceChanged(Invoice invoice) {
        List<?> items = getFormSession().getDocumentItems(invoice);
        copyItemsListPanel.refreshList(items);
    }
    
    protected Object getSelectedDocument() {
        return selectedDocument;
    }

    protected void setSelectedDocument(Object selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    protected InvoiceListRemote getFormSession() {
        return formSession;
    }
}
