/*
 * ProductAssemblerPanel.java
 *
 * Created on Вторник, 2008, Август 19, 19:19
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.assembling.AlgorithmException;
import com.cosmos.acacia.crm.assembling.ProductAssembler;
import com.cosmos.acacia.crm.assembling.ProductAssemblerEvent;
import com.cosmos.acacia.crm.assembling.ProductAssemblerListener;
import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBScrollPane;
import java.awt.Component;
import java.lang.Object;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Miro
 */
public class ProductAssemblerPanel
    extends AcaciaPanel
{
    @EJB
    private static AssemblingRemote formSession;


    /** Creates new form ProductAssemblerPanel */
    public ProductAssemblerPanel()
    {
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

        schemaTitledPanel = new com.cosmos.swingb.JBTitledPanel();
        buttonsPanel = new com.cosmos.swingb.JBPanel();
        assembleButton = new com.cosmos.swingb.JBButton();
        cancelButton = new com.cosmos.swingb.JBButton();
        okButton = new com.cosmos.swingb.JBButton();
        productTitledPanel = new com.cosmos.swingb.JBTitledPanel();

        setName("Form"); // NOI18N

        schemaTitledPanel.setName("schemaTitledPanel"); // NOI18N

        buttonsPanel.setName("buttonsPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(ProductAssemblerPanel.class, this);
        assembleButton.setAction(actionMap.get("assembleAction")); // NOI18N
        assembleButton.setName("assembleButton"); // NOI18N

        cancelButton.setAction(actionMap.get("cancelAction")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        okButton.setAction(actionMap.get("okAction")); // NOI18N
        okButton.setName("okButton"); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(assembleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {assembleButton, cancelButton, okButton});

        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(assembleButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        buttonsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {assembleButton, cancelButton, okButton});

        productTitledPanel.setName("productTitledPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(schemaTitledPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
            .addComponent(productTitledPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(schemaTitledPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productTitledPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton assembleButton;
    private com.cosmos.swingb.JBPanel buttonsPanel;
    private com.cosmos.swingb.JBButton cancelButton;
    private com.cosmos.swingb.JBButton okButton;
    private com.cosmos.swingb.JBTitledPanel productTitledPanel;
    private com.cosmos.swingb.JBTitledPanel schemaTitledPanel;
    // End of variables declaration//GEN-END:variables


    private AcaciaTable parametersTable;
    //private AcaciaTreeTable productTreeTable;
    private AcaciaTable productTreeTable;

    private AssemblingSchema assemblingSchema;
    private List<AssemblingSchemaItem> schemaItems;

    private List<ComplexProductItem> complexProductItems;

    @Override
    protected void initData()
    {
        okButton.setEnabled(false);
        ResourceMap resource = getResourceMap();

        String strValue;
        parametersTable = new AcaciaTable();
        parametersTable.setModel(new SchemaTableModel());
        JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setViewportView(parametersTable);
        schemaTitledPanel.setContentContainer(scrollPane);

        strValue = resource.getString("productTitledPanel.title");
        productTitledPanel.setTitle(strValue);
        //productTreeTable = new AcaciaTreeTable();
        productTreeTable = new AcaciaTable();
        BindingGroup bindingGroup = new BindingGroup();
        EntityProperties entityProperties =
            getFormSession().getComplexProductItemEntityProperties();
        productTreeTable.bind(bindingGroup, getComplexProductItems(), entityProperties);
        scrollPane = new JBScrollPane();
        scrollPane.setViewportView(productTreeTable);
        productTitledPanel.setContentContainer(scrollPane);

        parametersTable.setEditable(true);
    }

    private void initParameters()
    {
        ResourceMap resource = getResourceMap();
        AssemblingSchema schema = getAssemblingSchema();

        DefaultTableModel model = (DefaultTableModel)parametersTable.getModel();
        for(AssemblingSchemaItem item : getSchemaItems())
        {
            String message = item.getMessageCode() + ": " + item.getMessageText();
            Object value = item.getDefaultValue();
            model.addRow(new Object[] {message, value});
        }

        String schemaName = schema.getSchemaCode() + " - " + schema.getSchemaName();
        String strValue = resource.getString("schemaTitledPanel.title");
        schemaTitledPanel.setTitle(strValue + schemaName);
    }

    @Override
    public DialogResponse showDialog(Component parentComponent)
    {
        DialogResponse response;
        if(!DialogResponse.SELECT.equals((response = selectAssemblingSchema(parentComponent))))
        {
            return response;
        }

        return super.showDialog(parentComponent);
    }

    @Override
    public void showFrame(Component parentComponent)
    {
        if(!DialogResponse.SELECT.equals(selectAssemblingSchema(parentComponent)))
        {
            return;
        }

        super.showFrame(parentComponent);
    }

    private DialogResponse selectAssemblingSchema(Component parentComponent)
    {
        AssemblingSchema schema = getAssemblingSchema();
        if(schema != null)
        {
            initParameters();
            return DialogResponse.SELECT;
        }

        AssemblingSchemasPanel asPanel = new AssemblingSchemasPanel(
            AssemblingSchemasPanel.Mode.AssembleSchemaSelect);
        DialogResponse response = asPanel.showDialog(parentComponent);
        if(!DialogResponse.SELECT.equals(response))
        {
            close();
            return response;
        }

        schema = (AssemblingSchema)asPanel.getSelectedRowObject();
        setAssemblingSchema(schema);
        initParameters();
        return response;
    }

    @Action
    public void cancelAction()
    {
        close();
    }

    @Action
    public void okAction()
    {
    }

    @Action
    public void assembleAction()
    {
        productAssemble();
    }

    private void productAssemble()
    {
        Properties params = new Properties();

        ProductAssembler assembler =
            new ProductAssembler(getAssemblingSchema(),
            getFormSession(),
            new AssemblerCallbackHandler());
        ComplexProductAssemblerListener listener = new ComplexProductAssemblerListener();
        assembler.addProductAssemblerListener(listener);

        try
        {
            ComplexProduct product = assembler.assemble(params);
            log.info("Product: " + product.toString(true));
        }
        catch(AlgorithmException ex)
        {
            log.info("EXC: " + ex.getMessage());
            ValidationException vex = new ValidationException();
            vex.addMessage(ex.getMessage());
            throw vex;
        }
        finally
        {
            assembler.removeProductAssemblerListener(listener);
        }
    }

    public AssemblingSchema getAssemblingSchema()
    {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema)
    {
        this.assemblingSchema = assemblingSchema;
    }

    public List<AssemblingSchemaItem> getSchemaItems()
    {
        if(schemaItems == null)
        {
            schemaItems = getFormSession().getAssemblingSchemaItems(getAssemblingSchema());
        }

        return schemaItems;
    }

    public List<ComplexProductItem> getComplexProductItems()
    {
        if(complexProductItems == null)
        {
            complexProductItems = new ArrayList<ComplexProductItem>();
        }

        return complexProductItems;
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getBean(AssemblingRemote.class);
        }

        return formSession;
    }

    private class ComplexProductAssemblerListener
        implements ProductAssemblerListener
    {

        @Override
        public void productAssemblerEvent(ProductAssemblerEvent event)
        {
            ComplexProductItem productItem = event.getComplexProductItem();
            log.info("event.getComplexProductItem(): " + productItem);
            log.info("\t AppliedAlgorithm: " + productItem.getAppliedAlgorithm());
            log.info("\t AppliedValue: " + productItem.getAppliedValue());
            log.info("\t ComplexProduct: " + productItem.getComplexProduct());
            log.info("\t ComplexProductItemId: " + productItem.getComplexProductItemId());
            log.info("\t Info: " + productItem.getInfo());
            log.info("\t OrderPosition: " + productItem.getOrderPosition());
            log.info("\t Product: " + productItem.getProduct());
            log.info("\t Quantity: " + productItem.getQuantity());
            log.info("\t UnitPrice: " + productItem.getUnitPrice());
            productTreeTable.addRow(productItem);
            System.out.println("getComplexProductItems(): " + getComplexProductItems());
        }

    }


    private class SchemaTableModel
        extends DefaultTableModel
    {
        private Class[] columnType =
        {
            String.class, Object.class
        };

        private boolean[] canEditColumn =
        {
            false, true
        };

        public SchemaTableModel()
        {
            super();

            ResourceMap resource = getResourceMap();

            String strValue;
            strValue = resource.getString("productTreeTable.column.parameter.title");
            addColumn(strValue);

            strValue = resource.getString("productTreeTable.column.value.title");
            addColumn(strValue);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return columnType[columnIndex];
        }

        @Override
        public boolean isCellEditable(int row, int column)
        {
            return canEditColumn[column];
        }


    }
}
