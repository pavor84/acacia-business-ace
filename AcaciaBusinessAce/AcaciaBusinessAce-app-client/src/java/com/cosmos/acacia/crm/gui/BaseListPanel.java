package com.cosmos.acacia.crm.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
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
 * 
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public abstract class BaseListPanel<T extends DataObjectBean, I extends DataObjectBean> extends AbstractTablePanel {
    protected EntityProperties entityProps;
    
    protected BaseRemote<T, I> formSession;
    protected BindingGroup bindingGroup;

    protected List<T> list;
    
    protected Class<T> entityClass;
    
    public BaseListPanel(BigInteger parentDataObjectId) {
        this ( parentDataObjectId, null );
    }
    
    public BaseListPanel(BigInteger parentDataObjectId, List<T> list) {
        super(parentDataObjectId);
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.list = list;
        bindComponents();
    }
    
    protected void bindComponents(){
        entityProps = getFormSession().getListingEntityProperties();
        
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
            list = getFormSession().list(getParentDataObjectId());
        }
        return list;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().delete((T)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        T o = (T) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        if ( canCreate() ){
            T o = getFormSession().newEntity(getParentDataObjectId());
            return showDetailForm(o, true);
        }else{
            return null;
        }
    }

    protected Object showDetailForm(T o, boolean editable) {
        BaseForm<T,I> editPanel = createFormPanel(o);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
        }

        return null;
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
        T o = (T) rowObject;
        showDetailForm(o, false);
    }
    
    protected BaseForm<T, I> createFormPanel(T o) {
        String detailFormClassName = entityClass.getSimpleName()+"Form";
        Class detailFormClass;
        try {
            detailFormClass = Class.forName(detailFormClassName);
            BaseForm<T,I> editPanel = (BaseForm<T,I>)detailFormClass.getConstructor(entityClass).newInstance(o);
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
}
