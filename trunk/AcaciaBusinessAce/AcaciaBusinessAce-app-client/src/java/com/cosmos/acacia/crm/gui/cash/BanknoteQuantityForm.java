/*
 * BanknoteQuantityForm.java
 *
 * Created on Сряда, 2009, Април 15, 13:15
 */

package com.cosmos.acacia.crm.gui.cash;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.Binding.SyncFailure;

import com.cosmos.acacia.crm.bl.cash.BanknoteQuantityRemote;
import com.cosmos.acacia.crm.bl.cash.CurrencyNominalRemote;
import com.cosmos.acacia.crm.bl.impl.EnumResourceRemote;
import com.cosmos.acacia.crm.data.BanknoteQuantity;
import com.cosmos.acacia.crm.data.CashReconcile;
import com.cosmos.acacia.crm.data.CurrencyNominal;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;

/**
 * 
 * Created	:	01.05.2009
 * @author	Petar Milev
 *
 */
public class BanknoteQuantityForm extends BaseEntityPanel {
    
    private BanknoteQuantity entity;
    
    private BindingGroup bindGroup;
    private BanknoteQuantityRemote formSession = getBean(BanknoteQuantityRemote.class);
    private EnumResourceRemote enumResourceRemote = getBean(EnumResourceRemote.class);
    private CurrencyNominalRemote currencyNominalManager = getBean(CurrencyNominalRemote.class);
    private EntityProperties entProps;
    private EntityProperties curNominalProps;
    
    private BigDecimal amount;
    private BigDecimal amountDefCurrency;

    /** Creates new form */
    public BanknoteQuantityForm(BanknoteQuantity banknoteQuantity, DataObjectBean parent) {
        super(parent);
        this.entity = banknoteQuantity;
        initialize();
    }

    private void initialize() {
        initComponents();
        initComponentsCustom();
        init();
    }

    private void initComponentsCustom() {
        entityFormButtonPanel = new EntityFormButtonPanel();
        BoxLayout boxLayout = new BoxLayout(footerPanel, BoxLayout.Y_AXIS);
        footerPanel.setLayout(boxLayout);
        footerPanel.add(entityFormButtonPanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        currencyField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel1 = new com.cosmos.swingb.JBLabel();
        nominalField = new com.cosmos.acacia.gui.AcaciaComboBox();
        jBLabel2 = new com.cosmos.swingb.JBLabel();
        jBLabel3 = new com.cosmos.swingb.JBLabel();
        totalAmountField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel4 = new com.cosmos.swingb.JBLabel();
        totalAmountDefCurField = new com.cosmos.swingb.JBFormattedTextField();
        jBLabel5 = new com.cosmos.swingb.JBLabel();
        quantityField = new com.cosmos.swingb.JBFormattedTextField();
        footerPanel = new com.cosmos.swingb.JBPanel();

        setName("Form"); // NOI18N

        currencyField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BGN", "USD" }));
        currencyField.setName("currencyField"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(BanknoteQuantityForm.class);
        jBLabel1.setText(resourceMap.getString("jBLabel1.text")); // NOI18N
        jBLabel1.setName("jBLabel1"); // NOI18N

        nominalField.setMaximumRowCount(30);
        nominalField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.01", "0.02", "0.05", "0.1", "0.2", "0.5", "1", "2", "5", "10", "20", "50", "100", "200", "500", "1000", "2000", "5000" }));
        nominalField.setName("nominalField"); // NOI18N

        jBLabel2.setText(resourceMap.getString("jBLabel2.text")); // NOI18N
        jBLabel2.setName("jBLabel2"); // NOI18N

        jBLabel3.setText(resourceMap.getString("jBLabel3.text")); // NOI18N
        jBLabel3.setName("jBLabel3"); // NOI18N

        totalAmountField.setEditable(false);
        totalAmountField.setText(resourceMap.getString("totalAmountField.text")); // NOI18N
        totalAmountField.setName("totalAmountField"); // NOI18N

        jBLabel4.setText(resourceMap.getString("jBLabel4.text")); // NOI18N
        jBLabel4.setName("jBLabel4"); // NOI18N

        totalAmountDefCurField.setEditable(false);
        totalAmountDefCurField.setName("totalAmountDefCurField"); // NOI18N

        jBLabel5.setText(resourceMap.getString("jBLabel5.text")); // NOI18N
        jBLabel5.setName("jBLabel5"); // NOI18N

        quantityField.setText(resourceMap.getString("quantityField.text")); // NOI18N
        quantityField.setName("quantityField"); // NOI18N

        footerPanel.setName("footerPanel"); // NOI18N

        javax.swing.GroupLayout footerPanelLayout = new javax.swing.GroupLayout(footerPanel);
        footerPanel.setLayout(footerPanelLayout);
        footerPanelLayout.setHorizontalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        footerPanelLayout.setVerticalGroup(
            footerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(47, 47, 47)))
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalAmountDefCurField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(totalAmountField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(quantityField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(nominalField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(currencyField, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
                    .addComponent(footerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currencyField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nominalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalAmountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalAmountDefCurField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.acacia.gui.AcaciaComboBox currencyField;
    private com.cosmos.swingb.JBPanel footerPanel;
    private com.cosmos.swingb.JBLabel jBLabel1;
    private com.cosmos.swingb.JBLabel jBLabel2;
    private com.cosmos.swingb.JBLabel jBLabel3;
    private com.cosmos.swingb.JBLabel jBLabel4;
    private com.cosmos.swingb.JBLabel jBLabel5;
    private com.cosmos.acacia.gui.AcaciaComboBox nominalField;
    private com.cosmos.swingb.JBFormattedTextField quantityField;
    private com.cosmos.swingb.JBFormattedTextField totalAmountDefCurField;
    private com.cosmos.swingb.JBFormattedTextField totalAmountField;
    // End of variables declaration//GEN-END:variables
    
    private EntityFormButtonPanel entityFormButtonPanel;

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        return entityFormButtonPanel;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void performSave(boolean closeAfter) {
        entity = formSession.saveBanknoteQuantity(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        } else {
            bindGroup.unbind();
            initData();
        }
    }

    @Override
    protected void initData() {
        
        if (entProps == null)
            entProps = getFormSession().getDetailEntityProperties();
        if (curNominalProps == null)
            curNominalProps = currencyNominalManager.getDetailEntityProperties();

        if (bindGroup == null)
            bindGroup = new BindingGroup();

        beforeBind();
        bind();
        afterBind();
    }
    
    private void beforeBind() {
        //labels
        Currency defaultCurr = (Currency) ((CashReconcile)getMainDataObject()).getCurrency().getEnumValue();
        String currencyDisplay = defaultCurr.getCode();
        setLabelCurrency(jBLabel5, currencyDisplay);
    }
    
    private void setLabelCurrency(JBLabel label, String currencyDisplay) {
        String text = label.getText();
        if ( !text.contains("{0}") )
            return;
        text = MessageFormat.format(text, currencyDisplay);
        label.setText(text);
    }

    private void afterBind() {
        totalAmountField.setFormat(AcaciaUtils.getDecimalFormat());
        totalAmountDefCurField.setFormat(AcaciaUtils.getDecimalFormat());
        
        currencyField.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                onCurrencySelected();
            }
        }, true);
        
        quantityField.getBinding().addBindingListener(new AbstractBindingListener() {
            public void targetChanged(Binding binding, PropertyStateEvent event) {
                updateAmountFields();
            }
            public void syncFailed(Binding binding, SyncFailure failure) {
                updateAmountFields();
            }
        });
        nominalField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAmountFields();
            }
        });
        
        updateAmountFields();
    }

    protected void onCurrencySelected() {
        bindNominalField();
    }
    
    private static class AlignedListCellRenderer extends DefaultListCellRenderer {
        
        private int align;
        
        public AlignedListCellRenderer(int align) {
            this.align = align;
        }
     
        public Component getListCellRendererComponent(JList list, 
                                                      Object value, 
                                                      int index, 
                                                      boolean isSelected, 
                                                      boolean cellHasFocus) {
            // DefaultListCellRenderer uses a JLabel as the rendering component:
            JLabel lbl = (JLabel)super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            lbl.setHorizontalAlignment(align);
            return lbl;
        }
    }
    
    private BigDecimal nominal;
    private Currency currency;
    
    private void bindNominalField() {
        
        if ( nominalField.getBinding()!=null ){
            if ( bindGroup.getBindings().contains(nominalField.getBinding()) )
                bindGroup.removeBinding(nominalField.getBinding());
            if ( bindGroup.getBindings().contains(nominalField.getComboBoxBinding()) )
                bindGroup.removeBinding(nominalField.getComboBoxBinding());
        }
        
        // nominal
        curNominalProps.getPropertyDetails("nominal").setEditable(false);
        nominalField
            .bind(bindGroup, getNominals(), this, curNominalProps.getPropertyDetails("nominal"))
                .bind();
        nominalField.setRenderer(new AlignedListCellRenderer(JLabel.RIGHT));
        if ( nominals.size()>0 )
            nominalField.setSelectedIndex(0);
    }

    private void updateAmountFields() {
        
        if ( nominalField.getSelectedIndex()>=0 ){
            try{
                entity.setCurrencyNominal(nominals.get(nominalField.getSelectedIndex()));
            }catch (IndexOutOfBoundsException o){
                entity.setCurrencyNominal(nominals.get(0));
            }
        }
        
        //valid
        if ( quantityField.getValue()!=null && nominalField.getSelectedItem() instanceof BigDecimal &&
                currencyField.getSelectedItem() instanceof DbResource ){
            totalAmountField.setValue(entity.getAmount());
            Currency defaultCurr = (Currency) ((CashReconcile)getMainDataObject()).getCurrency().getEnumValue();
            totalAmountDefCurField.setValue(entity.getAmount(defaultCurr));
        }
        //invalid
        else{
            totalAmountField.setValue(null);
            totalAmountDefCurField.setValue(null);
        }
    }

    /**
     * Just binds all components
     */
    @SuppressWarnings("deprecation")
    protected void bind() {
        
        // currency
        curNominalProps.getPropertyDetails("currency").setEditable(false);
        currencyField.bind(bindGroup, getCurrencies(), entity.getCurrencyNominal(), curNominalProps.getPropertyDetails("currency"));
        
        // quantity
        quantityField.bind(bindGroup, entity, entProps.getPropertyDetails("quantity"), AcaciaUtils.getIntegerFormat());
        
        //nominals
        bindNominalField();
        
        bindGroup.bind();
    }
    
    private List<CurrencyNominal> nominals = null;
    
    private List<BigDecimal> getNominals() {
        DbResource currency = null;
        if ( currencyField.getSelectedItem() instanceof String ){
            if ( entity.getCurrencyNominal()!=null )
                currency = entity.getCurrencyNominal().getCurrency();
        }else
            currency = (DbResource) currencyField.getSelectedItem();
        
        if ( currency==null )
            nominals = new ArrayList<CurrencyNominal>();
        else
            nominals= currencyNominalManager.getNominals(
                (Currency) currency.getEnumValue());
        List<BigDecimal> nominalValues = new ArrayList<BigDecimal>();
        for (CurrencyNominal currencyNominal : nominals) {
            if ( currencyNominal.equals(entity.getCurrencyNominal()))
                this.nominal = currencyNominal.getNominal();
            nominalValues.add(currencyNominal.getNominal());
        }
        return nominalValues;
    }

    public void setReadonly() {
        super.setReadonly();
    }
    
    public BanknoteQuantityRemote getFormSession() {
        return formSession;
    }
    
    private List<DbResource> currencies; 
    private List getCurrencies() {
        if (currencies == null)
            currencies = enumResourceRemote.getEnumResources(Currency.class);
        return currencies;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountDefCurrency() {
        return amountDefCurrency;
    }

    public void setAmountDefCurrency(BigDecimal amountDefCurrency) {
        this.amountDefCurrency = amountDefCurrency;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
