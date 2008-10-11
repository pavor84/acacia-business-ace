/*
 * SelectionCallbackPanel.java
 *
 * Created on Сряда, 2008, Октомври 8, 20:05
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.crm.assembling.Algorithm;
import com.cosmos.acacia.crm.assembling.ConstraintRow;
import com.cosmos.acacia.crm.assembling.ProductSelectionRow;
import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBScrollPane;
import com.cosmos.swingb.TableSelectionModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Miro
 */
public class SelectionCallbackPanel
    extends AcaciaPanel
{
    private static final Logger logger = Logger.getLogger(ProductAssemblerPanel.class);

    @EJB
    private static AssemblingRemote formSession;

    private ChoiceCallback callback;
    private AcaciaTable productRowsTable;
    private Algorithm.Type algorithmType;
    private Integer minSelections;
    private Integer maxSelections;


    /** Creates new form SelectionCallbackPanel */
    public SelectionCallbackPanel(ChoiceCallback callback)
    {
        this.callback = callback;
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

        constraintsPanel = new com.cosmos.swingb.JBPanel();
        valuePanel = new com.cosmos.swingb.JBPanel();
        valueTextField = new com.cosmos.swingb.JBFormattedTextField();
        valueLabel = new com.cosmos.swingb.JBLabel();
        minSelectionsPanel = new com.cosmos.swingb.JBPanel();
        minSelectionsLabel = new com.cosmos.swingb.JBLabel();
        minSelectionsTextField = new com.cosmos.swingb.JBFormattedTextField();
        maxSelectionsPanel = new com.cosmos.swingb.JBPanel();
        maxSelectionsLabel = new com.cosmos.swingb.JBLabel();
        maxSelectionsTextField = new com.cosmos.swingb.JBFormattedTextField();
        tablePanel = new com.cosmos.swingb.JBPanel();
        buttonPanel = new com.cosmos.swingb.JBPanel();
        selectButton = new com.cosmos.swingb.JBButton();
        cancelButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(SelectionCallbackPanel.class);
        constraintsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("constraintsPanel.border.title"))); // NOI18N
        constraintsPanel.setName("constraintsPanel"); // NOI18N
        constraintsPanel.setLayout(new java.awt.GridLayout(1, 0, 25, 0));

        valuePanel.setName("valuePanel"); // NOI18N
        valuePanel.setLayout(new java.awt.BorderLayout());

        valueTextField.setEditable(false);
        valueTextField.setName("valueTextField"); // NOI18N
        valuePanel.add(valueTextField, java.awt.BorderLayout.CENTER);

        valueLabel.setText(resourceMap.getString("valueLabel.text")); // NOI18N
        valueLabel.setName("valueLabel"); // NOI18N
        valuePanel.add(valueLabel, java.awt.BorderLayout.WEST);

        constraintsPanel.add(valuePanel);

        minSelectionsPanel.setName("minSelectionsPanel"); // NOI18N
        minSelectionsPanel.setLayout(new java.awt.BorderLayout());

        minSelectionsLabel.setText(resourceMap.getString("minSelectionsLabel.text")); // NOI18N
        minSelectionsLabel.setName("minSelectionsLabel"); // NOI18N
        minSelectionsPanel.add(minSelectionsLabel, java.awt.BorderLayout.WEST);

        minSelectionsTextField.setEditable(false);
        minSelectionsTextField.setName("minSelectionsTextField"); // NOI18N
        minSelectionsPanel.add(minSelectionsTextField, java.awt.BorderLayout.CENTER);

        constraintsPanel.add(minSelectionsPanel);

        maxSelectionsPanel.setName("maxSelectionsPanel"); // NOI18N
        maxSelectionsPanel.setLayout(new java.awt.BorderLayout());

        maxSelectionsLabel.setText(resourceMap.getString("maxSelectionsLabel.text")); // NOI18N
        maxSelectionsLabel.setName("maxSelectionsLabel"); // NOI18N
        maxSelectionsPanel.add(maxSelectionsLabel, java.awt.BorderLayout.WEST);

        maxSelectionsTextField.setEditable(false);
        maxSelectionsTextField.setName("maxSelectionsTextField"); // NOI18N
        maxSelectionsPanel.add(maxSelectionsTextField, java.awt.BorderLayout.CENTER);

        constraintsPanel.add(maxSelectionsPanel);

        add(constraintsPanel, java.awt.BorderLayout.PAGE_START);

        tablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("tablePanel.border.title"))); // NOI18N
        tablePanel.setName("tablePanel"); // NOI18N
        tablePanel.setLayout(new java.awt.BorderLayout());
        add(tablePanel, java.awt.BorderLayout.CENTER);

        buttonPanel.setName("buttonPanel"); // NOI18N
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(SelectionCallbackPanel.class, this);
        selectButton.setAction(actionMap.get("selectButton")); // NOI18N
        selectButton.setName("selectButton"); // NOI18N
        buttonPanel.add(selectButton);

        cancelButton.setAction(actionMap.get("cancelButton")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        buttonPanel.add(cancelButton);

        add(buttonPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel buttonPanel;
    private com.cosmos.swingb.JBButton cancelButton;
    private com.cosmos.swingb.JBPanel constraintsPanel;
    private com.cosmos.swingb.JBLabel maxSelectionsLabel;
    private com.cosmos.swingb.JBPanel maxSelectionsPanel;
    private com.cosmos.swingb.JBFormattedTextField maxSelectionsTextField;
    private com.cosmos.swingb.JBLabel minSelectionsLabel;
    private com.cosmos.swingb.JBPanel minSelectionsPanel;
    private com.cosmos.swingb.JBFormattedTextField minSelectionsTextField;
    private com.cosmos.swingb.JBButton selectButton;
    private com.cosmos.swingb.JBPanel tablePanel;
    private com.cosmos.swingb.JBLabel valueLabel;
    private com.cosmos.swingb.JBPanel valuePanel;
    private com.cosmos.swingb.JBFormattedTextField valueTextField;
    // End of variables declaration//GEN-END:variables


    @Override
    protected void initData()
    {
        algorithmType = getAlgorithmType();
        this.minSelections = getMinSelections();
        this.maxSelections = getMaxSelections();
        if(Algorithm.Type.SingleSelectionAlgorithms.contains(algorithmType))
        {
            minSelections = maxSelections = 1;
        }

        AssemblingMessage message = getAssemblingMessage();
        String strValue;
        if((strValue = message.getMessageTitle()) != null)
        {
            setTitle(strValue);
        }

        if((strValue = message.getMessageText()) != null)
        {
            TitledBorder border = (TitledBorder)tablePanel.getBorder();
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

        enableSelectButton(0);

        productRowsTable = new AcaciaTable();
        SelectionModel selectionModel = new SelectionModel();
        productRowsTable.setSelectionModel(selectionModel);
        JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setViewportView(productRowsTable);
        tablePanel.add(BorderLayout.CENTER, scrollPane);

        BindingGroup bindingGroup = new BindingGroup();
        productRowsTable.bind(
            bindingGroup, 
            getProductSelectionRows(),
            getProductSelectionRowEntityProperties());
        bindingGroup.bind();
        productRowsTable.packAll();

        int panelCount = 0;
        if(Algorithm.Type.ValueDependentAlgorithms.contains(algorithmType))
        {
            valueTextField.setValue(getValueAgainstConstraints());
            panelCount++;
        }
        else
            valuePanel.setEnabled(false);

        if(minSelections != null)
        {
            minSelectionsTextField.setValue(minSelections);
            panelCount++;
        }
        else
            minSelectionsPanel.setEnabled(false);

        if(maxSelections != null)
        {
            maxSelectionsTextField.setValue(maxSelections);
            panelCount++;
        }
        else
            maxSelectionsPanel.setEnabled(false);

        if(panelCount == 0)
            constraintsPanel.setEnabled(false);

        setPreferredSize(new Dimension(640, 480));
    }

    private EntityProperties getProductSelectionRowEntityProperties()
    {
        EntityProperties entityProperties =
            getFormSession().getProductSelectionRowEntityProperties();
        PropertyDetails propertyDetails;
        if(Algorithm.Type.EqualsAlgorithms.contains(algorithmType))
        {
            propertyDetails = entityProperties.getPropertyDetails("minConstraint");
            propertyDetails.setColumnName("Value");
            propertyDetails = entityProperties.getPropertyDetails("maxConstraint");
            propertyDetails.setVisible(false);
        }
        else if(Algorithm.Type.UserSelectionAlgorithms.contains(algorithmType))
        {
            propertyDetails = entityProperties.getPropertyDetails("minConstraint");
            propertyDetails.setVisible(false);
            propertyDetails = entityProperties.getPropertyDetails("maxConstraint");
            propertyDetails.setVisible(false);
        }

        return entityProperties;
    }

    private List<ProductSelectionRow> getProductSelectionRows()
    {
        List<ConstraintRow> constraintRows = getChoices();
        List<ProductSelectionRow> rows = new ArrayList<ProductSelectionRow>(constraintRows.size());
        for(ConstraintRow constraintRow : constraintRows)
        {
            rows.add(new ProductSelectionRow(constraintRow));
        }

        return rows;
    }

    private AssemblingSchemaItem getAssemblingSchemaItem()
    {
        return callback.getAssemblingSchemaItem();
    }

    private Algorithm.Type getAlgorithmType()
    {
        return getAssemblingSchemaItem().getAlgorithmType();
    }

    private Integer getMinSelections()
    {
        return getAssemblingSchemaItem().getMinSelections();
    }

    private Integer getMaxSelections()
    {
        return getAssemblingSchemaItem().getMaxSelections();
    }

    private AssemblingMessage getAssemblingMessage()
    {
        return getAssemblingSchemaItem().getAssemblingMessage();
    }

    private Object getValueAgainstConstraints()
    {
        return callback.getValueAgainstConstraints();
    }

    private List<ConstraintRow> getChoices()
    {
        return callback.getChoices();
    }

    /*private int getDefaultChoice()
    {
        return callback.getDefaultChoice();
    }

    private void setSelectedRows(List<ConstraintRow> selectedRows)
    {
        callback.setSelectedRows(selectedRows);
    }

    private void setSelectedRow(ConstraintRow selectedRow)
    {
        callback.setSelectedRow(selectedRow);
    }*/

    private void enableSelectButton(int selectedRowCount)
    {
        logger.info("enableSelectButton(" + selectedRowCount +
            "): minSelections=" + minSelections +
            ", maxSelections=" + maxSelections +
            ", algorithmType=" + algorithmType);
        if(Algorithm.Type.SelectionAlgorithms.contains(algorithmType))
        {
            logger.info("enableSelectButton: SelectionAlgorithms");
            return;
        }

        if(minSelections == null && maxSelections == null)
        {
            logger.info("enableSelectButton: minSelections = maxSelections == null");
            return;
        }

        if(minSelections != null && selectedRowCount < minSelections)
        {
            logger.info("selectedRowCount < minSelections");
            selectButton.setEnabled(false);
            return;
        }

        if(maxSelections != null && selectedRowCount > maxSelections)
        {
            logger.info("selectedRowCount > maxSelections");
            selectButton.setEnabled(false);
            return;
        }

        selectButton.setEnabled(true);
    }

    @Action
    public void selectButton()
    {
        List<ProductSelectionRow> selectionRows;
        int size;
        if((selectionRows = productRowsTable.getSelectedRowObjects()) != null &&
            (size = selectionRows.size()) > 0)
        {
            List<ConstraintRow> selectedRows = new ArrayList<ConstraintRow>(size);
            for(ProductSelectionRow selectionRow : selectionRows)
            {
                selectedRows.add(selectionRow.getConstraintRow());
            }
            setSelectedValues(selectedRows);
            logger.info("selectedRows: " + selectedRows);
            setDialogResponse(DialogResponse.SELECT);
            close();
        }
    }

    @Action
    public void cancelButton()
    {
        setDialogResponse(DialogResponse.CANCEL);
        close();
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getBean(AssemblingRemote.class);
        }

        return formSession;
    }

    private class SelectionModel
        extends TableSelectionModel
    {
        public SelectionModel()
        {
            if(Algorithm.Type.SingleSelectionAlgorithms.contains(algorithmType))
                setSelectionMode(SINGLE_SELECTION);
            else
                setSelectionMode(MULTIPLE_INTERVAL_SELECTION);
        }

        @Override
        protected void set(int r)
        {
            /*if(value.get(r))
                return;

            if(maxSelections != null && value.cardinality() >= maxSelections)
                return;*/

            super.set(r);
            enableSelectButton(value.cardinality());
        }

        @Override
        protected void clear(int r)
        {
            /*if(!value.get(r))
                return;

            if(minSelections != null && value.cardinality() <= minSelections)
                return;*/

            super.clear(r);
            enableSelectButton(value.cardinality());
        }
    }
}
