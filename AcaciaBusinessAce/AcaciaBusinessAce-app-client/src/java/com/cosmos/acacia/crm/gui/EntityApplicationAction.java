/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.data.ui.EntityAction;
import com.cosmos.swingb.JBPanel;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public class EntityApplicationAction extends AbstractApplicationAction<EntityAction> {

    public EntityApplicationAction(ApplicationActionMap actionMap, ResourceMap resourceMap, EntityAction entityAction) throws Exception {
        super(actionMap, resourceMap, entityAction);
    }

    @Override
    protected Class<? extends JBPanel> getFormClass() {
        return getEntityListFormClass(getSecureAction().getEntityClass());
    }

    protected Class<? extends JBPanel> getEntityListFormClass(Class entityClass) {
        Form form;
        if((form = getForm(entityClass)) == null) {
            return null;
        }

        String className;
        if((className = form.entityListFormClassName()) == null || className.length() == 0) {
            return null;
        }

        try {
            return (Class<? extends JBPanel>) Class.forName(className);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Form getForm(Class entityClass) {
        return (Form) entityClass.getAnnotation(Form.class);
    }
}
