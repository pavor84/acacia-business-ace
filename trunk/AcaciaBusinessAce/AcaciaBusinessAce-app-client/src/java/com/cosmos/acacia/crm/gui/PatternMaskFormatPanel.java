/*
 * PatternMaskFormatPanel.java
 *
 * Created on РЎСЉР±РѕС‚Р°, 2008, РњР°СЂС‚ 22, 19:39
 */

package com.cosmos.acacia.crm.gui;

import javax.ejb.EJB;

import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.gui.ProductPanel.Button;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created : 28.07.2008
 * 
 * @author Petar Milev
 */
public class PatternMaskFormatPanel extends BaseEntityPanel {

    @EJB
    private PatternMaskListRemote formSession;
    private BusinessPartnersListPanel listPanel;

    /** Creates new form PatternMaskFormatPanel */
    public PatternMaskFormatPanel(PatternMaskFormat modelObject) {
        super(modelObject.getParentId());
        this.format = modelObject;
        initComponents();
        initData();
    }

    private BindingGroup bindingGroup;
    private PatternMaskFormat format;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        nameField = new com.cosmos.swingb.JBTextField();
        formatField = new com.cosmos.swingb.JBTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextPane();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        ownerField = new com.cosmos.acacia.gui.AcaciaComboList();
        jBLabel4 = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PatternMaskFormatPanel.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        nameField.setText(resourceMap.getString("nameField.text")); // NOI18N
        nameField.setName("nameField"); // NOI18N
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        formatField.setName("formatField"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        ownerField.setName("ownerField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jBLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(formatField, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(ownerField, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_nameFieldActionPerformed

    @Override
    protected void initData() {
        BindingGroup bg = getBindingGroup();

        EntityProperties entityProps = getFormSession().getPatternMaskEntityProperties();

        nameField.bind(bg, format, entityProps.getPropertyDetails("patternName"));
        formatField.bind(bg, format, entityProps.getPropertyDetails("format"));
        descriptionField.bind(bg, format, entityProps.getPropertyDetails("description"));
        if(listPanel == null) {
            Classifier classifier = getClassifier(Classifier.Supplier.getClassifierCode());
            listPanel = new BusinessPartnersListPanel(classifier);
        }
        ownerField.bind(bg, listPanel, format, entityProps.getPropertyDetails("owner"),
            "${displayName}", UpdateStrategy.READ_WRITE);
        
        bg.bind();

        setSaveActionState();

        if (bg != null) {
            bg.addBindingListener(new AbstractBindingListener() {

                @SuppressWarnings("unchecked")
                @Override
                public void targetChanged(Binding binding, PropertyStateEvent event) {
                    setSaveActionState();
                }
            });
        }
    }

    protected void setSaveActionState() {
        setEnabled(Button.Save, getBindingGroup().isContentValid());
    }

    public void setEnabled(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction) getAction(button);
        if (action != null) {
            action.setEnabled(enabled);
        }
    }

    public javax.swing.Action getAction(Button button) {
        ApplicationActionMap actionMap = getApplicationActionMap();
        if (actionMap != null && button != null) {
            return actionMap.get(button.getActionName());
        }

        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTextPane descriptionField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBTextField formatField;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBTextField nameField;
    private com.cosmos.acacia.gui.AcaciaComboList ownerField;
    // End of variables declaration//GEN-END:variables

    /**
     * Getter for formSession
     * @return PatternMaskListRemote
     */
    public PatternMaskListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PatternMaskListRemote.class);
        }

        return formSession;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStopClass() {
        return AcaciaPanel.class;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if(bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel1;
    }

    @Override
    public Object getEntity() {
        return format;
    }

    @Override
    public void performSave(boolean closeAfter) {
        format = getFormSession().savePatternMaskFormat(format);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(format);
        if (closeAfter)
            close();
    }
}
