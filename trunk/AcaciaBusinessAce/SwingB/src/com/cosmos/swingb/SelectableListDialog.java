/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Component;
import java.util.List;

/**
 *
 * @author Miro
 */
public interface SelectableListDialog {

    DialogResponse showDialog(Component parentComponent);

    Object getSelectedRowObject();

    void setSelectedRowObject(Object selectedObject);

    List getListData();

    void setEditable(boolean editable);

    boolean isEditable();

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void setVisibleSelectButtons(boolean visible);
}
