/*
 * PatternMaskFormatPanel.java
 *
 * Created on РЎСЉР±РѕС‚Р°, 2008, РњР°СЂС‚ 22, 19:39
 */

package com.cosmos.acacia.crm.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBErrorPane;

/**
 *
 * @author  jchan
 */
public class PatternMaskFormatPanel extends AcaciaPanel {
    
    @EJB
    private PatternMaskListRemote formSession;
    
    /** Creates new form PatternMaskFormatPanel */
    public PatternMaskFormatPanel() {
        super(null);
        init();
    }
    
    private BindingGroup bindGroup;
    private PatternMaskFormat format;
    
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

        jBPanel1 = new com.cosmos.swingb.JBPanel();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        nameField = new com.cosmos.swingb.JBTextField();
        formatField = new com.cosmos.swingb.JBTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextArea();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        ownerField = new com.cosmos.acacia.gui.AcaciaComboBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(PatternMaskFormatPanel.class);
        jBPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("formatterDetailsPanel.border.title"))); // NOI18N
        jBPanel1.setName("formatterDetailsPanel"); // NOI18N
        jBPanel1.setTitle(resourceMap.getString("formatterDetailsPanel.title")); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        nameField.setText(resourceMap.getString("nameField.text")); // NOI18N
        nameField.setName("nameField"); // NOI18N

        formatField.setName("formatField"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setColumns(20);
        descriptionField.setRows(5);
        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        ownerField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ownerField.setName("ownerField"); // NOI18N

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel1Layout.createSequentialGroup()
                        .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .addGroup(jBPanel1Layout.createSequentialGroup()
                        .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                            .addComponent(formatField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                            .addComponent(ownerField, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBPanel1.getAccessibleContext().setAccessibleName(resourceMap.getString("formatterDetailsPanel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void initData() {
        format = getFormSession().newPatternMaskFormat();
        
        bindGroup = new BindingGroup();

        EntityProperties entityProps = getFormSession().getPatternMaskEntityProperties();
        
        nameField.bind(bindGroup, format, entityProps.getPropertyDetails("patternName") );
        formatField.bind(bindGroup, format, entityProps.getPropertyDetails("format") );
        descriptionField.bind(bindGroup, format, "description");
        ownerField.bind(bindGroup, getPossibleOwners(), format, entityProps.getPropertyDetails("ownerId") );

        bindGroup.bind();
    }
    
    private List<BusinessPartner> getPossibleOwners() {
        return new ArrayList<BusinessPartner>();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTextArea descriptionField;
    private com.cosmos.swingb.JBTextField formatField;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.swingb.JBTextField nameField;
    private com.cosmos.acacia.gui.AcaciaComboBox ownerField;
    // End of variables declaration//GEN-END:variables
    
    
    /**
     * Getter for formSession
     * @return PatternMaskListRemote
     */
    public PatternMaskListRemote getFormSession() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(PatternMaskListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
        
        return formSession;
    }
    
    @Action
    public void saveAction() {
        try
        {
            format = getFormSession().savePatternMaskFormat(format);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(format);
            close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            // TODO: Log that error
            String basicMessage = getResourceMap().getString(
                "saveAction.Action.error.basicMessage", ex.getMessage());
            ResourceMap resource = getResourceMap();
            String title = resource.getString("saveAction.Action.error.title");

            String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
            String category = ProductPanel.class.getName() + ": saveAction.";
            Level errorLevel = Level.WARNING;

            ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex,
                errorLevel, new HashMap<String, String>());
            JBErrorPane.showDialog(this, errorInfo);
        }
    }
}
