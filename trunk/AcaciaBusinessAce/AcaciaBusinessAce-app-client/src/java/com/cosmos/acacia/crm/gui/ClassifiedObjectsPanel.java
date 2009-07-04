/*
 * ClassifiedObjectsPanel.java
 *
 * Created on 03 July 2008, 20:12
 */

package com.cosmos.acacia.crm.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;

import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.data.ClassifiedObjectBean;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AcaciaTable;

/**
 *
 * @author  Bozhidar Bozhanov
 */
public class ClassifiedObjectsPanel extends AcaciaPanel {

    /** Creates new form ClassifiedObjectsPanel */
    public ClassifiedObjectsPanel(BigInteger parentId) {
        super(parentId);
        initComponents();
        initData();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableHolderPanel1 = new com.cosmos.acacia.gui.TableHolderPanel();
        classifiersComboList = new com.cosmos.acacia.gui.AcaciaComboList();
        classifierLabel = new com.cosmos.swingb.JBLabel();

        setName("Form"); // NOI18N

        tableHolderPanel1.setName("tableHolderPanel1"); // NOI18N

        javax.swing.GroupLayout tableHolderPanel1Layout = new javax.swing.GroupLayout(tableHolderPanel1);
        tableHolderPanel1.setLayout(tableHolderPanel1Layout);
        tableHolderPanel1Layout.setHorizontalGroup(
            tableHolderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );
        tableHolderPanel1Layout.setVerticalGroup(
            tableHolderPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        classifiersComboList.setName("classifiersComboList"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ClassifiedObjectsPanel.class);
        classifierLabel.setText(resourceMap.getString("classifierLabel.text")); // NOI18N
        classifierLabel.setName("classifierLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(classifierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classifiersComboList, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                    .addComponent(tableHolderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(classifierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(classifiersComboList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableHolderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBLabel classifierLabel;
    private com.cosmos.acacia.gui.AcaciaComboList classifiersComboList;
    private com.cosmos.acacia.gui.TableHolderPanel tableHolderPanel1;
    // End of variables declaration//GEN-END:variables

    ClassifiersListPanel clp;
    ClassifiedObjectBeansListPanel objectsPanel;

    @Override
    protected void initData() {
        setResizable(false);
        clp = new ClassifiersListPanel(getParentDataObjectId());
        classifiersComboList.initUnbound(clp, "${classifierName}");

        objectsPanel = new ClassifiedObjectBeansListPanel(getParentDataObjectId());
        objectsPanel.setVisibleButtons(0);
        tableHolderPanel1.add(objectsPanel);

        classifiersComboList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                objectsPanel.initData();
            }
        });
    }

    class ClassifiedObjectBeansListPanel extends AbstractTablePanel<ClassifiedObjectBean> {
        BindingGroup objectsBindingGroup;

        public ClassifiedObjectBeansListPanel(BigInteger parentId) {
            super(parentId);
        }

        @Override
        public void initData() {

            super.initData();
            if (objectsBindingGroup != null)
                objectsBindingGroup.unbind();

            objectsBindingGroup = new BindingGroup();
            AcaciaTable objectsTable = getDataTable();
            JTableBinding tableBinding = objectsTable.bind(
                    objectsBindingGroup,
                    getObjects(),
                    getClassifiersManager().getClassifiedObjectBeansEntityProperties());

            objectsBindingGroup.bind();

            objectsTable.setEditable(false);
        }

        private List<ClassifiedObjectBean> getObjects() {
            return getClassifiersManager().getClassifiedObjectBeans((Classifier) classifiersComboList.getSelectedItem());
        }

        @Override
        protected boolean deleteRow(ClassifiedObjectBean rowObject) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        protected ClassifiedObjectBean modifyRow(ClassifiedObjectBean rowObject) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        protected ClassifiedObjectBean newRow() {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean canCreate() {
            return false;
        }

        @Override
        public boolean canModify(ClassifiedObjectBean rowObject) {
            return false;
        }

        @Override
        public boolean canDelete(ClassifiedObjectBean rowObject) {
            return false;
        }
    }
}