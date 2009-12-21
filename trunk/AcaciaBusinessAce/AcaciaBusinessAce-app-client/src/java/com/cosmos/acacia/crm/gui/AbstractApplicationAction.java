/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.data.ui.SecureAction;
import com.cosmos.acacia.data.ui.SecureAction.Show;
import com.cosmos.swingb.JBPanel;
import java.awt.event.ActionEvent;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public abstract class AbstractApplicationAction<A extends SecureAction> extends ApplicationAction {

    private A secureAction;

    public AbstractApplicationAction(
            ApplicationActionMap actionMap,
            ResourceMap resourceMap,
            A secureAction) throws Exception {
        super(actionMap, resourceMap,
                secureAction.getName(),
                null,
                secureAction.getEnabledProperty(),
                secureAction.getSelectedProperty(),
                secureAction.getBlock());
        this.secureAction = secureAction;
    }

    protected abstract Class<? extends JBPanel> getFormClass();

    public Show getShow() {
        return secureAction.getShow();
    }

    public A getSecureAction() {
        return secureAction;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Class<? extends JBPanel> formClass = getFormClass();
        JBPanel formPanel;
        try {
            formPanel = formClass.newInstance();
        } catch(Exception ex) {
            System.out.println("getClass().getName(): " + getClass().getName());
            System.out.println(toString());
            getClass().getName();
            throw new RuntimeException(ex);
        }

        if(SecureAction.Show.Dialog.equals(getShow())) {
            formPanel.showDialog();
        } else {
            formPanel.showFrame();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(secureAction);

        return sb.toString();
    }
}
