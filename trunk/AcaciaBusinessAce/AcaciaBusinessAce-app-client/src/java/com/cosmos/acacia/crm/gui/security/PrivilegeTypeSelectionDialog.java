/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.security;

import com.cosmos.acacia.crm.data.security.EntityPrivilege;
import com.cosmos.acacia.crm.data.security.EntityTypePrivilege;
import com.cosmos.acacia.crm.data.security.PermissionCategoryPrivilege;
import com.cosmos.acacia.crm.data.security.SpecialPermissionPrivilege;
import com.cosmos.swingb.item.ItemSelectionDialog;
import com.cosmos.swingb.item.OptionalItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miro
 */
public class PrivilegeTypeSelectionDialog extends ItemSelectionDialog {

    public PrivilegeTypeSelectionDialog() {
        init();
    }

    protected void init() {
        List<OptionalItem> optionalItems = new ArrayList<OptionalItem>(4);

        OptionalItem item = new OptionalItem(EntityTypePrivilege.class);
        item.setSelected(Boolean.TRUE);
        optionalItems.add(item);

        item = new OptionalItem(PermissionCategoryPrivilege.class);
        optionalItems.add(item);

        item = new OptionalItem(SpecialPermissionPrivilege.class);
        optionalItems.add(item);

        item = new OptionalItem(EntityPrivilege.class);
        optionalItems.add(item);

        setOptionalItems(optionalItems);
    }
}
