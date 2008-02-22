/*
 * ProductPanel.java
 *
 * Created on Четвъртък, 2008, Февруари 14, 19:15
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.BeanResource;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBErrorPane;
import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 *
 * @author  miro
 */
public class ProductPanel extends AcaciaPanel {

    public ProductPanel(Product product) {
        super(product.getDataObject().getParentDataObject());
        this.product = product;
        init();
    }

    /** Creates new form ProductPanel */
    public ProductPanel(DataObject parentDataObject) {
        super(parentDataObject);
        init();
    }

    private void init()
    {
        initComponents();
        initData();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productNameLabel = new com.cosmos.swingb.JBLabel();
        productNameTextField = new com.cosmos.swingb.JBTextField();
        productCodeTextField = new com.cosmos.swingb.JBTextField();
        productCodeLabel = new com.cosmos.swingb.JBLabel();
        measureUnitComboBox = new com.cosmos.swingb.JBComboBox();
        productCategoryLabel = new com.cosmos.swingb.JBLabel();
        buttonPanel = new com.cosmos.swingb.JBPanel();
        closelButton = new com.cosmos.swingb.JBButton();
        saveButton = new com.cosmos.swingb.JBButton();
        productCategoryComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        measureUnitLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductPanel.class);
        productNameLabel.setText(resourceMap.getString("productNameLabel.text")); // NOI18N
        productNameLabel.setName("productNameLabel"); // NOI18N

        productNameTextField.setName("productNameTextField"); // NOI18N

        productCodeTextField.setName("productCodeTextField"); // NOI18N

        productCodeLabel.setText(resourceMap.getString("productCodeLabel.text")); // NOI18N
        productCodeLabel.setName("productCodeLabel"); // NOI18N

        measureUnitComboBox.setName("measureUnitComboBox"); // NOI18N

        productCategoryLabel.setText(resourceMap.getString("productCategoryLabel.text")); // NOI18N
        productCategoryLabel.setName("productCategoryLabel"); // NOI18N

        buttonPanel.setName("buttonPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(ProductPanel.class, this);
        closelButton.setAction(actionMap.get("closeAction")); // NOI18N
        closelButton.setName("closelButton"); // NOI18N

        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(339, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {closelButton, saveButton});

        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {closelButton, saveButton});

        productCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productCategoryComboBox.setName("productCategoryComboBox"); // NOI18N

        measureUnitLabel.setText(resourceMap.getString("measureUnitLabel.text")); // NOI18N
        measureUnitLabel.setName("measureUnitLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(measureUnitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(measureUnitComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                    .addComponent(productCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(productCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(productCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(measureUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measureUnitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel buttonPanel;
    private com.cosmos.swingb.JBButton closelButton;
    private com.cosmos.swingb.JBComboBox measureUnitComboBox;
    private com.cosmos.swingb.JBLabel measureUnitLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox productCategoryComboBox;
    private com.cosmos.swingb.JBLabel productCategoryLabel;
    private com.cosmos.swingb.JBLabel productCodeLabel;
    private com.cosmos.swingb.JBTextField productCodeTextField;
    private com.cosmos.swingb.JBLabel productNameLabel;
    private com.cosmos.swingb.JBTextField productNameTextField;
    private com.cosmos.swingb.JBButton saveButton;
    // End of variables declaration//GEN-END:variables

    @EJB
    private ProductsListRemote formSession;

    private BindingGroup productBindingGroup;
    private Product product;

    protected void initData()
    {
        System.out.println("initData().product: " + product);
        if(product == null)
        {
            product = getFormSession().newProduct();
        }

        productBindingGroup = new BindingGroup();

        productNameTextField.bind(productBindingGroup, product, "productName");
        productNameTextField.bind(productBindingGroup, product, "productCode");

        measureUnitComboBox.setRenderer(new BeanListCellRenderer());
        measureUnitComboBox.bind(productBindingGroup, getMeasureUnits(), product, "measureUnit");

        productCategoryComboBox.bind(productBindingGroup, getProductsCategories(), product, "category");

        productBindingGroup.bind();
    }

    protected ProductsListRemote getFormSession()
    {
        if(formSession == null)
        {
            try
            {
                formSession = InitialContext.doLookup(ProductsListRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return formSession;
    }

    @Action
    public void saveAction() {
        try
        {
            product = getFormSession().saveProduct(product);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(product);
            close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            // TODO: Log that error
            ResourceMap resource = getResourceMap();
            String title = resource.getString("saveAction.Action.error.title");
            String basicMessage = resource.getString("saveAction.Action.error.basicMessage", ex.getMessage());
            String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
            String category = ProductPanel.class.getName() + ": saveAction.";
            Level errorLevel = Level.WARNING;
            Map<String, String> state = new HashMap();
            state.put("productId", String.valueOf(product.getProductId()));
            state.put("productName", String.valueOf(product.getProductName()));
            state.put("productCode", String.valueOf(product.getProductCode()));
            ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
            JBErrorPane.showDialog(this, errorInfo);
        }
    }

    @Action
    public void closeAction() {
        close();
    }


    private List<ProductCategory> getProductsCategories()
    {
        return getFormSession().getProductsCategories(getParentDataObject());
    }

    private List<DbResource> getMeasureUnits()
    {
        return getFormSession().getMeasureUnits();
    }

    private List<DbResource> getMeasureUnits(MeasurementUnit.Category category)
    {
        return getFormSession().getMeasureUnits(category);
    }

    private class BeanListCellRenderer
        extends DefaultListCellRenderer
    {
        private BeanResource beanResource = new BeanResource(AcaciaApplication.class);

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus)
        {
            Component component;
            if(value instanceof DbResource)
            {
                String valueName = beanResource.getFullName((DbResource)value);
                component = super.getListCellRendererComponent(list, valueName, index, isSelected, cellHasFocus);
            }
            else
                component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            return component;
        }

    }
}

/*

	at com.cosmos.acacia.crm.data.DbResource.toString(DbResource.java:111)
	at javax.swing.plaf.basic.BasicComboBoxRenderer.getListCellRendererComponent(Unknown Source)
	at javax.swing.plaf.basic.BasicListUI.updateLayoutState(Unknown Source)
	at javax.swing.plaf.basic.BasicListUI.maybeUpdateLayoutState(Unknown Source)
	at javax.swing.plaf.basic.BasicListUI$Handler.valueChanged(Unknown Source)
	at javax.swing.DefaultListSelectionModel.fireValueChanged(Unknown Source)
	at javax.swing.DefaultListSelectionModel.fireValueChanged(Unknown Source)
	at javax.swing.DefaultListSelectionModel.fireValueChanged(Unknown Source)
	at javax.swing.DefaultListSelectionModel.changeSelection(Unknown Source)
	at javax.swing.DefaultListSelectionModel.changeSelection(Unknown Source)
	at javax.swing.DefaultListSelectionModel.setSelectionInterval(Unknown Source)
	at javax.swing.JList.setSelectedIndex(Unknown Source)
	at javax.swing.plaf.basic.BasicComboPopup.setListSelection(Unknown Source)
	at javax.swing.plaf.basic.BasicComboPopup.access$300(Unknown Source)
	at javax.swing.plaf.basic.BasicComboPopup$Handler.itemStateChanged(Unknown Source)
	at javax.swing.JComboBox.fireItemStateChanged(Unknown Source)
	at javax.swing.JComboBox.selectedItemChanged(Unknown Source)
	at javax.swing.JComboBox.contentsChanged(Unknown Source)
	at org.jdesktop.swingbinding.JComboBoxBinding$BindingComboBoxModel.contentsChanged(JComboBoxBinding.java:372)
	at org.jdesktop.swingbinding.JComboBoxBinding$BindingComboBoxModel.allChanged(JComboBoxBinding.java:324)
	at org.jdesktop.swingbinding.JComboBoxBinding$BindingComboBoxModel.updateElements(JComboBoxBinding.java:294)
	at org.jdesktop.swingbinding.JComboBoxBinding$Handler.propertyStateChanged(JComboBoxBinding.java:260)
	at org.jdesktop.beansbinding.PropertyHelper.firePropertyStateChange(PropertyHelper.java:212)
	at org.jdesktop.swingbinding.ElementsProperty.setValue0(ElementsProperty.java:98)
	at org.jdesktop.swingbinding.ElementsProperty.setValue(ElementsProperty.java:103)
	at org.jdesktop.swingbinding.ElementsProperty.setValue(ElementsProperty.java:16)
	at org.jdesktop.beansbinding.Binding.refreshUnmanaged(Binding.java:1229)
	at org.jdesktop.beansbinding.Binding.refresh(Binding.java:1207)
	at org.jdesktop.beansbinding.Binding.refreshAndNotify(Binding.java:1143)
	at org.jdesktop.beansbinding.AutoBinding.bindImpl(AutoBinding.java:197)
	at org.jdesktop.swingbinding.JComboBoxBinding.bindImpl(JComboBoxBinding.java:200)
	at org.jdesktop.beansbinding.Binding.bindUnmanaged(Binding.java:959)
	at org.jdesktop.beansbinding.Binding.bind(Binding.java:944)
	at org.jdesktop.beansbinding.BindingGroup.bind(BindingGroup.java:143)
	at com.cosmos.acacia.crm.gui.ProductPanel.initData(ProductPanel.java:209)
	at com.cosmos.acacia.crm.gui.ProductPanel.init(ProductPanel.java:60)
	at com.cosmos.acacia.crm.gui.ProductPanel.<init>(ProductPanel.java:54)
	at com.cosmos.acacia.crm.gui.ProductsListPanel$ProductsButtonActionsListener.newAction(ProductsListPanel.java:366)
	at com.cosmos.acacia.gui.CRUDButtonPanel.newAction(CRUDButtonPanel.java:188)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at org.jdesktop.application.ApplicationAction.noProxyActionPerformed(ApplicationAction.java:662)
	at org.jdesktop.application.ApplicationAction.actionPerformed(ApplicationAction.java:698)
	at javax.swing.AbstractButton.fireActionPerformed(Unknown Source)
	at javax.swing.AbstractButton$Handler.actionPerformed(Unknown Source)
	at javax.swing.DefaultButtonModel.fireActionPerformed(Unknown Source)
	at javax.swing.DefaultButtonModel.setPressed(Unknown Source)
	at javax.swing.plaf.basic.BasicButtonListener.mouseReleased(Unknown Source)
	at java.awt.AWTEventMulticaster.mouseReleased(Unknown Source)
	at java.awt.Component.processMouseEvent(Unknown Source)
	at javax.swing.JComponent.processMouseEvent(Unknown Source)
	at java.awt.Component.processEvent(Unknown Source)
	at java.awt.Container.processEvent(Unknown Source)
	at java.awt.Component.dispatchEventImpl(Unknown Source)
	at java.awt.Container.dispatchEventImpl(Unknown Source)
	at java.awt.Component.dispatchEvent(Unknown Source)
	at java.awt.LightweightDispatcher.retargetMouseEvent(Unknown Source)
	at java.awt.LightweightDispatcher.processMouseEvent(Unknown Source)
	at java.awt.LightweightDispatcher.dispatchEvent(Unknown Source)
	at java.awt.Container.dispatchEventImpl(Unknown Source)
	at java.awt.Window.dispatchEventImpl(Unknown Source)
	at java.awt.Component.dispatchEvent(Unknown Source)
	at java.awt.EventQueue.dispatchEvent(Unknown Source)
	at java.awt.EventDispatchThread.pumpOneEventForFilters(Unknown Source)
	at java.awt.EventDispatchThread.pumpEventsForFilter(Unknown Source)
	at java.awt.EventDispatchThread.pumpEventsForHierarchy(Unknown Source)
	at java.awt.EventDispatchThread.pumpEvents(Unknown Source)
	at java.awt.EventDispatchThread.pumpEvents(Unknown Source)
	at java.awt.EventDispatchThread.run(Unknown Source)

*/
