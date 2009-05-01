/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.entity.EntityFormProcessor;
import com.cosmos.acacia.entity.EntityService;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.awt.BorderLayout;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author Miro
 */
public class MainEntityPanel<E extends DataObjectBean> extends BaseEntityPanel {

    private E entity;
    private Class<E> entityClass;
    private EntityFormProcessor entityFormProcessor;
    private EntityService entityService;
    private EntityFormButtonPanel buttonPanel;
    private BindingGroup bindingGroup;
    private EntityProperties entityProperties;

    public MainEntityPanel(E entity) {
        super(entity);
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        init();
    }

    @Override
    public E getEntity() {
        return entity;
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected EntityFormProcessor getEntityFormProcessor() {
        if (entityFormProcessor == null) {
            entityFormProcessor = new EntityFormProcessor(getEntityClass());
        }

        return entityFormProcessor;
    }

    protected EntityProperties getEntityProperties() {
        if (entityProperties == null) {
            entityProperties = getEntityService().getEntityProperties(getEntityClass());
        }

        return entityProperties;
    }

    protected EntityService getEntityService() {
        if (entityService == null) {
            Class<? extends EntityService> entityServiceClass =
                    getEntityFormProcessor().getEntityServiceClass();
            entityService = getBean(entityServiceClass);
        }

        return entityService;
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            entity = getEntityService().save(entity);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(entity);
            if (closeAfter) {
                close();
            }
        } catch (Exception ex) {
            handleException("entity: " + entity, ex);
        }
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (bindingGroup == null) {
            bindingGroup = new BindingGroup();
        }

        return bindingGroup;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new EntityFormButtonPanel();
        }

        return buttonPanel;
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(920, 480));

        add(getEntityFormProcessor().getMainComponent(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);
    }
}
