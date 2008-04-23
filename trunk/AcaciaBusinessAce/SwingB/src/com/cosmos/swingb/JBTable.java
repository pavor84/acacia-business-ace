/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.converters.ResourceConverter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 *
 * @author Miro
 */
public class JBTable
    extends JXTable
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;

    private ObservableList observableData;
    private EntityProperties entityProperties;

    private BeanTableCellRenderer beanResourceCellRenderer;

   
    public JBTable()
    {
        this(Application.getInstance());
    }

    public JBTable(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBTable(Application application)
    {
        internalInitialization();
        this.application = application;
    }

    protected void internalInitialization()
    {
        setSelectionModel(new TableSelectionModel());
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setColumnControlVisible(true);
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setHorizontalScrollEnabled(true);
        setEditable(false);
    }

    public void addListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener)
    {
        ListSelectionModel selectionModel;
        if((selectionModel = getSelectionModel()) != null)
            selectionModel.removeListSelectionListener(listener);
    }

    public List getData() {
        return observableData;
    }

    public void setData(List data) {
        List oldData = getData();
        if(oldData != null && data != null)
        {
            observableData.clear();
            observableData.addAll(data);
        }
        else if(!(data instanceof ObservableList))
        {
            observableData = ObservableCollections.observableList(data);
        }
        else
        {
            observableData = (ObservableList)data;
        }
    }

    public int addRow(Object bean)
    {
        if(observableData == null)
            return -1;
        observableData.add(bean);
        return observableData.size() - 1;
    }

    public void setRow(int rowIndex, Object bean)
    {
        if(observableData != null)
            observableData.set(convertRowIndexToModel(rowIndex), bean);
    }

    public void replaceSelectedRow(Object bean)
    {
        int rowIndex = getSelectedRow();
        setRow(rowIndex, bean);
    }

    public int getRowIndex(Object bean)
    {
        if(observableData != null)
        {
            int rowIndex = observableData.indexOf(bean);
            if(rowIndex >= 0)
            {
                return convertRowIndexToView(rowIndex);
            }
        }

        return -1;
    }

    public void setSelectedRowObject(Object bean)
    {
        int rowIndex = getRowIndex(bean);
        if(rowIndex >= 0)
            getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
    }

    public int getSelectedModelRowIndex()
    {
        int viewRowIndex;
        if((viewRowIndex = getSelectedRow()) != -1)
        {
            return convertRowIndexToModel(viewRowIndex);
        }

        return -1;
    }

    public int[] getSelectedModelRowIndexes()
    {
        int[] selectedRowIndexes = getSelectedRows();
        int size;
        if(selectedRowIndexes != null && (size = selectedRowIndexes.length) > 0)
        {
            for(int i = 0; i < size; i++)
            {
                selectedRowIndexes[i] = convertRowIndexToModel(selectedRowIndexes[i]);
            }
        }

        return selectedRowIndexes;
    }

    public Object getSelectedRowObject()
    {
        int rowIndex = getSelectedModelRowIndex();
        if(rowIndex >= 0 && observableData != null && observableData.size() > rowIndex)
        {
            return observableData.get(rowIndex);
        }

        return null;
    }

    public List getSelectedRowObjects()
    {
        int[] rowIndexes = getSelectedModelRowIndexes();
        int size;
        if(rowIndexes != null && (size = rowIndexes.length) > 0 && observableData != null)
        {
            ArrayList rows = new ArrayList(size);
            for(int rowIndex : rowIndexes)
            {
                rows.add(observableData.get(rowIndex));
            }
            return rows;
        }

        return Collections.EMPTY_LIST;
    }

    public Object removeSelectedRow()
    {
        int rowIndex = getSelectedModelRowIndex();
        if(rowIndex >= 0 && observableData != null && observableData.size() > rowIndex)
        {
            return observableData.remove(rowIndex);
        }

        return null;
    }

    public JTableBinding bind(
            BindingGroup bindingGroup,
            List data,
            EntityProperties entityProperties) {
        AutoBinding.UpdateStrategy updateStrategy = entityProperties.getUpdateStrategy();
        if(updateStrategy == null)
            updateStrategy = AutoBinding.UpdateStrategy.READ;
        return bind(bindingGroup, data, entityProperties, updateStrategy);
    }

    /**
     * You may need to bind to JTable without having to use EntityProperties.
     * Use this method in this case.
     * @param bindingGroup
     * @param data
     * @param propertyDetails
     * @param updateStrategy
     * @return
     */
    public JTableBinding bind(
            BindingGroup bindingGroup,
            List data,
            Collection<PropertyDetails> propertyDetails,
            AutoBinding.UpdateStrategy updateStrategy) {
        updateStrategy = UpdateStrategy.READ;
        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;

        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy, observableData, this);
        createColumnsBinding(tableBinding, propertyDetails);
        tableBinding.bind();

        bindingGroup.addBinding(tableBinding);

        return tableBinding;
    }

    public JTableBinding bind(BindingGroup bindingGroup, List data,
                              EntityProperties entityProperties,
                              AutoBinding.UpdateStrategy updateStrategy) {
        updateStrategy = UpdateStrategy.READ;
        if (!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList) data;
        this.entityProperties = entityProperties;

        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy,
            observableData, this);
        createColumnsBinding(tableBinding, entityProperties);
        tableBinding.bind();

        bindingGroup.addBinding(tableBinding);

        return tableBinding;
    }


    protected void createColumnsBinding(
            JTableBinding tableBinding,
            EntityProperties entityProperties)
    {
        createColumnsBinding(tableBinding, entityProperties.getValues());
    }

    protected void createColumnsBinding(
            JTableBinding tableBinding,
            Collection<PropertyDetails> properties)
    {
        for(PropertyDetails property : properties)
        {
            if(!property.isHiden())
                createColumnBinding(tableBinding, property);
        }
    }

    //TODO
    public ColumnBinding createColumnBinding(
        JTableBinding tableBinding,
        PropertyDetails propertyDetails)
    {
        //Use custom display if available
        //Note that if custom display is used, the column class should be String.class or
        //null. For this reason, we only set the class of the column if the property name
        //is used. As a consequence the column with custom display is can not be editable.
        String expression = propertyDetails.getCustomDisplay();
        Class columnClass = null;
        if ( expression==null ){
            expression = "${" + propertyDetails.getPropertyName() + "}";
            columnClass = propertyDetails.getPropertyClass();
            //set the class only if propert name is used for the display expression
        }
        
        // TODO: Disallow custom display property for resources (to avoid class cast exceptions)
        
        ELProperty elProperty = ELProperty.create(expression);
        ColumnBinding columnBinding = tableBinding.addColumnBinding(elProperty);
        columnBinding.setColumnName(propertyDetails.getPropertyTitle());
        
        if (isResource(propertyDetails))
        {
            columnClass = propertyDetails.getPropertyClass();
            columnBinding.setConverter(
                    new ResourceConverter(getApplication().getClass(),
                        propertyDetails.getResourceDisplayInTable()));
        }
        
        columnBinding.setColumnClass(columnClass);

        Boolean b = propertyDetails.isEditable();
        if(b != null)
            columnBinding.setEditable(b.booleanValue());

        b = propertyDetails.isVisible();
        if(b != null)
            columnBinding.setVisible(b.booleanValue());

        Object obj = propertyDetails.getSourceUnreadableValue();
        if(obj != null)
            columnBinding.setSourceUnreadableValue(obj);

        //columnBinding.setConverter()
        //columnBinding.setValidator(arg0)

        return columnBinding;
    }

    public EntityProperties getEntityProperties() {
        return entityProperties;
    }

    public void bindComboBoxCellEditor(
            BindingGroup bindingGroup,
            List comboBoxValues,
            PropertyDetails propertyDetails)
    {
        Application app = getApplication();
        JBComboBox comboBox;
        if(app != null)
            comboBox = new JBComboBox(app);
        else
            comboBox = new JBComboBox();

        comboBox.bind(bindingGroup, comboBoxValues, this, propertyDetails);
        ComboBoxCellEditor comboBoxCellEditor = new ComboBoxCellEditor(comboBox);
        TableColumnExt column;
        String propertyName = propertyDetails.getPropertyName();
        try
        {
            column = getColumnExt(propertyName);
        }
        catch(Exception ex)
        {
            column = null;
        }

        if(column == null)
        {
            EntityProperties entityProps = getEntityProperties();
            if(entityProps == null)
                throw new IllegalArgumentException("EntityProperties is not initialized. Set EntityProperties first.");
            PropertyDetails pd = entityProps.getPropertyDetails(propertyName);
            if(pd != null)
            {
                String columnName = pd.getPropertyTitle();
                column = getColumnExt(columnName);
            }
        }

        if(column == null)
            throw new IllegalArgumentException("Can not find table column for property name: " + propertyName);

        column.setCellEditor(comboBoxCellEditor);
        if(app != null && column.getCellRenderer() == null)
            column.setCellRenderer(getBeanResourceCellRenderer());
    }

  /*      AcaciaComboBox categoryComboBox = new AcaciaComboBox();
        categoryComboBox.bind(productsBindingGroup, getProductsCategories(), productsTable, "category");
        ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(categoryComboBox);
        TableColumnExt categoryColumn = productsTable.getColumnExt("Category");
        categoryColumn.setCellEditor(cellEditor);
  */


    /**
     * Changes the default cell editor to a DatePicker and binds it
     *
     * @param bindingGroup
     * @param comboBoxValues
     * @param propertyName
     */
    public void bindDatePickerCellEditor(
            BindingGroup bindingGroup,
            PropertyDetails propertyDetails,
            DateFormat dateFormat)
    {
        Application app = getApplication();
        JBDatePicker datePicker;
        //if(app != null)
        //    datePicker = new JBDatePicker(app);
        //else
            datePicker = new JBDatePicker();

        datePicker.setFormats(dateFormat);
        datePicker.bind(bindingGroup, this, propertyDetails);
        JBDatePickerCellEditor datePickerCellEditor = new JBDatePickerCellEditor(datePicker);

        // TODO: set Formatting of not-edited date


        TableColumnExt column;
        try
        {
            column = getColumnExt(propertyDetails.getPropertyName());
        }
        catch(Exception ex)
        {
            column = null;
        }

        if(column == null)
        {
            if(propertyDetails != null)
            {
                String columnName = propertyDetails.getPropertyTitle();
                column = getColumnExt(columnName);
            }
        }

        if(column == null)
            throw new IllegalArgumentException("Can not find table column for property name: " + propertyDetails.getPropertyName());

        column.setCellEditor(datePickerCellEditor);

        if(app != null && column.getCellRenderer() == null)
            column.setCellRenderer(getBeanResourceCellRenderer());
    }

    public ApplicationContext getContext()
    {
        if(applicationContext == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap()
    {
        if(applicationActionMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(this.getClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {

        if(columnClass != null && "com.cosmos.acacia.crm.data.DbResource".equals(columnClass.getName()))
        {
            return getBeanResourceCellRenderer();
        }

        return super.getDefaultRenderer(columnClass);
    }

    protected TableCellRenderer getBeanResourceCellRenderer()
    {
        if(beanResourceCellRenderer == null)
        {
            beanResourceCellRenderer = new BeanTableCellRenderer(getApplication().getClass());
        }

        return beanResourceCellRenderer;
    }

    private boolean isResource(PropertyDetails propertyDetails)
    {
        return propertyDetails.getPropertyClass().getName()
                .equals("com.cosmos.acacia.crm.data.DbResource");
    }
}
