package com.cosmos.acacia.crm.gui.users;

import java.util.List;

import com.cosmos.acacia.callback.CallbackTransportObject;
import com.cosmos.acacia.callback.Callbackable;
import com.cosmos.acacia.crm.bl.users.UsersBean;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.swingb.DialogResponse;

public class OrganizationsCallbackHandler implements Callbackable {

    private String defaultOrganization;

    public OrganizationsCallbackHandler(String defaultOrganization)  {
        this.defaultOrganization = defaultOrganization;
    }

    public OrganizationsCallbackHandler() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CallbackTransportObject callback(CallbackTransportObject req) {

        System.out.println("HANDLED");

        OrganizationChoiceForm form = new OrganizationChoiceForm(null);
        form.setDefaultOrganizationString(defaultOrganization);
        form.init((List<Organization>) req.get(UsersBean.ORGANIZATIONS_KEY));

        CallbackTransportObject result = new CallbackTransportObject();
        DialogResponse response = form.showDialog();
        if(DialogResponse.SELECT.equals(response))
        {
            result.put(UsersBean.ORGANIZATION_KEY, form.getSelectedValue());
        }

        return result;
    }
}
