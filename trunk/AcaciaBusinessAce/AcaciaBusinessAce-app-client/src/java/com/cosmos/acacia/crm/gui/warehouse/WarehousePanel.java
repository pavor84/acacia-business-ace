/*
 * WarehousePanel.java
 *
 * Created on Петък, 2008, Май 2, 15:27
 */

package com.cosmos.acacia.crm.gui.warehouse;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.ejb.EJB;

import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.ContactPersonsListPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * @author  jchan
 */
public class WarehousePanel extends BaseEntityPanel {

    @EJB
    private WarehouseListRemote formSession;
    
    private Warehouse entity;

    private EntityProperties entProps;

    private BindingGroup bindGroup;

    /** Creates new form WarehousePanel */
    public WarehousePanel(Warehouse w, DataObject parent) {
        super(parent != null ? parent.getDataObjectId() : null);
        this.entity = w;
        init();
    }

    public WarehousePanel(Warehouse w){
        this(w,null);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBLabel1 = new com.cosmos.swingb.JBLabel();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextPane();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        branchField = new com.cosmos.acacia.gui.AcaciaComboList();
        warehousemanField = new com.cosmos.acacia.gui.AcaciaComboList();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(WarehousePanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        branchField.setName("branchField"); // NOI18N

        warehousemanField.setName("warehousemanField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(branchField, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(warehousemanField, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)))
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(branchField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(warehousemanField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void performSave(boolean closeAfter) {
        entity = getFormSession().saveWarehouse(entity);
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

    @Override
    protected void initData() {
        entProps = getFormSession().getWarehouseEntityProperties();
        PropertyDetails propDetails;

        bindGroup = new BindingGroup();

        //branch address
        propDetails = entProps.getPropertyDetails("address");
        AddressListPanel addressListPanel = new AddressListPanel(getOrganizationDataObjectId());
        addressListPanel.setTitle(getResourceMap().getString("AddressListPanel.title"));
        branchField.bind(
            bindGroup, 
            addressListPanel,
            entity,
            propDetails,
            "${addressName}",
            UpdateStrategy.READ_WRITE);
        branchField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bindWarehouseManField(entity.getAddress());
            }
        },true);
        
        //warehouse-man
        bindWarehouseManField(entity.getAddress());
//        propDetails = entProps.getPropertyDetails("warehouseman");
//        BusinessPartnersListPanel employeeListPanel = BusinessPartnersListPanel.createEmployeesPanel(getOrganizationDataObjectId());
//        warehousemanField.bind(bindGroup, employeeListPanel, entity, propDetails,
//            "${firstName} ${lastName}", UpdateStrategy.READ_WRITE);
        
        //description
        descriptionField.bind(bindGroup, entity, "description");

        bindGroup.bind();
    }
    
    @SuppressWarnings("unchecked")
    Binding warehouseManBinding = null;
    
    protected void bindWarehouseManField(Address address) {
        
        ContactPersonsListPanel listPanel = null;
        if ( address!=null ){
            listPanel = new ContactPersonsListPanel(address.getAddressId());
        }
        else{
            listPanel = new ContactPersonsListPanel(null);
        }
        
        if ( bindGroup.getBindings().contains(warehouseManBinding) )
            bindGroup.removeBinding(warehouseManBinding);
        
        warehouseManBinding = warehousemanField.bind(bindGroup, listPanel, entity, entProps.getPropertyDetails("warehouseman"),
            "${contact.firstName} ${contact.lastName}", UpdateStrategy.READ_WRITE);
        warehouseManBinding.bind();

        // auto select if one choice is available
        if (warehousemanField.getData().size() == 1) {
            warehousemanField.setSelectedItem(warehousemanField.getData().get(0));
        } else {
            warehousemanField.setSelectedItem(null);
        }
    }

    protected WarehouseListRemote getFormSession() {
        if (formSession == null)
            formSession = getBean(WarehouseListRemote.class);

        return formSession;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboList branchField;
    private com.cosmos.swingb.JBTextPane descriptionField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.acacia.gui.AcaciaComboList warehousemanField;
    // End of variables declaration//GEN-END:variables
}
