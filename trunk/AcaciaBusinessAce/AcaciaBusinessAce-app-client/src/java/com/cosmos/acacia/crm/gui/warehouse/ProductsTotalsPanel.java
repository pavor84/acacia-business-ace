/*
 * ProductsTotalsPanel.java
 *
 * Created on Понеделник, 2008, Юни 16, 18:58
 */

package com.cosmos.acacia.crm.gui.warehouse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.WarehouseProduct;
import com.cosmos.acacia.gui.AbstractTablePanelListener;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.TablePanelListener;

/**
 *
 * @author  jchan
 */
public class ProductsTotalsPanel extends AcaciaPanel {
    
    @EJB
    private WarehouseListRemote formSession;
    
    private Map<WarehouseProduct, List<WarehouseProduct>> warehouseProductsData;

    /** Creates new form ProductsTotalsPanel */
    public ProductsTotalsPanel(BigInteger parentId) {
        super(parentId);
        initComponents();
        initComponentsCustom();
        initData();
    }

    private void initComponentsCustom() {
        totalsListPanel = new WarehouseProductListPanel(getParentDataObjectId(), true);
        totalsListPanel.setReadonly(true, false);
        totalsPanel.setContentContainer(totalsListPanel);
        
        byWarehousesListPanel = new WarehouseProductListPanel(getParentDataObjectId());
        byWarehousesListPanel.setReadonly();
        byWarehousesListPanel.addTablePanelListener(new AbstractTablePanelListener() {
            @Override
            public void tablePanelClose() {
                close();
            }
        });
        byWarehousePanel.setContentContainer(byWarehousesListPanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSplitPane = new com.cosmos.swingb.JBSplitPane();
        totalsPanel = new com.cosmos.swingb.JBTitledPanel();
        byWarehousePanel = new com.cosmos.swingb.JBTitledPanel();

        setName("Form"); // NOI18N

        mainSplitPane.setDividerLocation(280);
        mainSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setName("mainSplitPane"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(ProductsTotalsPanel.class);
        totalsPanel.setTitle(resourceMap.getString("totalsPanel.title")); // NOI18N
        totalsPanel.setName("totalsPanel"); // NOI18N
        mainSplitPane.setTopComponent(totalsPanel);

        byWarehousePanel.setTitle(resourceMap.getString("byWarehousePanel.title")); // NOI18N
        byWarehousePanel.setName("byWarehousePanel"); // NOI18N
        mainSplitPane.setRightComponent(byWarehousePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.cosmos.swingb.JBTitledPanel byWarehousePanel;
    private com.cosmos.swingb.JBSplitPane mainSplitPane;
    private com.cosmos.swingb.JBTitledPanel totalsPanel;
    private WarehouseProductListPanel totalsListPanel;
    private WarehouseProductListPanel byWarehousesListPanel;
    // End of variables declaration//GEN-END:variables
    @Override
    protected void initData() {
        warehouseProductsData = getFormSession().getWarehouseProductsTotals(getParentDataObjectId());
        List<WarehouseProduct> productsSummary = new ArrayList<WarehouseProduct>(
                warehouseProductsData.keySet());
        totalsListPanel.addTablePanelListener(new TablePanelListener() {
        
            @Override
            public void tableRefreshed() {
            }
        
            @Override
            public void tablePanelClose() {
            }
            @Override
            public void selectionRowChanged() {
                onSummaryProductSelected();
            }
        
            @Override
            public void selectAction() {
            }
        
        });
        
        totalsListPanel.refreshList(productsSummary);
        byWarehousesListPanel.refreshList(new ArrayList<WarehouseProduct>());
    }
    
    protected void onSummaryProductSelected() {
        WarehouseProduct selected = (WarehouseProduct) totalsListPanel.getDataTable().getSelectedRowObject();
        List<WarehouseProduct> objects = warehouseProductsData.get(selected);
        byWarehousesListPanel.refreshList(objects);
    }

    protected WarehouseListRemote getFormSession() {
        if (formSession == null)
            try {
                formSession = InitialContext.doLookup(WarehouseListRemote.class.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        return formSession;
    }
    
}
