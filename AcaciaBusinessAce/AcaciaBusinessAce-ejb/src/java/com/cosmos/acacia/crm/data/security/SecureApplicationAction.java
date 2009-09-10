/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.security;

import java.io.Serializable;
import java.lang.reflect.Method;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;
import org.jdesktop.application.Task.BlockingScope;

/**
 *
 * @author Miro
 */
public class SecureApplicationAction extends ApplicationAction implements Serializable {

    private Privilege privilege;

    public SecureApplicationAction(
            ApplicationActionMap actionMap,
            ResourceMap resourceMap,
            String actionName,
            Privilege privilege) {
        this(actionMap, resourceMap, actionName, null, null, null, Task.BlockingScope.NONE, privilege);
    }

    public SecureApplicationAction(
            ApplicationActionMap actionMap,
            ResourceMap resourceMap,
            String actionName,
            Method actionMethod,
            String enabledProperty,
            String selectedProperty,
            BlockingScope block,
            Privilege privilege) {
        super(actionMap, resourceMap, actionName, actionMethod, enabledProperty, selectedProperty, block);
        this.privilege = privilege;
    }

    public Privilege getPrivilege() {
        return privilege;
    }
}
