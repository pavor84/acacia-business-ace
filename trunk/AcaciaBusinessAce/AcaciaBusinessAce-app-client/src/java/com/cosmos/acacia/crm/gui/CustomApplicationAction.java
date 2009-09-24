/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.data.ui.CustomAction;
import com.cosmos.swingb.JBPanel;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public class CustomApplicationAction extends AbstractApplicationAction<CustomAction> {

    public CustomApplicationAction(ApplicationActionMap actionMap, ResourceMap resourceMap, CustomAction customAction) throws Exception {
        super(actionMap, resourceMap, customAction);
    }

    @Override
    protected Class<? extends JBPanel> getFormClass() {
        return getSecureAction().getFormClass();
    }
}
