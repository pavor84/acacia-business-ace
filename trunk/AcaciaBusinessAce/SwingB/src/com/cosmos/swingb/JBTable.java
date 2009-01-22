/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

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
import org.jdesktop.beansbinding.Property;
import org.jdesktop.beansbinding.PropertyHelper;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingbinding.JTableBinding.ColumnBinding;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import org.jdesktop.swingx.table.TableColumnExt;

import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.beansbinding.converters.ResourceConverter;
import java.awt.Color;

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
        internalInitialization();
    }

    public JBTable(Application application)
    {
        this();
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
        setShowHorizontalLines(false);
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
        return addRow(bean,null);
    }
    
    public int addRow(Object bean, Integer index)
    {
        if(observableData == null)
            return -1;
        if ( index!=null ){
            observableData.add(index, bean);
            return index;
        }
        else{
            observableData.add(bean);
            return observableData.size() - 1;
        }
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
    
    /**
     * If the bean is in the table - its updated, otherwise added
     * @param bean
     */
    public void addOrUpdateRow(Object bean){
        addOrUpdateRow(bean, null);
    }
    
    /**
     * If the bean is in the table - its updated, otherwise added.
     * @param bean
     * @param addIdx - if added, specify the index
     */
    public void addOrUpdateRow(Object bean, Integer addIdx) {
        int rowIndex = getRowIndex(bean);
        if ( rowIndex==-1 )
            addRow(bean, addIdx);
        else
            setRow(rowIndex, bean);
    }
    
    /**
     * Updates the visualization of a given row associated with a given bean.
     * The bean should be already present in the table, otherwise the
     * method throws {@link IllegalArgumentException}
     * @param bean
     */
    public void updateRow(Object bean)
    {
        int rowIndex = getRowIndex(bean);
        if ( rowIndex==-1 )
            throw new IllegalArgumentException("Object: "+bean+" not found in table!");
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
    
    /**
     * Removes the row for the given object.
     * If the rowObject is not found, {@link IllegalArgumentException} is thrown.
     * @param rowObject
     */
    public void removeRow(Object rowObject){
        int rowIndex = getRowIndex(rowObject);
        if ( rowIndex==-1 )
            throw new IllegalArgumentException("Row for object not found. Object: "+rowObject);
        
        if(rowIndex >= 0 && observableData != null && observableData.size() > rowIndex)
        {
            observableData.remove(rowIndex);
        }
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
        return bind(bindingGroup, data, entityProperties, false);
    }

    public JTableBinding bind(
            BindingGroup bindingGroup,
            List data,
            EntityProperties entityProperties, boolean showIndexColumn) {
        AutoBinding.UpdateStrategy updateStrategy = entityProperties.getUpdateStrategy();
        if(updateStrategy == null)
            updateStrategy = AutoBinding.UpdateStrategy.READ;
        return bind(bindingGroup, data, entityProperties, updateStrategy, showIndexColumn);
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
        return bind(bindingGroup, data, propertyDetails, updateStrategy, false);
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
            AutoBinding.UpdateStrategy updateStrategy, boolean showIndexColumn) {
        updateStrategy = UpdateStrategy.READ;
        if(!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList)data;

        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy, observableData, this);
        createColumnsBinding(tableBinding, propertyDetails, showIndexColumn);
        tableBinding.bind();
        
        //pack the index column
        if ( showIndexColumn )
            packColumn(0, 6);

        bindingGroup.addBinding(tableBinding);

        return tableBinding;
    }
    
    public JTableBinding bind(BindingGroup bindingGroup, List data,
                              EntityProperties entityProperties,
                              AutoBinding.UpdateStrategy updateStrategy) {
        return bind(bindingGroup, data, entityProperties, updateStrategy, false);
    }

    public JTableBinding bind(BindingGroup bindingGroup, List data,
                              EntityProperties entityProperties,
                              AutoBinding.UpdateStrategy updateStrategy, boolean showIndexColumn) {
        updateStrategy = UpdateStrategy.READ;
        if (!(data instanceof ObservableList))
            observableData = ObservableCollections.observableList(data);
        else
            observableData = (ObservableList) data;
        this.entityProperties = entityProperties;

        JTableBinding tableBinding = SwingBindings.createJTableBinding(updateStrategy,
            observableData, this);
        createColumnsBinding(tableBinding, entityProperties, showIndexColumn);
        
        tableBinding.bind();
        
        //pack the index column
        if ( showIndexColumn )
            packColumn(0, 6);
        
        bindingGroup.addBinding(tableBinding);

        return tableBinding;
    }


    protected void createColumnsBinding(
            JTableBinding tableBinding,
            EntityProperties entityProperties, boolean showIndexColumn)
    {
        createColumnsBinding(tableBinding, entityProperties.getValues(), showIndexColumn);
    }

    protected void createColumnsBinding(
            JTableBinding tableBinding,
            Collection<PropertyDetails> properties, boolean showIndexColumn)
    {
        if ( showIndexColumn )
            createIndexColumnBinding(tableBinding);
        for(PropertyDetails property : properties)
        {
            if(!property.isHiden())
                createColumnBinding(tableBinding, property);
        }
    }

    /**
     * Creates binding for row number column at the table.
     * Adds the new column binding to the table binding and returns it.
     * @param tableBinding
     * @return
     */
    @SuppressWarnings("unchecked")
    protected ColumnBinding createIndexColumnBinding(JTableBinding tableBinding) {
        Property indexProperty = new PropertyHelper() {
            @Override
            public void setValue(Object arg0, Object arg1) {
            }
        
            @Override
            public boolean isWriteable(Object arg0) {
                return false;
            }
        
            @Override
            public boolean isReadable(Object arg0) {
                return true;
            }
        
            @Override
            public Class getWriteType(Object arg0) {
                return String.class;
            }
        
            @Override
            public Object getValue(Object item) {
                try{
                    return ""+(observableData.indexOf(item)+1);
                }catch ( Exception e ){
                    return "#";    
                }
            }
        };
        
        ColumnBinding indexBinding = tableBinding.addColumnBinding(0, indexProperty);
        indexBinding.setColumnName("#");
        indexBinding.setColumnClass(String.class);
        indexBinding.setEditable(false);
        indexBinding.setVisible(true);
        
        return indexBinding;
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
        if(expression == null)
        {
            expression = "${" + propertyDetails.getPropertyName() + "}";
            // set the class only if propert name is used for the display expression
            columnClass = propertyDetails.getPropertyClass();
        }
        
        // TODO: Disallow custom display property for resources (to avoid class cast exceptions)
        
        ELProperty elProperty = ELProperty.create(expression);
        ColumnBinding columnBinding = tableBinding.addColumnBinding(elProperty);
        columnBinding.setColumnName(propertyDetails.getPropertyTitle());
        
        if (isResource(propertyDetails))
        {
            columnClass = propertyDetails.getPropertyClass();
            columnBinding.setConverter(
                    new ResourceConverter(getApplication(),
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


    public void bindComboListCellEditor(
            BindingGroup bindingGroup,
            SelectableListDialog selectableListDialog,
            PropertyDetails propertyDetails,
            ObjectToStringConverter converter)
    {
        Application app = getApplication();
        JBComboList comboList;
        if(app != null)
            comboList = new JBComboList(app);
        else
            comboList = new JBComboList();

        comboList.bind(
            bindingGroup,
            selectableListDialog,
            this,
            propertyDetails,
            converter);

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

        ComboListCellEditor cellEditor = new ComboListCellEditor(comboList);
        column.setCellEditor(cellEditor);

        if(app != null && column.getCellRenderer() == null)
            column.setCellRenderer(getBeanResourceCellRenderer());
    }


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
        if(application == null)
            application = Application.getInstance();

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
            beanResourceCellRenderer = new BeanTableCellRenderer(getApplication());
        }

        return beanResourceCellRenderer;
    }

    private boolean isResource(PropertyDetails propertyDetails)
    {
        if (propertyDetails == null || propertyDetails.getPropertyClass() == null
                || propertyDetails.getPropertyClass().getName() == null)
        {
            return false;
        }
        
        return propertyDetails.getPropertyClass().getName()
                .equals("com.cosmos.acacia.crm.data.DbResource");
    }

}
