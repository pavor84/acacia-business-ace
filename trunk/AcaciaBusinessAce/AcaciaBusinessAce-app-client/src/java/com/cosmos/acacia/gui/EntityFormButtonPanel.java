/*
 * EntityFormButtonPanel.java
 *
 * Created on 30 March 2008, 00:32
 */
package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.FlowLayout;

//import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;

import com.cosmos.swingb.JBButton;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class EntityFormButtonPanel extends AcaciaPanel {

    private static final long serialVersionUID = 1L;

    //protected static Logger log = Logger.getLogger(EntityFormButtonPanel.class);
    /** Creates new form EntityFormButtonPanel */
    public EntityFormButtonPanel() {
        super();
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

        saveButton = new com.cosmos.swingb.JBButton();
        closeButton = new com.cosmos.swingb.JBButton();
        problemsButton = new com.cosmos.swingb.JBButton();
        customButton = new com.cosmos.swingb.JBButton();
        printButton = new com.cosmos.swingb.JBButton();
        customButtonsPanel = new com.cosmos.swingb.JBPanel();

        setName("Form"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(EntityFormButtonPanel.class, this);
        saveButton.setAction(actionMap.get("saveAction")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(EntityFormButtonPanel.class);
        saveButton.setToolTipText(resourceMap.getString("saveButton.toolTipText")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        closeButton.setAction(actionMap.get("closeAction")); // NOI18N
        closeButton.setToolTipText(resourceMap.getString("closeButton.toolTipText")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N

        problemsButton.setAction(actionMap.get("problemsAction")); // NOI18N
        problemsButton.setToolTipText(resourceMap.getString("problemsButton.toolTipText")); // NOI18N
        problemsButton.setName("problemsButton"); // NOI18N

        customButton.setIcon(resourceMap.getIcon("customButton.icon")); // NOI18N
        customButton.setText(resourceMap.getString("customButton.text")); // NOI18N
        customButton.setName("customButton"); // NOI18N

        printButton.setAction(actionMap.get("printAction")); // NOI18N
        printButton.setIcon(resourceMap.getIcon("customButton.icon")); // NOI18N
        printButton.setName("printButton"); // NOI18N

        customButtonsPanel.setName("customButtonsPanel"); // NOI18N

        javax.swing.GroupLayout customButtonsPanelLayout = new javax.swing.GroupLayout(customButtonsPanel);
        customButtonsPanel.setLayout(customButtonsPanelLayout);
        customButtonsPanelLayout.setHorizontalGroup(
            customButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );
        customButtonsPanelLayout.setVerticalGroup(
            customButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(problemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(customButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(customButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(problemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(customButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBButton customButton;
    private com.cosmos.swingb.JBPanel customButtonsPanel;
    private com.cosmos.swingb.JBButton printButton;
    private com.cosmos.swingb.JBButton problemsButton;
    private com.cosmos.swingb.JBButton saveButton;
    // End of variables declaration//GEN-END:variables
    private BaseEntityPanel baseEntityPanel;

    @Action
    public void saveAction() {
        try {
            if (baseEntityPanel.checkFormValidity()) {
                baseEntityPanel.saveAction();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            //log.info("saveAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    @Action
    public void closeAction() {
        try {
            baseEntityPanel.closeAction();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            //log.info("closeAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    @Action
    public void problemsAction() {
        try {
            baseEntityPanel.checkFormValidity();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            //log.info("problemsAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    @Action
    public void printAction() {
        try {
            baseEntityPanel.print();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            //log.info("printAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    public void initSaveStateListener(final BaseEntityPanel baseEntityPanel) {
        this.baseEntityPanel = baseEntityPanel;
//        try {
//            setSaveActionState(baseEntityPanel);
//
//            BindingGroup bindingGroup = baseEntityPanel.getBindingGroup();
//            if(bindingGroup != null)
//            {
//                bindingGroup.addBindingListener(new AbstractBindingListener()
//                {
//                    @SuppressWarnings("unchecked")
//                    @Override
//                    public void targetChanged(Binding binding, PropertyStateEvent event) {
//                        setSaveActionState(baseEntityPanel);
//                    }
//                });
//            }
//        } catch (ClassCastException ex) {
//            ex.printStackTrace();
//            //log.error("initSaveStateListener: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
//        }
    }

    public void setSaveActionState(BaseEntityPanel parent) {
        boolean isContentValid = baseEntityPanel.isContentValid();
        setEnabled(Button.Save, isContentValid);
        setEnabled(Button.Problems, !isContentValid);
        setEnabled(Button.Custom, isContentValid);
        setEnabledCustomButtons(isContentValid);
    }

    protected void setEnabledCustomButtons(boolean enabled) {
        for (Component button : customButtonsPanel.getComponents()) {
            button.setEnabled(enabled);
        }
    }

    public enum Button {

        Save("saveAction"),
        Close("closeAction"),
        Problems("problemsAction"),
        Custom("customButton"),
        Print("printButton");

        private Button(String actionName) {
            this.actionName = actionName;
        }
        private String actionName;

        public String getActionName() {
            return actionName;
        }
    };

    public javax.swing.Action getAction(Button button) {
        ApplicationActionMap actionMap = getApplicationActionMap();

        if (actionMap != null && button != null) {
            return actionMap.get(button.getActionName());
        }

        return null;
    }

    public void setEnabled(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction) getAction(button);
        if (action != null) {
            action.setEnabled(enabled);
        } else {
            if (button.equals(Button.Save)) {
                saveButton.setEnabled(enabled);
            } else if (button.equals(Button.Close)) {
                closeButton.setEnabled(enabled);
            } else if (button.equals(Button.Problems)) {
                problemsButton.setEnabled(enabled);
            } else if (button.equals(Button.Custom)) {
                customButton.setEnabled(enabled);
            }
        }
    }

    public void setSelected(Button button, boolean enabled) {
        ApplicationAction action = (ApplicationAction) getAction(button);
        if (action != null) {
            action.setSelected(enabled);
        }
    }

    public void setVisible(Button button, boolean visible) {
        switch (button) {
            case Save:
                saveButton.setVisible(visible);
                break;

            case Close:
                closeButton.setVisible(visible);
                break;

            case Problems:
                problemsButton.setVisible(visible);
                break;

            case Custom:
                customButton.setVisible(visible);
                break;

            case Print:
                printButton.setVisible(visible);
                break;
        }
    }

    public boolean isVisible(Button button) {
        switch (button) {
            case Save:
                return saveButton.isVisible();

            case Close:
                return closeButton.isVisible();

            case Problems:
                return problemsButton.isVisible();

            case Custom:
                return customButton.isVisible();

            case Print:
                return printButton.isVisible();
        }

        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }

    @Override
    protected void initData() {
        setVisible(Button.Custom, false);

        customButtonsPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 10, 0));
    }

    public com.cosmos.swingb.JBButton getCustomButton() {
        return customButton;
    }

    public JBButton getButton(Button button) {
        switch (button) {
            case Save:
                return saveButton;

            case Close:
                return closeButton;

            case Problems:
                return problemsButton;

            case Custom:
                return customButton;

            case Print:
                return printButton;
        }
        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }

    /**
     * Adds additional button to the panel. The button is added on the left side of the close button.
     * Any number of buttons can be added. Their state (visible, enabled) should be managed externally.
     * @param Button
     */
    public void addButton(JBButton button) {
        customButtonsPanel.add(button);
    }

    /**
     * Removes a button from the additional buttons panel. The button should have been added
     * before with {@link #addButton(JBButton)}
     * @param button
     */
    public void removeButton(JBButton button) {
        customButtonsPanel.remove(button);
    }

    /**
     * Removes all buttons added before with {@link #addButton(JBButton)}
     * @param button
     */
    public void removeAllButtons() {
        customButtonsPanel.removeAll();
    }
}
