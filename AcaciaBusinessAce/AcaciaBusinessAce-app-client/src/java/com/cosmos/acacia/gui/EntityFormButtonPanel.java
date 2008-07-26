/*
 * EntityFormButtonPanel.java
 *
 * Created on 30 March 2008, 00:32
 */

package com.cosmos.acacia.gui;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.beansbinding.AbstractBindingListener;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.PropertyStateEvent;

import com.cosmos.swingb.JBButton;


/**
 *
 * @author  Bozhidar Bozhanov
 */
public class EntityFormButtonPanel extends AcaciaPanel {

    protected static Logger log = Logger.getLogger(EntityFormButtonPanel.class);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(problemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(customButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(problemsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBButton closeButton;
    private com.cosmos.swingb.JBButton customButton;
    private com.cosmos.swingb.JBButton problemsButton;
    private com.cosmos.swingb.JBButton saveButton;
    // End of variables declaration//GEN-END:variables


    @Action
    public void saveAction()
    {
        try {
            BaseEntityPanel parent = (BaseEntityPanel) getParent();
            if (parent.checkFormValidity()){
                parent.saveAction();
            }
        } catch (ClassCastException ex) {
            log.info("saveAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    @Action
    public void closeAction()
    {
        try {
            ((BaseEntityPanel) getParent()).closeAction();
        } catch (ClassCastException ex) {
            log.info("closeAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    @Action
    public void problemsAction()
    {
        try {
            ((BaseEntityPanel) getParent()).checkFormValidity();
        } catch (ClassCastException ex) {
            log.info("problemsAction: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    public void initSaveStateListener()
    {
        try {
            final BaseEntityPanel parent = (BaseEntityPanel) getParent();
            setSaveActionState(parent);

            BindingGroup bindingGroup;
            if((bindingGroup = parent.getBindingGroup()) != null)
            {
                bindingGroup.addBindingListener(new AbstractBindingListener()
                {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void targetChanged(Binding binding, PropertyStateEvent event) {
                        setSaveActionState(parent);
                    }
                });
            }
        } catch (ClassCastException ex) {
            log.error("initSaveStateListener: Parent of the EntityFormButtonPanel can only be BaseEntityPanel");
        }
    }

    public void setSaveActionState(BaseEntityPanel parent)
    {
        BindingGroup bindingGroup;
        if((bindingGroup = parent.getBindingGroup()) != null)
        {
            setEnabled(Button.Save, bindingGroup.isContentValid());
            setEnabled(Button.Problems, !bindingGroup.isContentValid());
            setEnabled(Button.Custom, bindingGroup.isContentValid());
        }
    }


    public enum Button
    {
        Save("saveAction"),
        Close("closeAction"),
        Problems("problemsAction"),
        Custom("customButton");

        private Button(String actionName)
        {
            this.actionName = actionName;
        }

        private String actionName;

        public String getActionName() {
            return actionName;
        }
    };

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
        }else{
            if ( button.equals(Button.Save) )
                saveButton.setEnabled(enabled);
            else if ( button.equals(Button.Close) )
                closeButton.setEnabled(enabled);
            else if ( button.equals(Button.Problems) )
                problemsButton.setEnabled(enabled);
            else if ( button.equals(Button.Custom) )
                customButton.setEnabled(enabled);
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
        }
    }

    public boolean isVisible(Button button) {
        switch(button)
        {
            case Save:
                return saveButton.isVisible();

            case Close:
                return closeButton.isVisible();
                
            case Problems:
                return problemsButton.isVisible();
                
            case Custom:
                return customButton.isVisible();
        }
        
        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }

    @Override
    protected void initData() {
        setVisible(Button.Custom, false);
    }

    public com.cosmos.swingb.JBButton getCustomButton() {
        return customButton;
    }
    
    public JBButton getButton(Button button){
        switch(button)
        {
            case Save:
                return saveButton;

            case Close:
                return closeButton;
                
            case Problems:
                return problemsButton;
                
            case Custom:
                return customButton;
        }
        throw new IllegalArgumentException("Unknown or unsupported Button enumeration: " + button);
    }
    
}
