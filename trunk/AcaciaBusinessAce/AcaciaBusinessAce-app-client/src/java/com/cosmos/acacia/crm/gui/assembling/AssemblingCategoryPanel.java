/*
 * AssemblingCategoryPanel.java
 *
 * Created on Сряда, 2008, Юни 4, 21:43
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import javax.ejb.EJB;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Miro
 */
public class AssemblingCategoryPanel
    extends BaseEntityPanel
{
    @EJB
    private static AssemblingRemote formSession;

    private AssemblingCategory entity;
    private EntityProperties entProps;
    private BindingGroup bindGroup;

    /** Creates new form AssemblingCategoryPanel */
    public AssemblingCategoryPanel()
    {
        super(null);
        initComponents();
    }

    public AssemblingCategoryPanel(AssemblingCategory category)
    {
        super(category.getParentId());
        this.entity = category;
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

        parentCategoryLabel = new javax.swing.JLabel();
        parentCategoryLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        categoryCodeLabel = new javax.swing.JLabel();
        categoryNameLabel = new javax.swing.JLabel();
        categoryCodeTextField = new com.cosmos.swingb.JBTextField();
        categoryNameTextField = new com.cosmos.swingb.JBTextField();
        descriptionPanel = new javax.swing.JPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingCategoryPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N

        parentCategoryLabel.setText(resourceMap.getString("parentCategoryLabel.text")); // NOI18N
        parentCategoryLabel.setName("parentCategoryLabel"); // NOI18N

        parentCategoryLookup.setName("parentCategoryLookup"); // NOI18N

        categoryCodeLabel.setText(resourceMap.getString("categoryCodeLabel.text")); // NOI18N
        categoryCodeLabel.setName("categoryCodeLabel"); // NOI18N

        categoryNameLabel.setText(resourceMap.getString("categoryNameLabel.text")); // NOI18N
        categoryNameLabel.setName("categoryNameLabel"); // NOI18N

        categoryCodeTextField.setText(resourceMap.getString("categoryCodeTextField.text")); // NOI18N
        categoryCodeTextField.setName("categoryCodeTextField"); // NOI18N

        categoryNameTextField.setText(resourceMap.getString("categoryNameTextField.text")); // NOI18N
        categoryNameTextField.setName("categoryNameTextField"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(parentCategoryLabel)
                            .addComponent(categoryCodeLabel)
                            .addComponent(categoryNameLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(parentCategoryLookup, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(categoryNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parentCategoryLabel)
                    .addComponent(parentCategoryLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryCodeLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryNameLabel))
                .addGap(18, 18, 18)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel categoryCodeLabel;
    private com.cosmos.swingb.JBTextField categoryCodeTextField;
    private javax.swing.JLabel categoryNameLabel;
    private com.cosmos.swingb.JBTextField categoryNameTextField;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private javax.swing.JLabel parentCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaLookup parentCategoryLookup;
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        entProps = getFormSession().getAssemblingCategoryEntityProperties();
        PropertyDetails propDetails;
        
        bindGroup = new BindingGroup();
        
        //parent category
        propDetails = entProps.getPropertyDetails("parentCategory");
        parentCategoryLookup.bind(new AcaciaLookupProvider()
            {
                @Override
                public Object showSelectionControl()
                {
                    return onChooseCategory();
                }
            },
            bindGroup, 
            entity, 
            propDetails, 
            "${categoryName}",
            UpdateStrategy.READ_WRITE);
        
        //category code
        propDetails = entProps.getPropertyDetails("categoryCode");
        categoryCodeTextField.bind(bindGroup, entity, propDetails);

        //category name
        propDetails = entProps.getPropertyDetails("categoryName");
        categoryNameTextField.bind(bindGroup, entity, propDetails);

        //description
        descriptionTextPane.bind(bindGroup, entity, "description");

        bindGroup.bind();
    }

    @Override
    public EntityFormButtonPanel getButtonPanel()
    {
        return entityFormButtonPanel;
    }

    @Override
    public BindingGroup getBindingGroup()
    {
        return bindGroup;
    }

    @Override
    public Object getEntity()
    {
        return entity;
    }

    @Override
    public void performSave(boolean closeAfter)
    {
        entity = getFormSession().saveCategory(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if(closeAfter)
            close();
    }

    protected Object onChooseCategory()
    {
        /*ProductCategoriesTreePanel panel = new ProductCategoriesTreePanel(null);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Select, true);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Unselect, true);
        panel.setModificationsEnabled(false);
        
        DialogResponse dResponse = panel.showDialog(this);
        
        if ( DialogResponse.SELECT.equals(dResponse) ){
            
            ProductCategory category = (ProductCategory)
                panel.getListPanel().getSelectedRowObject();

            return category;
        }else{
            return null;
        }*/
        return null;
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getRemoteBean(this, AssemblingRemote.class);
        }

        return formSession;
    }
}
