/*
 * ProductAssemblerPanel.java
 *
 * Created on Вторник, 2008, Август 19, 19:19
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.assembling.ProductAssembler;
import com.cosmos.acacia.crm.assembling.ProductAssemblerEvent;
import com.cosmos.acacia.crm.assembling.ProductAssemblerListener;
import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.ComplexProduct;
import com.cosmos.acacia.crm.data.ComplexProductItem;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.assembling.AssemblingParameter;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.data.assembling.VirtualProduct;
import com.cosmos.acacia.crm.gui.ProductItemTreeTableNode;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.acacia.gui.AcaciaTreeTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBFormattedTextField;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.Serializable;
import java.lang.Object;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

/**
 *
 * @author  Miro
 */
public class ProductAssemblerPanel
    extends AcaciaPanel
{
    private static final Logger logger = Logger.getLogger(ProductAssemblerPanel.class);

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
    private AcaciaTreeTable productTreeTable;
    //private AcaciaTable productTreeTable;
    private JBFormattedTextField productPriceTextField;

    private AssemblingSchema assemblingSchema;

    private List<ComplexProductItem> complexProductItems;

    @Override
    protected void initData()
    {
        okButton.setEnabled(false);
        ResourceMap resource = getResourceMap();

        String strValue;
        parametersTable = new AcaciaTable();
        BindingGroup bindingGroup = new BindingGroup();
        EntityProperties entityProperties =
            getFormSession().getAssemblingParameterEntityProperties();
        List<AssemblingParameter> assemblingParameters = new ArrayList<AssemblingParameter>();
        parametersTable.bind(bindingGroup, assemblingParameters, entityProperties);
        bindingGroup.bind();
        JBScrollPane scrollPane = new JBScrollPane();
        scrollPane.setViewportView(parametersTable);
        schemaTitledPanel.setContentContainer(scrollPane);

        strValue = resource.getString("productTitledPanel.title");
        productTitledPanel.setTitle(strValue);
        productTreeTable = new AcaciaTreeTable();
        /*productTreeTable = new AcaciaTable();
        bindingGroup = new BindingGroup();
        entityProperties = getFormSession().getComplexProductItemEntityProperties();
        productTreeTable.bind(bindingGroup, getComplexProductItems(), entityProperties);
        bindingGroup.bind();*/
        scrollPane = new JBScrollPane();
        scrollPane.setViewportView(productTreeTable);

        JBPanel complexProductPanel = new JBPanel();
        complexProductPanel.setLayout(new BorderLayout());
        complexProductPanel.add(BorderLayout.CENTER, scrollPane);
        JBPanel productPricePanel = new JBPanel();
        productPricePanel.setLayout(new BorderLayout());
        JBLabel productPriceLabel = new JBLabel();
        strValue = resource.getString("productPriceLabel.text");
        productPriceLabel.setText(strValue);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        productPriceTextField = new JBFormattedTextField(decimalFormat);
        productPriceTextField.setEditable(false);
        productPricePanel.add(BorderLayout.WEST, productPriceLabel);
        productPricePanel.add(BorderLayout.CENTER, productPriceTextField);
        complexProductPanel.add(BorderLayout.PAGE_END, productPricePanel);
        productTitledPanel.setContentContainer(complexProductPanel);

        parametersTable.setEditable(true);
    }

    private void initParameters()
    {
        ResourceMap resource = getResourceMap();
        AssemblingSchema schema = getAssemblingSchema();

        Map<AssemblingMessage, AssemblingParameter> parametersMap =
            new TreeMap<AssemblingMessage, AssemblingParameter>();
        initParameters(schema, parametersMap);
        /*for(AssemblingSchemaItem item : getSchemaItems())
        {
            initParameters(item, parametersMap);
            AssemblingMessage message = item.getAssemblingMessage();
            if(message == null)
                continue;

            Object value = item.getDefaultValue();
            AssemblingParameter parameter = new AssemblingParameter();
            parameter.setAssemblingMessage(message);
            DbResource dbResource = item.getDataType();
            AssemblingSchemaItem.DataType dataType =
                (AssemblingSchemaItem.DataType)dbResource.getEnumValue();
            parameter.setDataType(dataType);
            parameter.setValue(value);
            //parameters.add(parameter);
        }*/

        List<AssemblingParameter> parameters = parametersTable.getData();
        parameters.addAll(parametersMap.values());

        String schemaName = schema.getSchemaCode() + " - " + schema.getSchemaName();
        String strValue = resource.getString("schemaTitledPanel.title");
        schemaTitledPanel.setTitle(strValue + schemaName);

        parametersTable.packAll();
    }

    private void initParameters(
        AssemblingSchema schema,
        Map<AssemblingMessage, AssemblingParameter> parametersMap)
    {
        for(AssemblingSchemaItem schemaItem : getSchemaItems(schema))
        {
            initParameters(schemaItem, parametersMap);

            AssemblingMessage message = schemaItem.getAssemblingMessage();
            if(message == null)
                continue;

            Object value = schemaItem.getDefaultValue();
            AssemblingParameter parameter = new AssemblingParameter();
            parameter.setAssemblingMessage(message);
            DbResource dbResource = schemaItem.getDataType();
            AssemblingSchemaItem.DataType dataType =
                (AssemblingSchemaItem.DataType)dbResource.getEnumValue();
            parameter.setDataType(dataType);
            parameter.setValue(value);
            parametersMap.put(message, parameter);
        }
    }

    private void initParameters(
        AssemblingSchemaItem schemaItem,
        Map<AssemblingMessage, AssemblingParameter> parametersMap)
    {
        for(AssemblingSchemaItemValue itemValue : getItemValues(schemaItem))
        {
            VirtualProduct virtualProduct = itemValue.getVirtualProduct();
            if(virtualProduct instanceof AssemblingSchema)
            {
                initParameters((AssemblingSchema)virtualProduct, parametersMap);
            }
        }
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
        ComplexProduct complexProduct = (ComplexProduct)getSelectedValue();
        try
        {
            getFormSession().saveComplexProduct(complexProduct);
            close();
        }
        catch(Exception ex)
        {
            handleException("okAction(). Save ComplexProduct", ex);
        }
    }

    @Action
    public void assembleAction()
    {
        //productTreeTable.getData().clear();
        productAssemble();
    }

    private Map<String, Object> getParameters()
    {
        HashMap params = new HashMap();
        List<AssemblingParameter> parameters = parametersTable.getData();
        if((parameters = parametersTable.getData()) != null &&
            parameters.size() > 0)
        {
            for(AssemblingParameter parameter : parameters)
            {
                AssemblingMessage message = parameter.getAssemblingMessage();
                String key = message.getMessageCode();
                AssemblingSchemaItem.DataType dataType = parameter.getDataType();
                logger.info("AssemblingSchemaItem.DataType: " + dataType);
                Serializable value = (Serializable)parameter.getValue();
                if(value != null)
                    value = dataType.toDataType(value);
                logger.info("key: " + key + ", value: " + value +
                    (value != null ? value.getClass().getName() : ""));
                params.put(key, value);
            }
        }
        logger.info("getParameters(): " + params);

        return params;
    }

    private void productAssemble()
    {
        ProductAssembler assembler =
            new ProductAssembler(getAssemblingSchema(),
            getFormSession(),
            new AssemblerCallbackHandler());
        ComplexProductAssemblerListener listener = new ComplexProductAssemblerListener();
        assembler.addProductAssemblerListener(listener);

        try
        {
            ComplexProduct product = assembler.assemble(getParameters());
            logger.info("Product: " + product.toString(true));
            setSelectedValue(product);
            okButton.setEnabled(true);
            assembleButton.setEnabled(false);
        }
        catch(Exception ex)
        {
            handleException("productAssemble().assembler.assemble(...)", ex);
        }
        finally
        {
            assembler.removeProductAssemblerListener(listener);
        }
        requestFocus();
    }

    public AssemblingSchema getAssemblingSchema()
    {
        return assemblingSchema;
    }

    public void setAssemblingSchema(AssemblingSchema assemblingSchema)
    {
        this.assemblingSchema = assemblingSchema;
    }

    public List<AssemblingSchemaItem> getSchemaItems(AssemblingSchema schema)
    {
        return getFormSession().getAssemblingSchemaItems(schema);
    }

    public List<AssemblingSchemaItemValue> getItemValues(AssemblingSchemaItem schemaItem)
    {
        return getFormSession().getAssemblingSchemaItemValues(schemaItem);
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
            logger.info("event.getComplexProductItem(): " + productItem);
            logger.info("\t AppliedAlgorithm: " + productItem.getAppliedAlgorithm());
            logger.info("\t AppliedValue: " + productItem.getAppliedValue());
            logger.info("\t ComplexProduct: " + productItem.getComplexProduct());
            logger.info("\t ComplexProductItemId: " + productItem.getComplexProductItemId());
            logger.info("\t Info: " + productItem.getInfo());
            logger.info("\t OrderPosition: " + productItem.getOrderPosition());
            logger.info("\t Product: " + productItem.getProduct());
            logger.info("\t Quantity: " + productItem.getQuantity());
            logger.info("\t UnitPrice: " + productItem.getUnitPrice());

            ComplexProduct product = event.getComplexProduct();
            productPriceTextField.setValue(product.getSalePrice());

            //productTreeTable.addRow(productItem);
            DefaultTreeTableModel treeTableModel = 
                (DefaultTreeTableModel)productTreeTable.getTreeTableModel();
            DefaultMutableTreeTableNode rootNode;
            if((rootNode = (DefaultMutableTreeTableNode)treeTableModel.getRoot()) == null)
            {
                rootNode = new ProductItemTreeTableNode(product, getFormSession());
                treeTableModel.setRoot(rootNode);
            }
            else
            {
                //System.out.println("rootNode: " + rootNode + ", ComplexProduct: " + product);
                //((ProductItemTreeTableNode)rootNode).refresh();
                rootNode = new ProductItemTreeTableNode(product, getFormSession());
                treeTableModel.setRoot(rootNode);
            }
            
            productTreeTable.packAll();
        }

    }


}
