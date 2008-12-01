/*
 * ProductCategoryPanel.java
 *
 * Created on Понеделник, 2008, Април 21, 11:20
 */

package com.cosmos.acacia.crm.gui;

import java.math.BigInteger;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * Created	:	21.04.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class ProductCategoryPanel extends BaseEntityPanel {
    private ProductCategory entity;

    @EJB
    private ProductsListRemote formSession;

    private EntityProperties entProps;

    private BindingGroup bindGroup;

    /** Creates new form ProductCategoryPanel */
    public ProductCategoryPanel(ProductCategory category, BigInteger parentId)
    {
        super(parentId);
        this.entity = category;
        init();
    }

    @Override
    protected void init()
    {
        initComponents();
        super.init();
    }

    public ProductCategoryPanel(){
        super(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        nameField = new com.cosmos.swingb.JBTextField();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextArea();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        categoryField = new com.cosmos.acacia.gui.AcaciaComboList();
        patternMaskFormatField = new com.cosmos.acacia.gui.AcaciaComboList();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductCategoryPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        nameField.setText(resourceMap.getString("nameField.text")); // NOI18N
        nameField.setName("nameField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setColumns(20);
        descriptionField.setRows(5);
        descriptionField.setFont(resourceMap.getFont("descriptionField.font")); // NOI18N
        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        categoryField.setName("categoryField"); // NOI18N

        patternMaskFormatField.setName("patternMaskFormatField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(categoryField, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(patternMaskFormatField, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(categoryField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(patternMaskFormatField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void initData() {
        entProps = getFormSession().getProductCategoryEntityProperties();
        PropertyDetails propDetails;

        bindGroup = new BindingGroup();

        //parent category
        propDetails = entProps.getPropertyDetails("parentCategory");
        ProductCategoriesTreePanel categoryListPanel = new ProductCategoriesTreePanel(getParentDataObjectId());
        categoryField.bind(bindGroup, categoryListPanel, entity, propDetails,
            "${categoryName}", UpdateStrategy.READ_WRITE);
        
        //category name
        propDetails = entProps.getPropertyDetails("categoryName");
        nameField.bind(bindGroup, entity, propDetails);

        //pattern mask format
        propDetails = entProps.getPropertyDetails("patternMaskFormat");
        PatternMaskFormatListPanel patternListPanel = new PatternMaskFormatListPanel(getParentDataObjectId());
        
        patternMaskFormatField.bind(bindGroup, patternListPanel, entity, propDetails,
            "${patternName} (${format})", UpdateStrategy.READ_WRITE);

        //description
        descriptionField.bind(bindGroup, entity, "description");

        bindGroup.bind();
    }

    @SuppressWarnings("unchecked")
    protected Object onChooseCategory() {
        ProductCategoriesTreePanel panel = new ProductCategoriesTreePanel(null);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Select, true);
        panel.getListPanel().setVisible(com.cosmos.acacia.gui.AbstractTablePanel.Button.Unselect, true);
        panel.setModificationsEnabled(false);

        DialogResponse dResponse = panel.showDialog(this);

        if ( DialogResponse.SELECT.equals(dResponse) ){

            ProductCategory category = (ProductCategory)
                panel.getListPanel().getSelectedRowObject();

//            PatternMaskFormat oldFormat =
//                (PatternMaskFormat) patternMaskBinding.getTargetProperty()
//                    .getValue(patternMaskBinding.getTargetObject());
//
//            if ( oldFormat==null && category!=null ){
//                patternMaskBinding.getTargetProperty()
//                    .setValue(patternMaskBinding.getTargetObject(), category.getPatternMaskFormat());
//            }

            return category;
        }else{
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboList categoryField;
    private com.cosmos.swingb.JBTextArea descriptionField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBTextField nameField;
    private com.cosmos.acacia.gui.AcaciaComboList patternMaskFormatField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void performSave(boolean closeAfter) {
        entity = getFormSession().saveProductCategory(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter)
            close();
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null)
                formSession = getBean(ProductsListRemote.class);

        return formSession;
    }
}
