/*
 * InputCallbackPanel.java
 *
 * Created on Сряда, 2008, Октомври 8, 20:05
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.callback.assembling.ValueInputCallback;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.gui.AcaciaComboBox;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBDatePicker;
import com.cosmos.swingb.JBFormattedTextField;
import com.cosmos.util.CosmosUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.Serializable;
import java.text.Format;
import java.util.Date;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

/**
 *
 * @author  Miro
 */
public class InputCallbackPanel
    extends AcaciaPanel
{
    private static final Logger logger = Logger.getLogger(InputCallbackPanel.class);

    private ValueInputCallback callback;
    private AssemblingSchemaItem schemaItem;
    private AssemblingSchemaItem.DataType dataType;

    /** Creates new form InputCallbackPanel */
    public InputCallbackPanel(ValueInputCallback callback)
    {
        this.callback = callback;
        this.schemaItem = callback.getAssemblingSchemaItem();
        this.dataType = (AssemblingSchemaItem.DataType)schemaItem.getDataType().getEnumValue();
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonsPanel = new com.cosmos.swingb.JBPanel();
        okButton = new com.cosmos.swingb.JBButton();
        cancelButton = new com.cosmos.swingb.JBButton();
        valuePanel = new com.cosmos.swingb.JBPanel();
        valueLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        buttonsPanel.setName("buttonsPanel"); // NOI18N
        buttonsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(InputCallbackPanel.class, this);
        okButton.setAction(actionMap.get("okButton")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        buttonsPanel.add(okButton);

        cancelButton.setAction(actionMap.get("cancelButton")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        buttonsPanel.add(cancelButton);

        add(buttonsPanel, java.awt.BorderLayout.PAGE_END);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(InputCallbackPanel.class);
        valuePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("valuePanel.border.title"))); // NOI18N
        valuePanel.setName("valuePanel"); // NOI18N
        valuePanel.setLayout(new java.awt.BorderLayout());

        valueLabel.setText(resourceMap.getString("valueLabel.text")); // NOI18N
        valueLabel.setName("valueLabel"); // NOI18N
        valuePanel.add(valueLabel, java.awt.BorderLayout.WEST);

        add(valuePanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel buttonsPanel;
    private com.cosmos.swingb.JBButton cancelButton;
    private com.cosmos.swingb.JBButton okButton;
    private com.cosmos.swingb.JBLabel valueLabel;
    private com.cosmos.swingb.JBPanel valuePanel;
    // End of variables declaration//GEN-END:variables


    private JBFormattedTextField valueTextField;
    private JBDatePicker valueDatePicker;
    private AcaciaComboBox valueComboBox;

    @Override
    protected void initData()
    {
        CosmosUtils.sameSize(okButton, cancelButton);

        AssemblingMessage message = getAssemblingMessage();
        String strValue;
        if((strValue = message.getMessageTitle()) != null)
        {
            setTitle(strValue);
        }

        if((strValue = message.getMessageText()) != null)
        {
            TitledBorder border = (TitledBorder)valuePanel.getBorder();
            border.setTitle(strValue);
        }

        if((strValue = message.getMessageLabel()) != null)
        {
            if(!strValue.trim().endsWith(":"))
            {
                strValue += ": ";
            }
            if(!strValue.endsWith(" "))
            {
                strValue += " ";
            }
            valueLabel.setText(strValue);
        }

        Object value = callback.getInitialValue();
        switch(dataType)
        {
            case IntegerType:
            case DecimalType:
            case StringType:
                JBFormattedTextField textField = getValueTextField();
                valuePanel.add(textField, BorderLayout.CENTER);
                if(value != null)
                    textField.setValue(value);
                break;

            case DateType:
                JBDatePicker datePicker = getValueDatePicker();
                valuePanel.add(datePicker, BorderLayout.CENTER);
                if(value != null)
                    datePicker.setDate((Date)value);
                break;

            case EnumerationType:
                AcaciaComboBox comboBox = getValueComboBox();
                valuePanel.add(comboBox, BorderLayout.CENTER);
                if(value != null)
                    comboBox.setSelectedItem(value);
                break;

            default:
                throw new UnsupportedOperationException("Unknown DateType: " + dataType);
        }

        Dimension size = getPreferredSize();
        size.width = 320;
        setPreferredSize(size);
    }

    private AssemblingMessage getAssemblingMessage()
    {
        return schemaItem.getAssemblingMessage();
    }

    private JBFormattedTextField getValueTextField()
    {
        if(valueTextField == null)
        {
            Format format;
            switch(dataType)
            {
                case IntegerType:
                    format = AcaciaUtils.getIntegerFormat();
                    break;

                case DecimalType:
                    format = AcaciaUtils.getDecimalFormat();
                    break;

                default:
                    format = null;
                    break;
            }

            if(format != null)
                valueTextField = new JBFormattedTextField(format);
            else
                valueTextField = new JBFormattedTextField();
            valueTextField.setName("valueTextField"); // NOI18N
        }

        return valueTextField;
    }

    private JBDatePicker getValueDatePicker()
    {
        if(valueDatePicker == null)
        {
            valueDatePicker = new JBDatePicker();
            valueDatePicker.setName("valueDatePicker"); // NOI18N
        }

        return valueDatePicker;
    }

    private AcaciaComboBox getValueComboBox()
    {
        if(valueComboBox == null)
        {
            valueComboBox = new AcaciaComboBox();
            valueComboBox.setName("valueComboBox"); // NOI18N
        }

        return valueComboBox;
    }

    private Serializable getValue()
    {
        Serializable value;
        switch(dataType)
        {
            case IntegerType:
            case DecimalType:
                value = (Serializable)getValueTextField().getValue();
                break;

            case StringType:
                value = getValueTextField().getText();
                if(value != null)
                {
                    String strValue = (String)value;
                    if((strValue = strValue.trim()).length() == 0)
                        value = null;
                }
                break;

            case DateType:
                value = getValueDatePicker().getDate();
                break;

            case EnumerationType:
                value = (Serializable)getValueComboBox().getSelectedItem();
                break;

            default:
                throw new UnsupportedOperationException("Unknown DateType: " + dataType);
        }

        logger.info("getValue().value: " + value);
        return AcaciaUtils.validateValue(value, dataType, "value");
    }

    @Action
    public void okButton()
    {
        logger.info("okButton()");
        Object value = getValue();
        logger.info("okButton().value: " + value);

        if(value == null)
            return;

        setSelectedValue(value);
        setDialogResponse(DialogResponse.OK);
        close();
    }

    @Action
    public void cancelButton()
    {
        setDialogResponse(DialogResponse.CANCEL);
        close();
    }
}
