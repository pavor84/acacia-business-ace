/*
 * AssemblingSchemaItemValuePanel.java
 *
 * Created on Неделя, 2008, Юни 15, 23:59
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.RealProduct;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import com.cosmos.acacia.crm.gui.ProductsListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import javax.ejb.EJB;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Miro
 */
public class AssemblingSchemaItemValuePanel
    extends BaseEntityPanel
{
    @EJB
    private static AssemblingRemote formSession;

    private AssemblingSchemaItemValue entity;
    private EntityProperties entityProps;
    private BindingGroup bindingGroup;


    /** Creates new form AssemblingSchemaItemValuePanel */
    public AssemblingSchemaItemValuePanel()
    {
        super(null);
        initComponents();
    }

    public AssemblingSchemaItemValuePanel(AssemblingSchemaItemValue itemValue)
    {
        super(itemValue.getParentId());
        this.entity = itemValue;
        init();
    }

    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        selectProductOrSchemaButton = new com.cosmos.swingb.JBButton();
        productOrSchemaTextField = new com.cosmos.swingb.JBTextField();
        productOrSchemaLabel = new com.cosmos.swingb.JBLabel();
        constraintsPanel = new com.cosmos.swingb.JBPanel();
        minValuePanel = new com.cosmos.swingb.JBPanel();
        minValueTextField = new com.cosmos.swingb.JBTextField();
        minValueLabel = new com.cosmos.swingb.JBLabel();
        maxValuePanel = new com.cosmos.swingb.JBPanel();
        maxValueTextField = new com.cosmos.swingb.JBTextField();
        maxValueLabel = new com.cosmos.swingb.JBLabel();
        quantityTextField = new com.cosmos.swingb.JBTextField();
        quantityLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(AssemblingSchemaItemValuePanel.class, this);
        selectProductOrSchemaButton.setAction(actionMap.get("productOrSchemaSelectAction")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingSchemaItemValuePanel.class);
        selectProductOrSchemaButton.setText(resourceMap.getString("selectProductOrSchemaButton.text")); // NOI18N
        selectProductOrSchemaButton.setName("selectProductOrSchemaButton"); // NOI18N

        productOrSchemaTextField.setEditable(false);
        productOrSchemaTextField.setName("productOrSchemaTextField"); // NOI18N

        productOrSchemaLabel.setText(resourceMap.getString("productOrSchemaLabel.text")); // NOI18N
        productOrSchemaLabel.setName("productOrSchemaLabel"); // NOI18N

        constraintsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("constraintsPanel.border.title"))); // NOI18N
        constraintsPanel.setName("constraintsPanel"); // NOI18N
        constraintsPanel.setLayout(new java.awt.GridLayout(1, 0));

        minValuePanel.setName("minValuePanel"); // NOI18N

        minValueTextField.setName("minValueTextField"); // NOI18N

        minValueLabel.setText(resourceMap.getString("minValueLabel.text")); // NOI18N
        minValueLabel.setName("minValueLabel"); // NOI18N

        javax.swing.GroupLayout minValuePanelLayout = new javax.swing.GroupLayout(minValuePanel);
        minValuePanel.setLayout(minValuePanelLayout);
        minValuePanelLayout.setHorizontalGroup(
            minValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minValuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(minValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(minValueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        minValuePanelLayout.setVerticalGroup(
            minValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(minValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(minValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        constraintsPanel.add(minValuePanel);

        maxValuePanel.setName("maxValuePanel"); // NOI18N

        maxValueTextField.setName("maxValueTextField"); // NOI18N

        maxValueLabel.setText(resourceMap.getString("maxValueLabel.text")); // NOI18N
        maxValueLabel.setName("maxValueLabel"); // NOI18N

        javax.swing.GroupLayout maxValuePanelLayout = new javax.swing.GroupLayout(maxValuePanel);
        maxValuePanel.setLayout(maxValuePanelLayout);
        maxValuePanelLayout.setHorizontalGroup(
            maxValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maxValuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(maxValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(maxValueTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
        );
        maxValuePanelLayout.setVerticalGroup(
            maxValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(maxValuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(maxValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(maxValueTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        constraintsPanel.add(maxValuePanel);

        quantityTextField.setName("quantityTextField"); // NOI18N

        quantityLabel.setText(resourceMap.getString("quantityLabel.text")); // NOI18N
        quantityLabel.setName("quantityLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(constraintsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(productOrSchemaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(productOrSchemaTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(selectProductOrSchemaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectProductOrSchemaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productOrSchemaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productOrSchemaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(constraintsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel constraintsPanel;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBLabel maxValueLabel;
    private com.cosmos.swingb.JBPanel maxValuePanel;
    private com.cosmos.swingb.JBTextField maxValueTextField;
    private com.cosmos.swingb.JBLabel minValueLabel;
    private com.cosmos.swingb.JBPanel minValuePanel;
    private com.cosmos.swingb.JBTextField minValueTextField;
    private com.cosmos.swingb.JBLabel productOrSchemaLabel;
    private com.cosmos.swingb.JBTextField productOrSchemaTextField;
    private com.cosmos.swingb.JBLabel quantityLabel;
    private com.cosmos.swingb.JBTextField quantityTextField;
    private com.cosmos.swingb.JBButton selectProductOrSchemaButton;
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        entityProps = getFormSession().getAssemblingSchemaItemValueEntityProperties();
        PropertyDetails propDetails;
        
        bindingGroup = new BindingGroup();

        // propDetails = entityProps.getPropertyDetails("virtualProduct");
        // productOrSchemaTextField.bind(bindingGroup, entity, propDetails);
        VirtualProduct virtualProduct = entity.getVirtualProduct();
        productOrSchemaTextField.setText(virtualProduct.getProductName());

        propDetails = entityProps.getPropertyDetails("minConstraint");
        minValueTextField.bind(bindingGroup, entity, propDetails);

        propDetails = entityProps.getPropertyDetails("maxConstraint");
        maxValueTextField.bind(bindingGroup, entity, propDetails);

        propDetails = entityProps.getPropertyDetails("quantity");
        quantityTextField.bind(bindingGroup, entity, propDetails);

        bindingGroup.bind();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel()
    {
        return entityFormButtonPanel;
    }

    @Override
    public BindingGroup getBindingGroup()
    {
        return bindingGroup;
    }

    @Override
    public Object getEntity()
    {
        return entity;
    }

    @Override
    public void performSave(boolean closeAfter)
    {
        entity = getFormSession().saveItemValue(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if(closeAfter)
            close();
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getRemoteBean(this, AssemblingRemote.class);
        }

        return formSession;
    }

    @Action
    public void productOrSchemaSelectAction()
    {
        VirtualProduct virtualProduct = entity.getVirtualProduct();
        if(virtualProduct == null)
        {
            VirtualProductSelectionPanel selectionPanel = new VirtualProductSelectionPanel(entity);
            DialogResponse response = selectionPanel.showDialog(this);
            if(DialogResponse.SELECT.equals(response))
            {
                virtualProduct = (VirtualProduct)selectionPanel.getSelectedValue();
                entity.setVirtualProduct(virtualProduct);
                productOrSchemaTextField.setText(virtualProduct.getProductName());
            }
        }
        else if(virtualProduct instanceof AssemblingSchema)
        {
            AssemblingSchema assemblingSchema = (AssemblingSchema)virtualProduct;
            AssemblingSchemasListPanel asListPanel = new AssemblingSchemasListPanel(assemblingSchema.getParentId());
            asListPanel.setSelectedRowObject(assemblingSchema);
            DialogResponse response = asListPanel.showDialog(this);
            if(DialogResponse.SELECT.equals(response))
            {
                virtualProduct = assemblingSchema = (AssemblingSchema)asListPanel.getSelectedRowObject();
                entity.setVirtualProduct(assemblingSchema);
                productOrSchemaTextField.setText(virtualProduct.getProductName());
            }
        }
        else if(virtualProduct instanceof RealProduct)
        {
            RealProduct realProduct = (RealProduct)virtualProduct;
            SimpleProduct simpleProduct = realProduct.getSimpleProduct();
            ProductsListPanel productsPanel = new ProductsListPanel(simpleProduct.getParentId());
            productsPanel.setSelectedRowObject(simpleProduct);
            DialogResponse response = productsPanel.showDialog(this);
            if(DialogResponse.SELECT.equals(response))
            {
                simpleProduct = (SimpleProduct)productsPanel.getSelectedRowObject();
                virtualProduct = realProduct = getFormSession().getRealProduct(simpleProduct);
                entity.setVirtualProduct(realProduct);
                productOrSchemaTextField.setText(virtualProduct.getProductName());
            }
        }
    }
}
