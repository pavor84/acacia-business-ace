/*
 * AbstractTablePanel.java
 *
 * Created on Събота, 2008, Март 15, 21:49
 */

package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task;

import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.gui.ClassifyObjectPanel;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.SelectableListDialog;
import com.cosmos.swingb.listeners.TableModificationListener;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author  Miro
 */
public abstract class AbstractTablePanel
    extends AcaciaPanel
    implements SelectableListDialog
{

    protected static Logger log = Logger.getLogger(AbstractTablePanel.class);

    public AbstractTablePanel()
    {
        this((BigInteger)null);
    }

    /** Creates new form AbstractTablePanel */
    public AbstractTablePanel(DataObjectBean dataObjectBean)
    {
        super(dataObjectBean);
        init();
    }

    public AbstractTablePanel(BigInteger parentDataObjectId)
    {
        super(parentDataObjectId);
        init();
    }

    private void init()
    {
        initComponents();
        initData();

        dataTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent event)
            {
                if (event.getClickCount() == 2)
                {
                    performDefaultDoubleClickAction();
                }
            }
        });
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
        classifyButton = new com.cosmos.swingb.JBButton();
        specialFunctionalityButton = new com.cosmos.swingb.JBButton();

        setName("Form"); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onKeyPressed(evt);
            }
        });

        dataScrollPane.setName("dataScrollPane"); // NOI18N

        dataTable.setName("dataTable"); // NOI18N
        dataTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                onKeyPressed(evt);
            }
        });
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

        classifyButton.setAction(actionMap.get("classifyAction")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AbstractTablePanel.class);
        classifyButton.setText(resourceMap.getString("classifyButton.text")); // NOI18N
        classifyButton.setName("classifyButton"); // NOI18N

        specialFunctionalityButton.setAction(actionMap.get("specialAction")); // NOI18N
        specialFunctionalityButton.setName("specialFunctionalityButton"); // NOI18N

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unselectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(specialFunctionalityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(classifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(unselectButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialFunctionalityButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(dataScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE)
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

private void onKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onKeyPressed
    onKeyCommand(evt);
}//GEN-LAST:event_onKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBPanel buttonsPanel;
    private com.cosmos.swingb.JBButton classifyButton;
    private com.cosmos.swingb.JBButton closeButton;
    private javax.swing.JScrollPane dataScrollPane;
    private com.cosmos.acacia.gui.AcaciaTable dataTable;
    private com.cosmos.swingb.JBButton deleteButton;
    private com.cosmos.swingb.JBButton modifyButton;
    private com.cosmos.swingb.JBButton newButton;
    private com.cosmos.swingb.JBButton refreshButton;
    private com.cosmos.swingb.JBButton selectButton;
    private com.cosmos.swingb.JBButton specialFunctionalityButton;
    private com.cosmos.swingb.JBButton unselectButton;
    // End of variables declaration//GEN-END:variables

    private Map<Button, JButton> buttonsMap;

    private boolean editable;
    private boolean visibilitySetChanged = false;
    
    private Object selectedRowObject;
    private Set<TableModificationListener> tableModificationListeners = new HashSet<TableModificationListener>();
    private Classifier classifier;
    private Set<AbstractTablePanel> associatedTables = new HashSet<AbstractTablePanel>();
    
    @EJB
    private ClassifiersRemote classifiersFormSession;
            
    @Override
    protected void initData()
    {
        if (!visibilitySetChanged) {
            setVisible(Button.Select, false);
            setVisible(Button.Unselect, false);
            setVisible(Button.Special, false);
        }

        setEnabled(Button.Select, false);
        setEnabled(Button.Unselect, false);
        setEnabled(Button.Modify, false);
        setEnabled(Button.Delete, false);
        setEnabled(Button.Special, false);
        setEnabled(Button.Classify, false);
        
        if(selectedRowObject != null)
            dataTable.setSelectedRowObject(selectedRowObject);

        addListSelectionListener(dataTable);
    }

    @Override
    public Object getSelectedRowObject() {
        return selectedRowObject;
    }

    @Override
    public void setSelectedRowObject(Object selectedRowObject) {
        this.selectedRowObject = selectedRowObject;
        AcaciaTable table = getDataTable();
        if(selectedRowObject != null)
        {
            table.setSelectedRowObject(selectedRowObject);
        }
        else
        {
            unselect();
        }
    }

    /**
     * A getter for subclasses to use
     * @return JScrollPane
     */
    protected JScrollPane getDataScrollPane(){
        return dataScrollPane;
    }
    
    /**
     * A getter for subclasses to use
     * @return JScrollPane
     */
    protected JBPanel getButtonsPanel(){
        return buttonsPanel;
    }

    public void addColumn(int orderPosition, String propertyName, String columnName,
                           String customELDisplay, EntityProperties entityProperties) {
        PropertyDetails pd = new PropertyDetails(propertyName, columnName, null);
        pd.setCustomDisplay(customELDisplay);
        pd.setOrderPosition(orderPosition);
        entityProperties.addPropertyDetails(pd);
    }

    protected String getString(String key) {
        return getResourceMap().getString(key);
    }

    /**
     * Custom display is EL expression string
     * @param propertyDetails
     * @param propertyName
     * @param customDisplay - provide valid EL expression string.
     * Otherwise no fail-fast in this method. Will fail when compiled.
     */
    public void setCustomDisplay(List<PropertyDetails> propertyDetails, String propertyName, String customDisplay) {
        for (PropertyDetails pd : propertyDetails) {
            if ( pd.getPropertyName().equals(propertyName)){
                pd.setCustomDisplay(customDisplay);
                break;
            }
        }
    }

    public AcaciaTable getDataTable()
    {
        return dataTable;
    }

    public List getListData()
    {
        return getDataTable().getData();
    }

    public void setVisibleSelectButtons()
    {
        setVisible(Button.Select, true);
        setVisible(Button.Unselect, true);
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public boolean isEditable()
    {
        return editable;
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
     * Implement this method for adding new rows
     * 
     * @return the new row object
     */
    protected abstract Object newRow();

    public abstract boolean canCreate();
    public abstract boolean canModify(Object rowObject);
    public abstract boolean canDelete(Object rowObject);


    protected void addListSelectionListener(AcaciaTable table)
    {
        new TableSelectionListener(table);
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

    public void setVisible(Button button)
    {
        setVisible(EnumSet.of(button));
    }

    public void setVisible(Set<Button> buttons)
    {
        Map<Button, JButton> bm = getButtonsMap();
        for(Button button : bm.keySet())
        {
            JButton jb = bm.get(button);
            jb.setVisible(buttons.contains(button));
        }
    }

    public void setVisible(Button button, boolean visible)
    {
        if (visible)
            visibilitySetChanged = true;
        
        getButtonsMap().get(button).setVisible(visible);
    }

    public boolean isVisible(Button button)
    {
        return getButtonsMap().get(button).isVisible();
    }
    
    protected JBButton getButton(Button button) {
        switch(button)
        {
            case Select:
                return selectButton;
            case Unselect:
                return unselectButton;
            case New:
                return newButton;
            case Modify:
                return modifyButton;
            case Delete:
                return deleteButton;
            case Refresh:
                return refreshButton;
            case Close:
                return closeButton;
            case Special:
                return specialFunctionalityButton;
        }
        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }


    /**
     * Sets the visible buttons:
     * Select=1, New=2, Modify=4, Delete=8, Refresh=16, Close=32, Unselect=64, Classify = 128
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
        fireTableSelectAction();
    }

    protected void fireTableSelectAction() {
        for (TablePanelListener listener : tablePanelListeners) {
            listener.selectAction();
        }
    }

    @Action
    public void unselectAction() {
        unselect();
    }

    protected void unselect()
    {
        if(dataTable != null)
            dataTable.getSelectionModel().clearSelection();
    }

    @Action
    public void newAction() {
        Object newRowObject = newRow();
        if(newRowObject != null)
        {
            // Classify the new object if needed
            classifyObject(newRowObject);
            dataTable.addRow(newRowObject);
            fireAdd(newRowObject);
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
                fireModify(newRowObject);
            }
        }
    }
    
    @Action
    public void deleteAction() {
        Object rowObject = dataTable.getSelectedRowObject();
        
        if ( showDeleteConfirmation(getResourceMap().getString("deleteAction.ConfirmDialog.message")) ){
            try {
                if(deleteRow(rowObject))
                {
                    dataTable.removeSelectedRow();
                    fireDelete(rowObject);    
                }
            } catch (Exception ex) {
                ValidationException ve = extractValidationException(ex);
                if (ve != null) {
                    JOptionPane.showMessageDialog(this, formTableReferencedMessage(ve.getMessage()));
                } else {
                    log.error(ex);
                }
            }
            
        }
    }
    
    /**
     * Forms the error message shown when constraint violation occurs
     * 
     * @param the name of the table
     * @return the message
     */
    private String formTableReferencedMessage(String table)
    {
        String message = getResourceMap().getString("deleteAction.err.referenced");
        String tableUserfriendly = 
            getResourceMap().getString("table.userfriendlyname."+table);
        String result = null;
        if ( tableUserfriendly==null )
            result = message + " " + table.replace('_', ' ');
        else
            result = message + " " + tableUserfriendly;
        return result;
    }
    
    
    protected void classifyObject(Object object) {
        if (!(object instanceof DataObjectBean))
            return;
        
        if (getClassifier() == null)
            return;
        
        DataObject dataObject = ((DataObjectBean) object).getDataObject();
        
        getClassifiersFormSession().classifyDataObject(dataObject, getClassifier());
    }
    
    protected ClassifiersRemote getClassifiersFormSession()
    {
        if(classifiersFormSession == null)
        {
            try
            {
                classifiersFormSession = InitialContext.doLookup(ClassifiersRemote.class.getName());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return classifiersFormSession;
    }
    
    protected boolean showDeleteConfirmation(String message){
        ResourceMap resource = getResourceMap();
        String title = resource.getString("deleteAction.ConfirmDialog.title");
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
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Action
    public Task refreshAction() {
        log.info("refreshAction");
        Task result = new RefreshActionTask(getApplication());
        fireTableRefreshed();
        return result;
    }

    @Action
    public void classifyAction() {
        log.info("classifyAction");
        if (getDataTable().getSelectedRowObject() != null) {
            ClassifyObjectPanel cop = new ClassifyObjectPanel(
                ((DataObjectBean)getDataTable().getSelectedRowObject()).getDataObject());
            cop.showDialog();
        }
    }
    
    protected void fireTableRefreshed() {
        for (TablePanelListener listener : tablePanelListeners) {
            listener.tableRefreshed();
        }
    }

    @Action
    public void closeAction() {
        selectedRowObject = null;
        setDialogResponse(DialogResponse.CLOSE);
        close();
        fireTablePanelClose();
    }

    protected void fireTablePanelClose() {
        for (TablePanelListener listener : tablePanelListeners) {
            listener.tablePanelClose();
        }
    }

    @Override
    public DialogResponse showDialog(Component parentComponent)
    {
        setVisibleSelectButtons();
        return super.showDialog(parentComponent);
    }

    public enum Button
    {
        Select("selectAction"),
        Unselect("unselectAction"),
        Classify("classifyAction"),
        New("newAction"),
        Modify("modifyAction"),
        Delete("deleteAction"),
        Refresh("refreshAction"),
        Close("closeAction"),
        Special("specialAction");


        private Button(String actionName)
        {
            this.actionName = actionName;
        }

        private String actionName;

        public String getActionName() {
            return actionName;
        }

        public static final Set<Button> NewModifyDelete = // 2, 4, 8 (14)
            EnumSet.of(New, Modify, Delete);
        public static final Set<Button> NewModifyDeleteUnselect = // 2, 4, 8, 64
            EnumSet.of(New, Modify, Delete, Unselect);
        public static final Set<Button> NewModifyDeleteRefresh = // 2, 4, 8, 16
            EnumSet.of(New, Modify, Delete, Refresh);
        public static final Set<Button> DeleteRefresh = // 8, 16
            EnumSet.of(Delete, Refresh);
    };

    public Map<Button, JButton> getButtonsMap()
    {
        if(buttonsMap == null)
        {
            buttonsMap = new EnumMap<Button, JButton>(Button.class);
            buttonsMap.put(Button.Select, selectButton);
            buttonsMap.put(Button.New, newButton);
            buttonsMap.put(Button.Modify, modifyButton);
            buttonsMap.put(Button.Delete, deleteButton);
            buttonsMap.put(Button.Refresh, refreshButton);
            buttonsMap.put(Button.Close, closeButton);
            buttonsMap.put(Button.Unselect, unselectButton);
            buttonsMap.put(Button.Classify, classifyButton);
            buttonsMap.put(Button.Special, specialFunctionalityButton);
        }

        return buttonsMap;
    }

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
        Unselect(64, Button.Unselect),
        Classify(128, Button.Classify),
        Special(256, Button.Special);

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

        @Override
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
                    setEnabled(Button.Special, false);
                    setEnabled(Button.Classify, false);
                }
                else
                {
                    Object selectedObject = table.getSelectedRowObject();
                    setEnabled(Button.Modify, canModify(selectedObject));
                    setEnabled(Button.Delete, canDelete(selectedObject));
                    setEnabled(Button.Select, true);
                    setEnabled(Button.Unselect, true);
                    setEnabled(Button.Special, true);
                    setEnabled(Button.Classify, true);
                }
                setEnabled(Button.New, canCreate());
                selectedRowObject = null;
                fireSelectionChanged();
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
            log.info("RefreshActionTask()");
        }

        @Override
        protected Object doInBackground()
        {
            log.info("doInBackground().begin");
            // Your Task's code here.  This method runs
            // on a background thread, so don't reference
            // the Swing GUI from here.
            Object result = null;
            log.info("doInBackground().end: " + result);
            return result;  // return your result
        }

        @Override
        protected void succeeded(Object result)
        {
            log.info("succeeded(Result:" + result + ")");
            // Runs on the EDT.  Update the GUI based on
            // the result computed by doInBackground().
        }
    }


    public void addTableModificationListener(TableModificationListener listener)
    {
        tableModificationListeners.add(listener);
    }

    protected void fireSelectionChanged() {
        for (TablePanelListener listener : tablePanelListeners) {
            listener.selectionRowChanged();
        }
    }

    public Set<TableModificationListener> getTableModificationListeners()
    {
        return tableModificationListeners;
    }

    protected void fireAdd(Object row)
    {
        log.info("fireAdd called with " + row);
        for (TableModificationListener listener: tableModificationListeners)
        {
            listener.rowAdded(row);
        }
    }

    protected void fireModify(Object row)
    {
        log.info("fireModify called with " + row);
        for (TableModificationListener listener: tableModificationListeners)
        {
            listener.rowModified(row);
        }
    }

    protected void fireDelete(Object row)
    {
        log.info("fireDelete called with " + row);
        for (TableModificationListener listener: tableModificationListeners)
        {
            listener.rowDeleted(row);
        }
    }
    
    private List<TablePanelListener> tablePanelListeners = new ArrayList<TablePanelListener>();
    
    /**
     * Add new listener
     * @param listener
     */
    public void addTablePanelListener(TablePanelListener listener){
        tablePanelListeners.add(listener);
    }
    
    /**
     * Finds the row that is displaying the passed instance and
     * refreshes the data.
     * Fires row modification event to all listeners.
     * @param newChildren
     */
    public void updateRowObject(Object rowObject) {
        dataTable.updateRow(rowObject);
        fireModify(rowObject);
    }

    /**
     * Gets the classifier for the objects in this table
     * @return
     */
    public Classifier getClassifier() {
        return classifier;
    }

    /**
     * Sets the classifier for the objects in this table
     * @param classifier
     */
    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
        filterByClassifier();
    }
    
    
    protected void filterByClassifier() {
        if (getClassifier() != null) {
            List<Object> data = dataTable.getData();
            List<Object> dataMirror = new LinkedList<Object>(data);
            
            List<DataObject> dataObjects = getClassifiersFormSession().getDataObjects(classifier);
            int i = 0;            
            for (Object obj : data) {
                if (!(obj instanceof DataObjectBean))
                    continue;

                DataObject currentDataObject = ((DataObjectBean) obj).getDataObject();
                
                if (!dataObjects.contains(currentDataObject)) {
                    dataMirror.remove(i);
                    i--;
                }
                i++;
            }
           
            dataTable.setData(dataMirror);
            
            setTitle(getTitle() + " (" + classifier.getClassifierName() + ")");
        }
    }
    
    protected void performDefaultDoubleClickAction() {
        if (isVisible(Button.Select))
            selectAction();
        else
            modifyAction();
    }

    public void setParentDataObjectToAssociatedTables(BigInteger parentDataObjectId) {
        for (AbstractTablePanel table : associatedTables) {
            table.setParentDataObjectId(parentDataObjectId);
        }
    }
    
    public void associateWithTable(AbstractTablePanel table) {
        if (!associatedTables.contains(table)) {
            associatedTables.add(table);
            table.associateWithTable(this);
        }
    }
    
    public void onKeyCommand(KeyEvent evt){
        if (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && evt.getKeyCode() == KeyEvent.VK_N) {
            newAction();
        } else if (evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && evt.getKeyCode() == KeyEvent.VK_D){
            deleteAction();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE ){
            closeAction();
        }
    }

    @Action
    public void specialAction() {
    }
    
    public void setSpecialCaption(String key) {
        specialFunctionalityButton.setText(getResourceMap().getString(key));
    }
}