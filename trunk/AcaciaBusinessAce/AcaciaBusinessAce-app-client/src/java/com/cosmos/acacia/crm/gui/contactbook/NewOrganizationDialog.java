/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.xhtml.XHTMLDialog;
import com.cosmos.swingb.xhtml.XHTMLUtils;
import java.awt.Component;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author Miro
 */
public class NewOrganizationDialog extends AcaciaPanel {

    private Classifier classifier;

    /*public NewOrganizationDialog(BigInteger parentDataObjectId) {
        super(parentDataObjectId);
    }*/

    public NewOrganizationDialog(BigInteger parentDataObjectId, Classifier classifier) {
        super(parentDataObjectId);
        this.classifier = classifier;
    }

    @Override
    public DialogResponse showDialog(Component parentComponent) {
        ResourceMap resourceMap = getResourceMap();
        String basicButtonName = resourceMap.getString("basicButton.text");
        String detailedButtonName = resourceMap.getString("detailedButton.text");
        String cancelButtonName = resourceMap.getString("cancelButton.text");
        String message1 = resourceMap.getString("newAction.Action.message1");
        String message2 = resourceMap.getString("newAction.Action.message2");
        String message3 = resourceMap.getString("newAction.Action.message3");
        String title = resourceMap.getString("newAction.Action.title");
        Object[] options = {
            basicButtonName,
            detailedButtonName,
            cancelButtonName};

        String message = XHTMLUtils.toString(
                message1,
                basicButtonName,
                message2,
                detailedButtonName,
                message3);
        int result = XHTMLDialog.showQuestionMessage(
                parentComponent,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                null, options, options[0]);

        BaseEntityPanel entityPanel;
        switch(result) {
            case 0:
                entityPanel = new BasicOrganizationPanel(getParentDataObjectId(), classifier);
                break;

            case 1:
                entityPanel = new OrganizationPanel(getParentDataObjectId());
                break;

            default:
                entityPanel = null;
        }

        if(entityPanel != null) {
            DialogResponse response = entityPanel.showDialog(parentComponent);
            if(DialogResponse.SAVE.equals(response)) {
                Organization organization = (Organization)entityPanel.getSelectedValue();
                if(classifier != null) {
                    getClassifiersManager().classifyDataObject(organization.getDataObject(), classifier);
                }
                setSelectedValue(organization);
                return response;
            }
        }

        return DialogResponse.CLOSE;
    }

    @Override
    protected void initData() {
    }
}
