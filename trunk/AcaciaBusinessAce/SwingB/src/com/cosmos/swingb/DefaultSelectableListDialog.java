/**
 * 
 */
package com.cosmos.swingb;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Created	:	30.04.2009
 * @author	Petar Milev
 *
 */
public class DefaultSelectableListDialog implements SelectableListDialog {

    /** @see com.cosmos.swingb.SelectableListDialog#getListData()
     */
    @Override
    public List getListData() {
        return new ArrayList();
    }

    /** @see com.cosmos.swingb.SelectableListDialog#getSelectedRowObject()
     */
    @Override
    public Object getSelectedRowObject() {
        return null;
    }

    /** @see com.cosmos.swingb.SelectableListDialog#isEditable()
     */
    @Override
    public boolean isEditable() {
        return false;
    }

    /** @see com.cosmos.swingb.SelectableListDialog#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /** @see com.cosmos.swingb.SelectableListDialog#setEditable(boolean)
     */
    @Override
    public void setEditable(boolean editable) {
    }

    /** @see com.cosmos.swingb.SelectableListDialog#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
    }

    /** @see com.cosmos.swingb.SelectableListDialog#setSelectedRowObject(java.lang.Object)
     */
    @Override
    public void setSelectedRowObject(Object selectedObject) {
    }

    /** @see com.cosmos.swingb.SelectableListDialog#setVisibleSelectButtons(boolean)
     */
    @Override
    public void setVisibleSelectButtons(boolean visible) {
    }

    /** @see com.cosmos.swingb.SelectableListDialog#showDialog(java.awt.Component)
     */
    @Override
    public DialogResponse showDialog(Component parentComponent) {
        return null;
    }
}
