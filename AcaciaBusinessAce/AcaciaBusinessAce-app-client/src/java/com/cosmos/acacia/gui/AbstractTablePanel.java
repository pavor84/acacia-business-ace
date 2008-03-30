/*
 * AbstractTablePanel.java
 *
 * Created on Събота, 2008, Март 15, 21:49
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.swingb.DialogResponse;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;

/**
 *
 * @author  Miro
 */
public abstract class AbstractTablePanel
    extends AcaciaPanel
{
    /** Creates new form AbstractTablePanel */
    public AbstractTablePanel(DataObject parentDataObject) {
        super(parentDataObject);
        initComponents();
        initData();
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dataScrollPane = new javax.swing.JScrollPane();
        dataTable = new com.cosmos.acacia.gui.AcaciaTable();
        buttonsPanel = new com.cosmos.swingb.JBPanel();
        closeButton = new com.cosmos.swingb.JBButton();
        refreshButton = new com.cosmos.swingb.JBButton();
        deleteButton = new com.cosmos.swingb.JBButton();
        modifyButton = new com.cosmos.swingb.JBButton();
        newButton = new com.cosmos.swingb.JBButton();
        selectButton = new com.cosmos.swingb.JBButton();
        unselectButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N

        dataScrollPane.setName("dataScrollPane"); // NOI18N

        dataTable.setName("dataTable"); // NOI18N
        dataScrollPane.setViewportView(dataTable);

        buttonsPanel.setName("buttonsPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(AbstractTablePanel.class, this);
        closeButton.setAction(actionMap.get("closeAction")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        refreshButton.setAction(actionMap.get("refreshAction")); // NOI18N
        refreshButton.setName("refreshButton"); // NOI18N

        deleteButton.setAction(actionMap.get("deleteAction")); // NOI18N
        deleteButton.setName("deleteButton"); // NOI18N

        modifyButton.setAction(actionMap.get("modifyAction")); // NOI18N
        modifyButton.setName("modifyButton"); // NOI18N

        newButton.setAction(actionMap.get("newAction")); // NOI18N
        newButton.setName("newButton"); // NOI18N

        selectButton.setAction(actionMap.get("selectAction")); // NOI18N
        selectButton.setName("selectButton"); // NOI18N

        unselectButton.setAction(actionMap.get("unselectAction")); // NOI18N
        unselectButton.setName("unselectButton"); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unselectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {closeButton, deleteButton, modifyButton, newButton, refreshButton, selectButton});

        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unselectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        buttonsPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {closeButton, deleteButton, modifyButton, newButton, refreshButton, selectButton, unselectButton});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addComponent(buttonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(dataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel buttonsPanel;
    private com.cosmos.swingb.JBButton closeButton;
    private javax.swing.JScrollPane dataScrollPane;
    private com.cosmos.acacia.gui.AcaciaTable dataTable;
    private com.cosmos.swingb.JBButton deleteButton;
    private com.cosmos.swingb.JBButton modifyButton;
    private com.cosmos.swingb.JBButton newButton;
    private com.cosmos.swingb.JBButton refreshButton;
    private com.cosmos.swingb.JBButton selectButton;
    private com.cosmos.swingb.JBButton unselectButton;
    // End of variables declaration//GEN-END:variables
    

    private Object selectedRowObject;
    private TableSelectionListener tableSelectionListener;

    protected void initData()
    {
        setVisible(Button.Select, false);
        setVisible(Button.Unselect, false);

        setEnabled(Button.Select, false);
        setEnabled(Button.Unselect, false);
        setEnabled(Button.Modify, false);
        setEnabled(Button.Delete, false);

        if(selectedRowObject != null)
            dataTable.setSelectedRowObject(selectedRowObject);

        addListSelectionListener(dataTable);
    }

    public Object getSelectedRowObject() {
        return selectedRowObject;
    }

    public void setSelectedRowObject(Object selectedRowObject) {
        this.selectedRowObject = selectedRowObject;
    }


    public AcaciaTable getDataTable()
    {
        return dataTable;
    }

    /**
     * 
     * @param rowObject
     * @return true if the object is delete
     */
    protected abstract boolean deleteRow(Object rowObject);

    /**
     * rowObject is the object which have to be modified.
     * The object is not modified if return null. Othervise return the new Object.
     * 
     * @param rowObject
     * @return newRowObject
     */
    protected abstract Object modifyRow(Object rowObject);

    /**
     * 
     * @return
     */
    protected abstract Object newRow();

    public abstract boolean canCreate();
    public abstract boolean canModify(Object rowObject);
    public abstract boolean canDelete(Object rowObject);


    public void addListSelectionListener(AcaciaTable table)
    {
        tableSelectionListener = new TableSelectionListener(table);
    }

    public javax.swing.Action getAction(Button button)
    {
        ApplicationActionMap actionMap = getApplicationActionMap();
        if(actionMap != null && button != null)
        {
            return actionMap.get(button.getActionName());
        }

        return null;
    }

    public void setEnabled(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction)getAction(button);
        if(action != null)
        {
            action.setEnabled(enabled);
        }
    }

    public void setSelected(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction)getAction(button);
        if(action != null)
        {
            action.setSelected(enabled);
        }
    }

    public void setVisible(Button button, boolean visible) {       
        switch(button)
        {
            case Select:
                selectButton.setVisible(visible);
                break;

            case Unselect:
                unselectButton.setVisible(visible);
                break;

            case New:
                newButton.setVisible(visible);
                break;

            case Modify:
                modifyButton.setVisible(visible);
                break;

            case Delete:
                deleteButton.setVisible(visible);
                break;

            case Refresh:
                refreshButton.setVisible(visible);
                break;

            case Close:
                closeButton.setVisible(visible);
                break;
        }
    }

    public boolean isVisible(Button button) {
        switch(button)
        {
            case Select:
                return selectButton.isVisible();

            case Unselect:
                return unselectButton.isVisible();

            case New:
                return newButton.isVisible();

            case Modify:
                return modifyButton.isVisible();

            case Delete:
                return deleteButton.isVisible();

            case Refresh:
                return refreshButton.isVisible();

            case Close:
                return closeButton.isVisible();
        }

        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }

    /**
     * Sets the visible buttons:
     * Select=1, New=2, Modify=4, Delete=8, Refresh=16, Close=32
     * Sum the buttons you want to show and pass the result to the method
     * 
     * @param visibleButtons
     */
    public void setVisibleButtons(int visibleButtons){
        for (ButtonVisibility bv: ButtonVisibility.values())
        {
            if ((bv.getVisibilityIndex() & visibleButtons) != 0)
                setVisible(bv.getButton(), true);
            else
                setVisible(bv.getButton(), false);
        }
    }
    
    /*public void setButtonsTextVisibility(boolean visible){
        selectButton.setToolTipText(selectButton.getText());
        selectButton.setText("");
        
        newButton.setToolTipText(newButton.getText());
        newButton.setText("");
        
        modifyButton.setToolTipText(modifyButton.getText());
        modifyButton.setText("");
        
        deleteButton.setToolTipText(deleteButton.getText());
        deleteButton.setText("");
        
        refreshButton.setToolTipText(refreshButton.getText());
        refreshButton.setText("");
        
        closeButton.setToolTipText(closeButton.getText());
        closeButton.setText("");
    }*/
    
    @Action
    public void selectAction() {
        selectedRowObject = dataTable.getSelectedRowObject();
        setDialogResponse(DialogResponse.SELECT);
        close();
    }

    @Action
    public void unselectAction() {
        dataTable.getSelectionModel().clearSelection();
    }

    @Action
    public void newAction() {
        Object newRowObject = newRow();
        if(newRowObject != null)
        {
            dataTable.addRow(newRowObject);
        }
    }

    @Action
    public void modifyAction() {
        Object rowObject = dataTable.getSelectedRowObject();
        if(rowObject != null)
        {
            if(rowObject instanceof DataObjectBean)
            {
                rowObject = ((DataObjectBean)rowObject).clone();
            }
            
            Object newRowObject = modifyRow(rowObject);
            if(newRowObject != null)
            {
                dataTable.replaceSelectedRow(newRowObject);
            }
        }
    }

    @Action
    public void deleteAction() {
        Object rowObject = dataTable.getSelectedRowObject();
        if(rowObject != null)
        {
            ResourceMap resource = getResourceMap();
            String title = resource.getString("deleteAction.ConfirmDialog.title");
            String message = resource.getString("deleteAction.ConfirmDialog.message");
            Icon icon = resource.getImageIcon("deleteAction.ConfirmDialog.icon");
            int result = JOptionPane.showConfirmDialog(
                    this.getParent(),
                    message,
                    title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon);
            if(JOptionPane.YES_OPTION == result)
            {
                if(deleteRow(rowObject))
                    dataTable.removeSelectedRow();
            }
        }
    }

    @Action
    public Task refreshAction() {
        System.out.println("refreshAction");
        return new RefreshActionTask(getApplication());
    }

    @Action
    public void closeAction() {
        selectedRowObject = null;
        setDialogResponse(DialogResponse.CLOSE);
        close();
    }

    @Override
    protected Class getResourceStopClass()
    {
        return AbstractTablePanel.class;
    }

    @Override
    public DialogResponse showDialog(Component parentComponent)
    {
        setVisible(Button.Select, true);
        setVisible(Button.Unselect, true);
        return super.showDialog(parentComponent);
    }

    



    public enum Button
    {
        Select("selectAction"),
        Unselect("unselectAction"),
        New("newAction"),
        Modify("modifyAction"),
        Delete("deleteAction"),
        Refresh("refreshAction"),
        Close("closeAction");

        private Button(String actionName)
        {
            this.actionName = actionName;
        }

        private String actionName;

        public String getActionName() {
            return actionName;
        }

    };

    /**
     * Enumeration for visibility indices of buttons, used for the bitwise
     * operation in setVisibleButtons
     */
    public enum ButtonVisibility
    {
        Select(1, Button.Select),
        New(2, Button.New),
        Modify(4, Button.Modify),
        Delete(8, Button.Delete),
        Refresh(16, Button.Refresh),
        Close(32, Button.Close),
        Unselect(64, Button.Unselect);
        
        ButtonVisibility(int visibilityIndex, Button button)
        {
            this.visibilityIndex = visibilityIndex;
            this.button = button;
        }
        private int visibilityIndex;
        private Button button;
        
        public int getVisibilityIndex()
        {
            return visibilityIndex;
        }
        
        public Button getButton()
        {
            return button;
        }
    }
    
    public class TableSelectionListener
        implements ListSelectionListener
    {
        private AcaciaTable table;

        public TableSelectionListener(AcaciaTable table)
        {
            table.addListSelectionListener(this);
            this.table = table;
        }

        public void valueChanged(ListSelectionEvent event) {
            if(!event.getValueIsAdjusting())
            {
                ListSelectionModel selectionModel = (ListSelectionModel)event.getSource();
                if(selectionModel.isSelectionEmpty())
                {
                    setEnabled(Button.Modify, false);
                    setEnabled(Button.Delete, false);
                    setEnabled(Button.Select, false);
                    setEnabled(Button.Unselect, false);
                }
                else
                {
                    Object selectedObject = table.getSelectedRowObject();
                    setEnabled(Button.Modify, canModify(selectedObject));
                    setEnabled(Button.Delete, canDelete(selectedObject));
                    setEnabled(Button.Select, true);
                    setEnabled(Button.Unselect, true);
                }
                setEnabled(Button.New, canCreate());
                selectedRowObject = null;
            }
        }
    }

    public class RefreshActionTask
        extends Task<Object, Void>
    {
        RefreshActionTask(Application app)
        {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to RefreshActionTask fields, here.
            super(app);
            System.out.println("RefreshActionTask()");
        }

        @Override
        protected Object doInBackground()
        {
            System.out.println("doInBackground().begin");
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            Object result = null;
            System.out.println("doInBackground().end: " + result);
            return result;  // return your result
        }

        @Override
        protected void succeeded(Object result)
        {
            System.out.println("succeeded(Result:" + result + ")");
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }



}
