/*
 * WarehousePanel.java
 *
 * Created on Петък, 2008, Май 2, 15:27
 */

package com.cosmos.acacia.crm.gui;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.Warehouse;
import com.cosmos.acacia.crm.gui.contactbook.AddressListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PersonsListPanel;
import com.cosmos.acacia.gui.AcaciaLookupProvider;
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
        super(parent);
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
        branchField = new com.cosmos.acacia.gui.AcaciaLookup();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        warehousemanField = new com.cosmos.acacia.gui.AcaciaLookup();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new com.cosmos.swingb.JBTextPane();
        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(WarehousePanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N

        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        branchField.setName("branchField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        warehousemanField.setName("warehousemanField"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        descriptionField.setName("descriptionField"); // NOI18N
        jScrollPane1.setViewportView(descriptionField);

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                            .addComponent(warehousemanField, 0, 0, Short.MAX_VALUE)
                            .addComponent(branchField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(branchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(warehousemanField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        branchField.bind(new AcaciaLookupProvider() {
            
            @Override
            public Object showSelectionControl() {
                return onChooseBranch();
            }
        
        }, bindGroup, 
        entity, 
        propDetails, 
        "${addressName}",
        UpdateStrategy.READ_WRITE);
        
        //warehouseman
        propDetails = entProps.getPropertyDetails("warehouseman");
        warehousemanField.bind(new AcaciaLookupProvider() {
            
            @Override
            public Object showSelectionControl() {
                return onChooseWarehouseman();
            }
        
        }, bindGroup, 
        entity, 
        propDetails, 
        "${firstName} ${lastName}",
        UpdateStrategy.READ_WRITE);
        
        //description
        descriptionField.bind(bindGroup, entity, "description");
        
        bindGroup.bind();
    }
    
    protected Object onChooseWarehouseman() {
        PersonsListPanel listPanel = new PersonsListPanel(null);
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        }else{
            return null;
        }
    }

    protected Object onChooseBranch() {
        DataObject dataObject = getFormSession().getDataObjectWithAddresses();
        AddressListPanel listPanel = new AddressListPanel(dataObject);
        DialogResponse dResponse = listPanel.showDialog(this);
        if ( DialogResponse.SELECT.equals(dResponse) ){
            return listPanel.getSelectedRowObject();
        }else{
            return null;
        }
    }
    
    protected WarehouseListRemote getFormSession() {
        if (formSession == null) {
            try {
                formSession = InitialContext.doLookup(WarehouseListRemote.class.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return formSession;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaLookup branchField;
    private com.cosmos.swingb.JBTextPane descriptionField;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.cosmos.acacia.gui.AcaciaLookup warehousemanField;
    // End of variables declaration//GEN-END:variables
    
}
