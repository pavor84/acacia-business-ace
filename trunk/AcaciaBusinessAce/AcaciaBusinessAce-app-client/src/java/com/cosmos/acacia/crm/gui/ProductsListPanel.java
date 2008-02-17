/*
 * ProductsListPanel.java
 *
 * Created on Неделя, 2008, Февруари 10, 12:38
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.CRUDButtonActionsListener;
import com.cosmos.acacia.gui.CRUDButtonPanel;
import com.cosmos.acacia.security.GUIAccessControl;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author  miro
 */
public class ProductsListPanel extends AcaciaPanel {

    /** Creates new form ProductsListPanel */
    public ProductsListPanel(DataObject parentDataObject) {
        super(parentDataObject);
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

        productsTabbedPane = new com.cosmos.swingb.JBTabbedPane();
        productsTabPanel = new com.cosmos.swingb.JBPanel();
        productsListScrollPane = new javax.swing.JScrollPane();
        productsTable = new com.cosmos.acacia.gui.AcaciaTable();
        buttonsPanel = new com.cosmos.acacia.gui.CRUDButtonPanel();
        optionsTabPanel = new com.cosmos.swingb.JBPanel();

        setName("ProductsListPanel"); // NOI18N

        productsTabbedPane.setName("productsTabbedPane"); // NOI18N

        productsTabPanel.setName("productsTabPanel"); // NOI18N

        productsListScrollPane.setName("productsListScrollPane"); // NOI18N

        productsTable.setName("productsTable"); // NOI18N
        productsListScrollPane.setViewportView(productsTable);

        buttonsPanel.setName("buttonsPanel"); // NOI18N

        javax.swing.GroupLayout productsTabPanelLayout = new javax.swing.GroupLayout(productsTabPanel);
        productsTabPanel.setLayout(productsTabPanelLayout);
        productsTabPanelLayout.setHorizontalGroup(
            productsTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productsTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productsTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productsListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        productsTabPanelLayout.setVerticalGroup(
            productsTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productsTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductsListPanel.class);
        productsTabbedPane.addTab(resourceMap.getString("productsTabPanel.TabConstraints.tabTitle"), productsTabPanel); // NOI18N

        optionsTabPanel.setName("optionsTabPanel"); // NOI18N

        javax.swing.GroupLayout optionsTabPanelLayout = new javax.swing.GroupLayout(optionsTabPanel);
        optionsTabPanel.setLayout(optionsTabPanelLayout);
        optionsTabPanelLayout.setHorizontalGroup(
            optionsTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 588, Short.MAX_VALUE)
        );
        optionsTabPanelLayout.setVerticalGroup(
            optionsTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        productsTabbedPane.addTab(resourceMap.getString("optionsTabPanel.TabConstraints.tabTitle"), optionsTabPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.CRUDButtonPanel buttonsPanel;
    private com.cosmos.swingb.JBPanel optionsTabPanel;
    private javax.swing.JScrollPane productsListScrollPane;
    private com.cosmos.swingb.JBPanel productsTabPanel;
    private com.cosmos.swingb.JBTabbedPane productsTabbedPane;
    private com.cosmos.acacia.gui.AcaciaTable productsTable;
    // End of variables declaration//GEN-END:variables

    @EJB
    private ProductsListRemote formSession;

    private BindingGroup productsBindingGroup;
    private JTableBinding productsTableBinding;
    private List<Product> products;
    private ProductsAccessControl accessControl;
    private ProductsButtonActionsListener buttonActionsListener;

    protected void initData()
    {
        accessControl = new ProductsAccessControl();
        buttonsPanel.setAccessControl(accessControl);
        buttonActionsListener = new ProductsButtonActionsListener();
        buttonsPanel.setButtonActionsListener(buttonActionsListener);
        
        buttonsPanel.setEnabled(CRUDButtonPanel.Button.Modify, false);
        buttonsPanel.setEnabled(CRUDButtonPanel.Button.Delete, false);

        JTableBinding tableBinding = getProductsTableBinding();

        BindingGroup bindingGroup = getProductsBindingGroup();
        bindingGroup.addBinding(tableBinding);

        /*Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, productsTable, ELProperty.create("${selectedElement.productId}"), productIdTextField, BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, productsTable, ELProperty.create("${selectedElement != null}"), productIdTextField, BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, productsTable, ELProperty.create("${selectedElement.productName}"), productNameTextField, BeanProperty.create("text"));
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, productsTable, ELProperty.create("${selectedElement != null}"), productNameTextField, BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);*/

        bindingGroup.bind();

        buttonsPanel.addListSelectionListener(productsTable);
    }

    protected BindingGroup getProductsBindingGroup()
    {
        if(productsBindingGroup == null)
        {
            productsBindingGroup = new BindingGroup();
        }

        return productsBindingGroup;
    }

    protected JTableBinding getProductsTableBinding()
    {
        if(productsTableBinding == null)
        {
            productsTable.setData(getProducts());
            productsTable.setEntityProperties(getProductEntityProperties());
            productsTableBinding = productsTable.getTableBinding();
//            List list = ObservableCollections.observableList(getProducts());
//            EntityProperties entityProps = getProductEntityProperties();
//            productsTableBinding = BeansBinding.createTableBinding(productsTable, list, entityProps);
        }

        return productsTableBinding;
    }

    protected List<Product> getProducts()
    {
        if(products == null)
        {
            products = getFormSession().getProducts(getParentDataObject());
        }

        return products;
    }

    protected EntityProperties getProductEntityProperties()
    {
        return getFormSession().getProductEntityProperties();
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

    protected int deleteProduct(Product product)
    {
        return getFormSession().deleteProduct(product);
    }

    private class RefreshProductsActionTask extends org.jdesktop.application.Task<Object, Void> {
        RefreshProductsActionTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RefreshProductsActionTask fields, here.
            super(app);
        }
        @Override protected Object doInBackground() {
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            return null;  // return your result
        }
        @Override protected void succeeded(Object result) {
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }

    private class RefreshTask
        extends Task
    {
        RefreshTask(Application app) {
            super(app);
        }

        @Override
        protected Void doInBackground() {
            try {
                setProgress(0, 0, 4);
                /*setMessage("Rolling back the current changes...");
                setProgress(1, 0, 4);
                entityManager.getTransaction().rollback();
                Thread.sleep(1000L); // remove for real app
                setProgress(2, 0, 4);

                setMessage("Starting a new transaction...");
                entityManager.getTransaction().begin();
                Thread.sleep(500L); // remove for real app
                setProgress(3, 0, 4);

                setMessage("Fetching new data...");*/
                /*java.util.Collection data = query.getResultList();
                Thread.sleep(1300L); // remove for real app
                setProgress(4, 0, 4);

                Thread.sleep(150L); // remove for real app
                list.clear();
                list.addAll(data);*/
                if(false)
                    throw new InterruptedException();
            } catch(InterruptedException ignore) { }
            return null;
        }
        @Override protected void finished() {
            setMessage("Done.");
            //setSaveNeeded(false);
        }
    }



    private class ProductsAccessControl
        extends GUIAccessControl
    {

        @Override
        public boolean canCreate(DataObject parentDataObject) {
            System.out.println("canCreate: " + parentDataObject);
            return true;
        }

        @Override
        public boolean canModify(DataObject dataObject) {
            System.out.println("canModify: " + dataObject);
            return true;
        }

        @Override
        public boolean canDelete(DataObject dataObject) {
            System.out.println("canDelete: " + dataObject);
            return true;
        }
    }

    private class ProductsButtonActionsListener
        implements CRUDButtonActionsListener
    {

        public void closeAction() {
            System.out.println("closeAction");
            ProductsListPanel.this.close();
        }

        public void refreshAction() {
            System.out.println("refreshProductsAction");
            new RefreshTask(ProductsListPanel.this.getApplication());
        }

        public void deleteAction() {
            Product product = (Product)productsTable.getSelectedRowObject();
            if(product != null)
            {
                deleteProduct(product);
                productsTable.removeSelectedRow();
            }
        }

        public void modifyAction() {
            Product product = (Product)productsTable.getSelectedRowObject();
            if(product != null)
            {
                System.out.println("Modify product: " + product);
                ProductPanel productPanel = new ProductPanel(product);
                DialogResponse response = productPanel.showDialog(ProductsListPanel.this);
                if(DialogResponse.SAVE.equals(response))
                {
                    product = (Product)productPanel.getSelectedValue();
                    productsTable.replaceSelectedRow(product);
                }
            }
        }

        public void newAction() {
            ProductPanel productPanel = new ProductPanel(getParentDataObject());
            DialogResponse response = productPanel.showDialog(ProductsListPanel.this);
            if(DialogResponse.SAVE.equals(response))
            {
                Product product = (Product)productPanel.getSelectedValue();
                productsTable.addRow(product);
            }
        }
    }
}
