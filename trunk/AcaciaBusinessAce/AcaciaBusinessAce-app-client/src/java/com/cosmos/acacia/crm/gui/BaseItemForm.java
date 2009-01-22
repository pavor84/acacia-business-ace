/**
 * 
 */
package com.cosmos.acacia.crm.gui;

import java.lang.reflect.ParameterizedType;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public abstract class BaseItemForm<T extends DataObjectBean, I extends DataObjectBean> extends BaseEntityPanel {

    protected I entity;
    protected T parent;
    
    protected BindingGroup bindGroup;
    protected BaseRemote<T, I> formSession;
    protected EntityProperties entProps;
    
    protected Class<T> entityClass;
    protected Class<T> itemClass;

    /** Creates new form */
    public BaseItemForm(I entity, T parent) {
        super(entity.getParentId());
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.itemClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.entity = entity;
        this.parent=parent;
        this.formSession = getFormSession();
    }

    protected void initialize() {
        createComponents();
        initComponentsCustom();
        init();
    }

    protected abstract void createComponents();

    protected void initComponentsCustom() {
        
    }

    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void performSave(boolean closeAfter) {
        entity = formSession.saveItem(entity);
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
            entProps = getFormSession().getItemDetailEntityProperties();

        if (bindGroup == null)
            bindGroup = new BindingGroup();

        bindComponents(bindGroup, entProps);
    }
    
    protected abstract void bindComponents(BindingGroup bindGroup, EntityProperties entProps);

    @Override
    public void setReadonly() {
        super.setReadonly();
    }

    protected BaseRemote<T, I> getFormSession(){
        if ( formSession==null ){
            formSession = getBean(getFormSessionClass());
        }
        return formSession;
    }
    
    protected abstract Class<? extends BaseRemote<T, I>> getFormSessionClass();
    
    protected T getParentEntity(){
        return parent;
    }
    
    protected PropertyDetails getDetails(String propertyName) {
        return entProps.getPropertyDetails(propertyName);
    }
}
