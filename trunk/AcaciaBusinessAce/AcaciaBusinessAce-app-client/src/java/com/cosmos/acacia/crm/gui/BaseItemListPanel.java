/**
 * 
 */
package com.cosmos.acacia.crm.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public abstract class BaseItemListPanel<T extends DataObjectBean, I extends DataObjectBean> extends AbstractTablePanel {

    protected EntityProperties entityProps;
    
    protected BaseRemote<T, I> formSession;
    protected BindingGroup bindingGroup;
    
    protected Class<T> entityClass;
    protected Class<T> itemClass;
    
    protected T parent;

    protected List<I> list;
    
    public BaseItemListPanel(T parent) {
        this ( parent, null );
    }
    
    public BaseItemListPanel(T parent, List<I> list) {
        super(parent.getId());
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.itemClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.list = list;
        this.parent = parent;
        bindComponents();
        initComponentsCustom();
    }
    
    protected void initComponentsCustom() {
        
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getItemsListEntityProperties();
        
        refreshDataTable(entityProps);
        
        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    protected BaseRemote<T, I> getFormSession(){
        if ( formSession==null ){
            formSession = getBean(getFormSessionClass());
        }
        return formSession;
    }
    
    protected abstract Class<? extends BaseRemote<T, I>> getFormSessionClass();
    
    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        if ( bindingGroup!=null )
            bindingGroup.unbind();
        
        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        
        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        if ( list==null ){
            if ( getParentDataObjectId()!=null )
                list = getFormSession().listItems(getParentDataObjectId());
            else
                list = new ArrayList<I>();
        }
            
        return list;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteItem((I)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        I o = (I) rowObject;
        return showDetailForm(o, true);
    }

    protected Object showDetailForm(I o, boolean editable) {
        BaseItemForm<T,I> editPanel = createFormPanel(o);
        initFormPanel(editPanel);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }
        
        return null;
    }
    
    protected BaseItemForm<T, I> createFormPanel(I o) {
        String detailFormClassName = itemClass.getSimpleName()+"Form";
        Class detailFormClass;
        try {
            detailFormClass = Class.forName(detailFormClassName);
            BaseItemForm<T,I> editPanel = (BaseItemForm<T,I>)detailFormClass.getConstructor(itemClass).newInstance(o);
            return editPanel;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void initFormPanel(BaseItemForm<T, I> formPanel) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
         
        refreshDataTable(entityProps);
        
        return t;
    }
    
    @Override
    protected void viewRow(Object rowObject) {
        I o = (I) rowObject;
        showDetailForm(o, false);
    }
    
    @Override
    protected Object newRow() {
        I o = getFormSession().newItem(getParentDataObjectId());
        return showDetailForm(o, true);
    }
    
    public void setParent(T parent){
        this.parent = parent;
    }
    
    protected T getParentEntity(){
        return parent;
    }
}
