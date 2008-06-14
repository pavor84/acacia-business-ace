/*
 * AssemblingSchemaPanel.java
 *
 * Created on Вторник, 2008, Юни 10, 21:06
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
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
public class AssemblingSchemaPanel
    extends BaseEntityPanel
{
    @EJB
    private static AssemblingRemote formSession;

    private AssemblingSchema entity;
    private EntityProperties entityProps;
    private BindingGroup bindingGroup;


    /** Creates new form AssemblingSchemaPanel */
    public AssemblingSchemaPanel()
    {
        super(null);
        initComponents();
    }

    public AssemblingSchemaPanel(AssemblingSchema schema)
    {
        super(schema.getParentId());
        this.entity = schema;
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

        assemblingCategoryLabel = new com.cosmos.swingb.JBLabel();
        assemblingCategoryLookup = new com.cosmos.acacia.gui.AcaciaLookup();
        schemaCodeTextField = new com.cosmos.swingb.JBTextField();
        schemaNameTextField = new com.cosmos.swingb.JBTextField();
        schemaCodeLabel = new com.cosmos.swingb.JBLabel();
        schemaNameLabel = new com.cosmos.swingb.JBLabel();
        isObsoleteCheckBox = new com.cosmos.swingb.JBCheckBox();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        descriptionPanel = new com.cosmos.swingb.JBPanel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextPane = new com.cosmos.swingb.JBTextPane();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AssemblingSchemaPanel.class);
        assemblingCategoryLabel.setText(resourceMap.getString("assemblingCategoryLabel.text")); // NOI18N
        assemblingCategoryLabel.setName("assemblingCategoryLabel"); // NOI18N

        assemblingCategoryLookup.setName("assemblingCategoryLookup"); // NOI18N

        schemaCodeTextField.setText(resourceMap.getString("schemaCodeTextField.text")); // NOI18N
        schemaCodeTextField.setName("schemaCodeTextField"); // NOI18N

        schemaNameTextField.setText(resourceMap.getString("schemaNameTextField.text")); // NOI18N
        schemaNameTextField.setName("schemaNameTextField"); // NOI18N

        schemaCodeLabel.setText(resourceMap.getString("schemaCodeLabel.text")); // NOI18N
        schemaCodeLabel.setName("schemaCodeLabel"); // NOI18N

        schemaNameLabel.setText(resourceMap.getString("schemaNameLabel.text")); // NOI18N
        schemaNameLabel.setName("schemaNameLabel"); // NOI18N

        isObsoleteCheckBox.setText(resourceMap.getString("isObsoleteCheckBox.text")); // NOI18N
        isObsoleteCheckBox.setName("isObsoleteCheckBox"); // NOI18N

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        descriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("descriptionPanel.border.title"))); // NOI18N
        descriptionPanel.setName("descriptionPanel"); // NOI18N
        descriptionPanel.setLayout(new java.awt.BorderLayout());

        descriptionScrollPane.setName("descriptionScrollPane"); // NOI18N

        descriptionTextPane.setName("descriptionTextPane"); // NOI18N
        descriptionScrollPane.setViewportView(descriptionTextPane);

        descriptionPanel.add(descriptionScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(schemaCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(schemaNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isObsoleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(schemaNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                            .addComponent(schemaCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                            .addComponent(assemblingCategoryLookup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(assemblingCategoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assemblingCategoryLookup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemaCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schemaCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemaNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(schemaNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(isObsoleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(descriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel assemblingCategoryLabel;
    private com.cosmos.acacia.gui.AcaciaLookup assemblingCategoryLookup;
    private com.cosmos.swingb.JBPanel descriptionPanel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private com.cosmos.swingb.JBTextPane descriptionTextPane;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBCheckBox isObsoleteCheckBox;
    private com.cosmos.swingb.JBLabel schemaCodeLabel;
    private com.cosmos.swingb.JBTextField schemaCodeTextField;
    private com.cosmos.swingb.JBLabel schemaNameLabel;
    private com.cosmos.swingb.JBTextField schemaNameTextField;
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        assemblingCategoryLookup.setEnabled(false);

        entityProps = getFormSession().getAssemblingSchemaEntityProperties();
        PropertyDetails propDetails;
        
        bindingGroup = new BindingGroup();
        
        //parent category
        propDetails = entityProps.getPropertyDetails("assemblingCategory");
        assemblingCategoryLookup.bind(new AcaciaLookupProvider()
            {
                @Override
                public Object showSelectionControl()
                {
                    return onChooseCategory();
                }
            },
            bindingGroup, 
            entity, 
            propDetails, 
            "${categoryName}",
            UpdateStrategy.READ_WRITE);

        //schema code
        propDetails = entityProps.getPropertyDetails("schemaCode");
        schemaCodeTextField.bind(bindingGroup, entity, propDetails);

        //schema name
        propDetails = entityProps.getPropertyDetails("schemaName");
        schemaNameTextField.bind(bindingGroup, entity, propDetails);

        //obsolete
        propDetails = entityProps.getPropertyDetails("obsolete");
        isObsoleteCheckBox.bind(bindingGroup, entity, propDetails);

        //description
        propDetails = entityProps.getPropertyDetails("description");
        descriptionTextPane.bind(bindingGroup, entity, propDetails);

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
        entity = getFormSession().saveSchema(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if(closeAfter)
            close();
    }

    protected Object onChooseCategory()
    {
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
