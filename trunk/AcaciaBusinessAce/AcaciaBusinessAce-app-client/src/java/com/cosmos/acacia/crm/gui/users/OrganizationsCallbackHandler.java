package com.cosmos.acacia.crm.gui.users;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.cosmos.acacia.crm.bl.users.OrganizationCallback;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.gui.AcaciaApplicationView;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.ejb.callback.ClientCallbackHandler;
import com.cosmos.swingb.DialogResponse;
import java.io.Serializable;

public class OrganizationsCallbackHandler
    implements ClientCallbackHandler, Serializable {

    private static transient AcaciaPanel parentPanel;
    
    public OrganizationsCallbackHandler(AcaciaPanel parentPanel) {
        OrganizationsCallbackHandler.parentPanel = parentPanel;
    }
    
    public OrganizationsCallbackHandler() {
        
    }
    
    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i ++) {
            OrganizationCallback callback = (OrganizationCallback) callbacks[i];

            OrganizationChoiceForm ocf = new OrganizationChoiceForm(callback.getOrganizations());
            //AcaciaApplicationView.setLookAndFeel();
            
            DialogResponse response = ocf.showDialog();
            if(DialogResponse.SELECT.equals(response))
            {
                callback.setOrganization((Organization) ocf.getSelectedValue());
            }
            
            callbacks[i] = callback;
        }
    }
}
