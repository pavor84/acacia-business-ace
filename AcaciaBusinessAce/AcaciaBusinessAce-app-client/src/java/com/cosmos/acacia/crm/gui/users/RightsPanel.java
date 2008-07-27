/*
 * RightsPanel.java
 *
 * Created on 25 July 2008, 17:36
 */

package com.cosmos.acacia.crm.gui.users;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.cosmos.acacia.crm.bl.users.UserRightsRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.UserRight;
import com.cosmos.acacia.crm.gui.DataObjectTypesListPanel;
import com.cosmos.acacia.gui.AcaciaToStringConverter;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class RightsPanel extends BaseEntityPanel {

    /** Creates new form RightsPanel */
    public RightsPanel(UserRight right) {
        super((BigInteger) null);
        this.right = right;
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

        dataObjectTypeLabel = new com.cosmos.swingb.JBLabel();
        dataObjectLabel = new com.cosmos.swingb.JBLabel();
        readCheckBox = new com.cosmos.swingb.JBCheckBox();
        createCheckBox = new com.cosmos.swingb.JBCheckBox();
        modifyCheckBox = new com.cosmos.swingb.JBCheckBox();
        deleteCheckBox = new com.cosmos.swingb.JBCheckBox();
        entityFormButtonPanel = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        dataObjectTypeComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();
        dataObjectComboBox = new com.cosmos.acacia.gui.AcaciaComboBox();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(RightsPanel.class);
        dataObjectTypeLabel.setText(resourceMap.getString("dataObjectTypeLabel.text")); // NOI18N
        dataObjectTypeLabel.setName("dataObjectTypeLabel"); // NOI18N

        dataObjectLabel.setText(resourceMap.getString("dataObjectLabel.text")); // NOI18N
        dataObjectLabel.setName("dataObjectLabel"); // NOI18N

        readCheckBox.setText(resourceMap.getString("readCheckBox.text")); // NOI18N
        readCheckBox.setName("readCheckBox"); // NOI18N

        createCheckBox.setText(resourceMap.getString("createCheckBox.text")); // NOI18N
        createCheckBox.setName("createCheckBox"); // NOI18N

        modifyCheckBox.setText(resourceMap.getString("modifyCheckBox.text")); // NOI18N
        modifyCheckBox.setName("modifyCheckBox"); // NOI18N

        deleteCheckBox.setText(resourceMap.getString("deleteCheckBox.text")); // NOI18N
        deleteCheckBox.setName("deleteCheckBox"); // NOI18N

        entityFormButtonPanel.setName("entityFormButtonPanel"); // NOI18N

        dataObjectTypeComboBox.setName("dataObjectTypeComboBox"); // NOI18N

        dataObjectComboBox.setName("dataObjectComboBox"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(readCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modifyCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataObjectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(dataObjectTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataObjectTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataObjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {createCheckBox, deleteCheckBox, modifyCheckBox, readCheckBox});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataObjectTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataObjectTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataObjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataObjectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(readCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modifyCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityFormButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBCheckBox createCheckBox;
    private com.cosmos.acacia.gui.AcaciaComboBox dataObjectComboBox;
    private com.cosmos.swingb.JBLabel dataObjectLabel;
    private com.cosmos.acacia.gui.AcaciaComboBox dataObjectTypeComboBox;
    private com.cosmos.swingb.JBLabel dataObjectTypeLabel;
    private com.cosmos.swingb.JBCheckBox deleteCheckBox;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel;
    private com.cosmos.swingb.JBCheckBox modifyCheckBox;
    private com.cosmos.swingb.JBCheckBox readCheckBox;
    // End of variables declaration//GEN-END:variables


    private UserRightsRemote formSession;
    private UserRight right;
    private BindingGroup rightsBindingGroup;

    @Override
    protected void initData() {

        rightsBindingGroup = new BindingGroup();
        EntityProperties entityProps = getFormSession().getUserRightEntityProperties();

        AcaciaToStringConverter converter = new AcaciaToStringConverter("${dataObjectType}");
        dataObjectTypeComboBox.bind(rightsBindingGroup,
                getDataObjectTypes(),
                right,
                entityProps.getPropertyDetails("dataObjectType"),
                converter);
        AutoCompleteDecorator.decorate(dataObjectTypeComboBox, converter);

        readCheckBox.bind(rightsBindingGroup, right, entityProps.getPropertyDetails("read"));
        createCheckBox.bind(rightsBindingGroup, right, entityProps.getPropertyDetails("create"));
        modifyCheckBox.bind(rightsBindingGroup, right, entityProps.getPropertyDetails("modify"));
        deleteCheckBox.bind(rightsBindingGroup, right, entityProps.getPropertyDetails("delete"));

        rightsBindingGroup.bind();
        
        AcaciaToStringConverter dobConverter = new AcaciaToStringConverter("${info}");
        dataObjectComboBox.setConverter(dobConverter);
        updateDataObjectComboBox((DataObjectType) dataObjectTypeComboBox.getSelectedItem());
        AutoCompleteDecorator.decorate(dataObjectComboBox, dobConverter);
        
        dataObjectTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    if (item instanceof DataObjectType) {
                        updateDataObjectComboBox((DataObjectType) item);
                    }
                }
            }
        });
    }

    private void updateDataObjectComboBox(DataObjectType type) {
        dataObjectComboBox.removeAllItems();
        List<DataObjectBean> dataObjectBeans = getDataObjectBeans(type);

        boolean isPreChosen = false;
        for (DataObjectBean dob : dataObjectBeans) {
            dataObjectComboBox.addItem(dob);
            
            if (dob.equals(right.getDataObject())) {
                dataObjectComboBox.setSelectedItem(dob);
                isPreChosen = true;
            }
        }
        
        if (!isPreChosen)
            dataObjectComboBox.setSelectedIndex(-1);

    }
    
    protected UserRightsRemote getFormSession() {
        if (formSession == null)
            formSession = getBean(UserRightsRemote.class);

        return formSession;
    }

    private List<DataObjectBean> getDataObjectBeans(DataObjectType dataObjectType) {
        List<DataObjectBean> dataObjectBeans =
                            getFormSession().getDataObjectBeans(dataObjectType);

        return dataObjectBeans;
    }
    
    private List<DataObjectType> getDataObjectTypes() {
        return DataObjectTypesListPanel.shortenDataObjectTypeNames(getFormSession().getDataObjectTypes());
    }

    @Override
    public BindingGroup getBindingGroup() {
        return rightsBindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    public Object getEntity() {
        return right;
    }

    @Override
    public void performSave(boolean closeAfter) {
        DataObjectBean dob = (DataObjectBean) dataObjectComboBox.getSelectedItem();
        if (dob != null) {
            right.setDataObject(dob.getDataObject());
            right.setObjectInfo(dob.getInfo());
        }
        
        setSelectedValue(right);
        setDialogResponse(DialogResponse.SAVE);
        close();
    }

}