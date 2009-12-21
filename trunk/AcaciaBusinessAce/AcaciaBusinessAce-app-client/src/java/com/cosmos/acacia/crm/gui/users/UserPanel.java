/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.gui.entity.AbstractEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.JBButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class UserPanel extends EntityPanel<User> {

    public UserPanel(AbstractEntityListPanel entityListPanel, User entity) {
        super(entityListPanel, entity, null);
    }

    @Override
    protected void init() {
        super.init();

        JBButton userPasswordButton = (JBButton) getJComponent("userPasswordJBButton");
        userPasswordButton.addActionListener(new ChangePasswordAction());
    }

    private class ChangePasswordAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            ChangePasswordForm panel = new ChangePasswordForm(null);
            panel.showDialog(UserPanel.this);
        }
    }
}
