package com.cosmos.acacia.crm.gui.users;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.cosmos.acacia.crm.bl.users.OrganizationCallback;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.swingb.DialogResponse;
import java.io.Serializable;
import javax.security.auth.callback.CallbackHandler;

public class OrganizationsCallbackHandler
    implements CallbackHandler, Serializable {

    private String defaultOrganization;
    
    public OrganizationsCallbackHandler(String defaultOrganization) {
        this.defaultOrganization = defaultOrganization;
    }
    
    public OrganizationsCallbackHandler() {
        
    }
    
    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i ++) {
            OrganizationCallback callback = (OrganizationCallback) callbacks[i];

            OrganizationChoiceForm form = new OrganizationChoiceForm(null);
            form.setDefaultOrganizationString(defaultOrganization);
            form.init(callback.getOrganizations());
            
            //AcaciaApplicationView.setLookAndFeel();
            
            DialogResponse response = form.showDialog();
            if(DialogResponse.SELECT.equals(response))
            {
                callback.setOrganization((Organization) form.getSelectedValue());
            }
            
            callbacks[i] = callback;
        }
    }
}
