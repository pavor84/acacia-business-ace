/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import java.util.List;

/**
 *
 * @author Miro
 */
public class UserListPanel extends EntityListPanel<User> {

    public UserListPanel() {
        super(User.class, null);
    }

    public UserListPanel(User user) {
        super(User.class, null, user);
    }

    @Override
    protected EntityPanel getEntityPanel(User entity) {
        return new UserPanel(this, entity);
    }

    @Override
    public List<User> getEntities() {
        User user;
        Class entityClass = getEntityClass();
        if((user = getUser()) != null) {
            return getEntityService().getEntities(entityClass, user);
        } else {
            return getEntityService().getEntities(entityClass);
        }
    }

    public User getUser() {
        if(parameters != null && parameters.length > 0 && parameters[0] instanceof User) {
            return (User) parameters[0];
        }

        return null;
    }

    public void setUser(User user) {
        if(parameters == null) {
            parameters = new Object[1];
        }
        parameters[0] = user;
        refresh();
    }
}
