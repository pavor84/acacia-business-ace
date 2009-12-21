/*
 * DeliveryCertificateItemForm.java
 *
 * Created on Неделя, 2008, Октомври 5, 21:17
 */

package com.cosmos.acacia.crm.gui.deliverycertificates;

import static com.cosmos.acacia.util.AcaciaUtils.getIntegerFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.error.ErrorInfo;

import com.cosmos.acacia.crm.bl.impl.DeliveryCertificatesRemote;
import com.cosmos.acacia.crm.data.product.ComplexProduct;
import com.cosmos.acacia.crm.data.warehouse.DeliveryCertificateItem;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.gui.ProductPanel;
import com.cosmos.acacia.crm.gui.invoice.ComplexProductDetailsPanel;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBErrorPane;

/**
 *
 * @author  daniel
 */
public class DeliveryCertificateItemForm extends BaseEntityPanel {

    private static final long serialVersionUID = 9082720492408464360L;
	
    private DeliveryCertificateItem entity;
    private BindingGroup bindingGroup;
    private DeliveryCertificatesRemote formSession;
    private EntityProperties entProps;
    private Product product;
    
    public DeliveryCertificateItemForm(DeliveryCertificateItem entity) {
        super(entity.getCertificateItemId());
        this.entity = entity;
        initialize();
    }
    
    private void initialize() {
        initComponents();
        init();
    }
    
    @Override
    protected void initData() {
    	entProps = getFormSession().getDeliveryCertificateItemDetailsEntityProperties();
    	BindingGroup bg = getBindingGroup();
    	
    	productTextField.setText(entity.getProduct().getProductName());
    	productTextField.setEditable(false);
    	
    	product = entity.getProduct();
    	if(product instanceof SimpleProduct){
    		detailButton.setEnabled(false);
    	}
    	
    	detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onComplexProductDetails();
            }
        });
    	
    	quantityFormattedTextField.bind(bg, entity, entProps.getEntityProperty("quantity"), getIntegerFormat());
    	bg.bind();
    }
    
    protected void onComplexProductDetails() {
        ComplexProductDetailsPanel detailsPanel = new ComplexProductDetailsPanel((ComplexProduct)product);
        detailsPanel.showDialog(this);
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
        return entity;
    }

    @Override
    public void performSave(boolean closeAfter) {
        try{
	    	getFormSession().saveDeliveryCertificateItem(entity);
	        setDialogResponse(DialogResponse.SAVE);
	        setSelectedValue(entity);
	        if (closeAfter) {
	            close();
	        } else {
	            bindingGroup.unbind();
	            initData();
	        }
        }catch(Exception ex){
    		ValidationException ve = extractValidationException(ex);
            if ( ve!=null ){
                String message = getValidationErrorsMessage(ve);
                JBErrorPane.showDialog(this, createSaveErrorInfo(message, null));
            }else{
                ex.printStackTrace();
                // TODO: Log that error
                String basicMessage = getResourceMap().getString("saveAction.Action.error.basicMessage", ex.getMessage());
                JBErrorPane.showDialog(this, createSaveErrorInfo(basicMessage, ex));
            }
    	}
    }
    
    private ErrorInfo createSaveErrorInfo(String basicMessage, Exception ex) {
        ResourceMap resource = getResourceMap();
        String title = resource.getString("saveAction.Action.error.title");

        String detailedMessage = resource.getString("saveAction.Action.error.detailedMessage");
        String category = ProductPanel.class.getName() + ": saveAction.";
        Level errorLevel = Level.WARNING;

        Map<String, String> state = new HashMap<String, String>();
        state.put("deliveryCertificateItemId", String.valueOf(entity.getCertificateItemId()));
        
        ErrorInfo errorInfo = new ErrorInfo(title, basicMessage, detailedMessage, category, ex, errorLevel, state);
        return errorInfo;
    }
    
    protected DeliveryCertificatesRemote getFormSession(){
        if(formSession == null){
            formSession = getBean(DeliveryCertificatesRemote.class);
        }

        return formSession;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityFormButtonPanel1 = new com.cosmos.acacia.gui.EntityFormButtonPanel();
        jBPanel1 = new com.cosmos.swingb.JBPanel();
        productLabel = new com.cosmos.swingb.JBLabel();
        quantityLabel = new com.cosmos.swingb.JBLabel();
        productTextField = new com.cosmos.swingb.JBTextField();
        detailButton = new com.cosmos.swingb.JBButton();
        quantityFormattedTextField = new com.cosmos.swingb.JBFormattedTextField();

        setName("Form"); // NOI18N

        entityFormButtonPanel1.setName("entityFormButtonPanel1"); // NOI18N

        jBPanel1.setName("jBPanel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(DeliveryCertificateItemForm.class);
        productLabel.setText(resourceMap.getString("productLabel.text")); // NOI18N
        productLabel.setName("productLabel"); // NOI18N

        quantityLabel.setText(resourceMap.getString("quantityLabel.text")); // NOI18N
        quantityLabel.setName("quantityLabel"); // NOI18N

        productTextField.setText(resourceMap.getString("productTextField.text")); // NOI18N
        productTextField.setName("productTextField"); // NOI18N

        detailButton.setText(resourceMap.getString("detailButton.text")); // NOI18N
        detailButton.setName("detailButton"); // NOI18N

        quantityFormattedTextField.setText(resourceMap.getString("quantityFormattedTextField.text")); // NOI18N
        quantityFormattedTextField.setName("quantityFormattedTextField"); // NOI18N

        javax.swing.GroupLayout jBPanel1Layout = new javax.swing.GroupLayout(jBPanel1);
        jBPanel1.setLayout(jBPanel1Layout);
        jBPanel1Layout.setHorizontalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBPanel1Layout.createSequentialGroup()
                        .addComponent(productTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(detailButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(quantityFormattedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBPanel1Layout.setVerticalGroup(
            jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detailButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entityFormButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton detailButton;
    private com.cosmos.acacia.gui.EntityFormButtonPanel entityFormButtonPanel1;
    private com.cosmos.swingb.JBPanel jBPanel1;
    private com.cosmos.swingb.JBLabel productLabel;
    private com.cosmos.swingb.JBTextField productTextField;
    private com.cosmos.swingb.JBFormattedTextField quantityFormattedTextField;
    private com.cosmos.swingb.JBLabel quantityLabel;
    // End of variables declaration//GEN-END:variables

}
