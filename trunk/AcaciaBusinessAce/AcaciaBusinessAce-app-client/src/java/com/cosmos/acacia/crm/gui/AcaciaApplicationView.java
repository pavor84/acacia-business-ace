/*
 * DesktopApplication1View.java
 */

package com.cosmos.acacia.crm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JPopupMenu.Separator;

import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;

import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.contactbook.CitiesListPanel;
import com.cosmos.acacia.crm.gui.contactbook.CountriesListPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PersonsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PositionTypesListPanel;
import com.cosmos.swingb.JBDesktopPane;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBMenu;
import com.cosmos.swingb.JBMenuBar;
import com.cosmos.swingb.JBMenuItem;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBProgressBar;
import com.cosmos.swingb.JBSeparator;
import com.cosmos.swingb.JBToolBar;

/**
 * The application's main frame.
 */
public class AcaciaApplicationView extends FrameView {

    public AcaciaApplicationView(SingleFrameApplication app) {
        super(app);
        initComponents();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setComponent(createMainComponent());
        setMenuBar(createMenuBar());
        setStatusBar(createStatusBar());
        setToolBar(createToolBar());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;

    private boolean saveNeeded;


    public boolean isSaveNeeded() {
        return saveNeeded;
    }

    private void setSaveNeeded(boolean saveNeeded) {
        if (saveNeeded != this.saveNeeded) {
            this.saveNeeded = saveNeeded;
            firePropertyChange("saveNeeded", !saveNeeded, saveNeeded);
        }
    }


    public boolean isRecordSelected() {
        //return masterTable.getSelectedRow() != -1;
        return false;
    }


    @Action
    public void newRecord() {
        /*
        desktopapplication1.Products p = new desktopapplication1.Products();
        entityManager.persist(p);
        list.add(p);
        int row = list.size()-1;
        masterTable.setRowSelectionInterval(row, row);
        masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
        setSaveNeeded(true);
        */
    }

    @Action(enabledProperty = "recordSelected")
    public void deleteRecord() {
        /*
        int[] selected = masterTable.getSelectedRows();
        List<desktopapplication1.Products> toRemove = new ArrayList<desktopapplication1.Products>(selected.length);
        for (int idx=0; idx<selected.length; idx++) {
            desktopapplication1.Products p = list.get(masterTable.convertRowIndexToModel(selected[idx]));
            toRemove.add(p);
            entityManager.remove(p);
        }
        list.removeAll(toRemove);
        setSaveNeeded(true);
        */
    }


    @Action(enabledProperty = "saveNeeded")
    public Task save() {
        return new SaveTask(getApplication());
    }

    private class SaveTask extends Task {
        SaveTask(org.jdesktop.application.Application app) {
            super(app);
        }
        @Override protected Void doInBackground() {
            /*
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            */
            return null;
        }
        @Override protected void finished() {
            setSaveNeeded(false);
        }
    }

    /**
     * An example action method showing how to create asynchronous tasks
     * (running on background) and how to show their progress. Note the
     * artificial 'Thread.sleep' calls making the task long enough to see the
     * progress visualization - remove the sleeps for real application.
     */
    @Action
    public Task refresh() {
       return new RefreshTask(getApplication());
    }

    private class RefreshTask extends Task {
        RefreshTask(org.jdesktop.application.Application app) {
            super(app);
        }
        @Override protected Void doInBackground() {
            /*
            try {
                setProgress(0, 0, 4);
                setMessage("Rolling back the current changes...");
                setProgress(1, 0, 4);
                entityManager.getTransaction().rollback();
                Thread.sleep(1000L); // remove for real app
                setProgress(2, 0, 4);

                setMessage("Starting a new transaction...");
                entityManager.getTransaction().begin();
                Thread.sleep(500L); // remove for real app
                setProgress(3, 0, 4);

                setMessage("Fetching new data...");
                java.util.Collection data = query.getResultList();
                Thread.sleep(1300L); // remove for real app
                setProgress(4, 0, 4);

                Thread.sleep(150L); // remove for real app
                list.clear();
                list.addAll(data);
            } catch(InterruptedException ignore) { }
            */
            return null;
        }
        @Override protected void finished() {
            setMessage("Done.");
            setSaveNeeded(false);
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = AcaciaApplication.getApplication().getMainFrame();
            aboutBox = new AcaciaApplicationAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        AcaciaApplication.getApplication().show(aboutBox);
    }

    @Action
    public void productsListAction() {
        System.out.println("productsListAction");
        ProductsListPanel productsListPanel = new ProductsListPanel(null);
        productsListPanel.showFrame();
    }
    
    @Action
    public void warehouseListAction(){
        WarehouseListPanel listPanel = new WarehouseListPanel(null);
        listPanel.showFrame();
    }
    
    @Action
    public void warehouseProductListAction(){
        WarehouseProductListPanel listPanel = new WarehouseProductListPanel(null);
        listPanel.showFrame();
    }
    
    @Action
    public void patternMaskListAction(){
        PatternMaskFormatListPanel listPanel = new PatternMaskFormatListPanel(null);
        listPanel.showFrame();
    }

    @Action
    public void productCategoriesAction()
    {
        ProductCategoriesTreePanel productCategories = new ProductCategoriesTreePanel(null);
        productCategories.showFrame();
    }

    @Action
    public void personsListAction(){
    	System.out.println("personsListAction");
    	PersonsListPanel personsListPanel = new PersonsListPanel(null);
    	personsListPanel.showFrame();
    }

    @Action
    public void organizationsListAction(){
    	System.out.println("organizationsListAction");
    	OrganizationsListPanel organizationsListPanel = new OrganizationsListPanel(null);
    	organizationsListPanel.showFrame();
    }
     
    @Action
    public void organizationsPositionTypesListAction(){
    	System.out.println("organizationsPositionTypesListAction");
    	PositionTypesListPanel positionTypesListPanel = new PositionTypesListPanel(Organization.class);
    	positionTypesListPanel.showFrame();
    }
    
    @Action
    public void personsPositionTypesListAction(){
    	System.out.println("personsPositionTypesListAction");
    	PositionTypesListPanel positionTypesListPanel = new PositionTypesListPanel(Person.class);
    	positionTypesListPanel.showFrame();
    }
        
    @Action
    public void citiesListAction(){
    	System.out.println("citiesList");
    	CitiesListPanel citiesListPanel = new CitiesListPanel();
    	citiesListPanel.showFrame();
    }
     
          
    @Action
    public void countriesListAction(){
    	System.out.println("cointriesList");
    	CountriesListPanel countriesListPanel = new CountriesListPanel();
    	countriesListPanel.showFrame();
    }

    @Action
    public void classifierGroupsAction()
    {
        ClassifierGroupsPanel classifierGroups = new ClassifierGroupsPanel(null);
        classifierGroups.showFrame();
    }

    @Action
    public void invoicesListAction() {
        System.out.println("invoicesListAction");
        InvoicesListPanel invoicesListPanel = new InvoicesListPanel(null);
        invoicesListPanel.showFrame();
    }
    
    private ActionMap getActionMap()
    {
        return getContext().getActionMap(this);
    }

    // TODO The result of this method must be returned from Server session bean
    private JBMenuBar createMenuBar()
    {
        JBMenuBar menuBar = new JBMenuBar();
        JBMenu fileMenu = new JBMenu();
        JBMenuItem newRecordMenuItem = new JBMenuItem();
        JBMenuItem deleteRecordMenuItem = new JBMenuItem();
        JBSeparator jSeparator1 = new JBSeparator();
        JBMenuItem saveMenuItem = new JBMenuItem();
        JBMenuItem refreshMenuItem = new JBMenuItem();
        JBSeparator jSeparator2 = new JBSeparator();
        JBMenuItem exitMenuItem = new JBMenuItem();
        JBMenu productsMenu = new JBMenu();
        JBMenu helpMenu = new JBMenu();
        JBMenuItem aboutMenuItem = new JBMenuItem();
        JBMenu businessPartners = new JBMenu();
        JBMenuItem personsListMenuItem = new JBMenuItem();
        JBMenuItem newPersonMenuItem = new JBMenuItem();
        JBMenuItem patternMasksItem = new JBMenuItem();
        JBMenu warehousesMenu = new JBMenu();
        JBMenuItem warehousesItem = new JBMenuItem();
        JBMenuItem warehouseProductsItem = new JBMenuItem();

        menuBar.setName("menuBar"); // NOI18N

        //org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AcaciaApplicationView.class);
        ResourceMap resourceMap = getResourceMap();
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        //javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getActionMap(AcaciaApplicationView.class, this);
        ActionMap actionMap = getActionMap();
        newRecordMenuItem.setAction(actionMap.get("newRecord")); // NOI18N
        newRecordMenuItem.setName("newRecordMenuItem"); // NOI18N
        fileMenu.add(newRecordMenuItem);

        deleteRecordMenuItem.setAction(actionMap.get("deleteRecord")); // NOI18N
        deleteRecordMenuItem.setName("deleteRecordMenuItem"); // NOI18N
        fileMenu.add(deleteRecordMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        saveMenuItem.setAction(actionMap.get("save")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        refreshMenuItem.setAction(actionMap.get("refresh")); // NOI18N
        refreshMenuItem.setName("refreshMenuItem"); // NOI18N
        fileMenu.add(refreshMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        productsMenu.setText(resourceMap.getString("productsMenu.text")); // NOI18N
        productsMenu.setName("productsMenu"); // NOI18N

        JBMenuItem menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("productsListAction")); // NOI18N
        menuItem.setName("productsListMenuItem"); // NOI18N
        productsMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("productCategoriesAction")); // NOI18N
        menuItem.setName("productCategoriesMenuItem"); // NOI18N
        productsMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("classifierGroupsAction")); // NOI18N
        menuItem.setName("classifierGroupsMenuItem"); // NOI18N
        productsMenu.add(menuItem);

        menuBar.add(productsMenu);

        businessPartners.setName("businessPartnersMenu");
        businessPartners.setText(resourceMap.getString("businessPartnersMenu.text"));

        newPersonMenuItem.setAction(actionMap.get("newPersonAction"));
        newPersonMenuItem.setName("newPersonMenuItem");
        businessPartners.add(newPersonMenuItem);

        personsListMenuItem.setAction(actionMap.get("personsListAction"));
        personsListMenuItem.setName("personsListMenuItem");
        businessPartners.add(personsListMenuItem);
        
        Separator partnersSectionSeparator = new Separator();
        businessPartners.add(partnersSectionSeparator);
        
        patternMasksItem.setAction(actionMap.get("patternMaskListAction"));
        patternMasksItem.setName("patternMasksItem");
        patternMasksItem.setText(resourceMap.getString("patternMasksItem.text"));
        businessPartners.add(patternMasksItem);

        menuBar.add(businessPartners);
        
        warehousesMenu.setName("warehousesMenu"); // NOI18N
        warehousesMenu.setText(resourceMap.getString("warehousesMenu.text")); // NOI18N

        warehousesItem.setAction(actionMap.get("warehouseListAction")); // NOI18N
        warehousesItem.setText(resourceMap.getString("warehousesItem.text"));
        warehousesItem.setName("warehouseListItem"); // NOI18N
        warehousesMenu.add(warehousesItem);
        
        warehouseProductsItem.setAction(actionMap.get("warehouseProductListAction")); // NOI18N
        warehouseProductsItem.setText(resourceMap.getString("warehouseProductsItem.text"));
        warehouseProductsItem.setName("warehouseProductsItem"); // NOI18N
        warehousesMenu.add(warehouseProductsItem);
        
        menuBar.add(warehousesMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        return menuBar;
    }

    // TODO The result of this method must be returned from Server session bean
    private JBToolBar createToolBar()
    {
        JBToolBar toolBar = new JBToolBar();
        ActionMap actionMap = getActionMap();
        for(Object key : actionMap.allKeys())
        {
            javax.swing.Action action = actionMap.get(key);
            toolBar.add(action);
        }

        return toolBar;
    }


    private JBLabel statusMessageLabel;
    private JBLabel statusAnimationLabel;
    private JBProgressBar progressBar;

    private Timer messageTimer;
    private Timer busyIconTimer;
    private Icon idleIcon;
    private Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JBPanel createStatusBar()
    {
        JBPanel statusPanel = new JBPanel();
        JSeparator statusPanelSeparator = new JSeparator();
        statusMessageLabel = new JBLabel();
        statusAnimationLabel = new JBLabel();
        progressBar = new JBProgressBar();

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
	messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
	messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        return statusPanel;
    }

    private JComponent createMainComponent()
    {
        JBDesktopPane desktopPane = new JBDesktopPane();
        desktopPane.setName("desktopPane"); // NOI18N
        return desktopPane;
    }
}
