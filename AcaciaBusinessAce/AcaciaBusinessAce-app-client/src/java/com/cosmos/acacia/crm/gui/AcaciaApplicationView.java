/*
 * DesktopApplication1View.java
 */

package com.cosmos.acacia.crm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPopupMenu.Separator;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.gui.assembling.AssemblingCategoryTreeTablePanel;
import com.cosmos.acacia.crm.gui.assembling.AssemblingSchemasListPanel;
import com.cosmos.acacia.crm.gui.contactbook.BusinessPartnersListPanel;
import com.cosmos.acacia.crm.gui.contactbook.CitiesListPanel;
import com.cosmos.acacia.crm.gui.contactbook.CountriesListPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationPanel;
import com.cosmos.acacia.crm.gui.contactbook.OrganizationsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PersonsListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PositionTypesListPanel;
import com.cosmos.acacia.crm.gui.contactbook.PositionsHierarchyTreePanel;
import com.cosmos.acacia.crm.gui.invoice.InvoicesListPanel;
import com.cosmos.acacia.crm.gui.purchaseorders.OrderConfirmationListPanel;
import com.cosmos.acacia.crm.gui.purchaseorders.OrdersMatchingForm;
import com.cosmos.acacia.crm.gui.purchaseorders.PurchaseOrderListPanel;
import com.cosmos.acacia.crm.gui.users.JoinOrganizationForm;
import com.cosmos.acacia.crm.gui.users.LeaveOrganizationForm;
import com.cosmos.acacia.crm.gui.users.LoginForm;
import com.cosmos.acacia.crm.gui.users.UserGroupsListPanel;
import com.cosmos.acacia.crm.gui.users.UsersListPanel;
import com.cosmos.acacia.crm.gui.warehouse.ProductsTotalsPanel;
import com.cosmos.acacia.crm.gui.warehouse.WarehouseListPanel;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.AbstractTablePanel.Button;
import com.cosmos.acacia.settings.GeneralSettings;
import com.cosmos.swingb.DialogResponse;
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

    protected static Logger log = Logger.getLogger(AcaciaApplicationView.class);

    public AcaciaApplicationView(SingleFrameApplication app) {
        super(app);
        setLookAndFeel();


        getFrame().setExtendedState(getFrame().getExtendedState() | JFrame.MAXIMIZED_BOTH);
        getFrame().requestFocus();
    }

    public void init() {
        System.out.println("loaded");
        initComponents();
        getRootPane().repaint();
    }

    public void login() {
        LoginForm loginForm = new LoginForm();
        DialogResponse response = loginForm.showDialog(AcaciaApplication.getApplication().getMainFrame());
        if(!DialogResponse.LOGIN.equals(response)) {
            AcaciaApplication.getApplication().exit();
        }
    }

    private BigInteger parentId;
    private AcaciaSessionRemote acaciaSession;

    private AcaciaSessionRemote getSession() {
        if (acaciaSession == null)
            acaciaSession = AcaciaPanel.getBean(AcaciaSessionRemote.class);

        return acaciaSession;
    }

    private BigInteger getParentId() {
        if (parentId == null) {
            try {
                parentId = getSession().getOrganization().getId();
            } catch (Exception ex) {
                // log
            }
        }

        return parentId;
    }
    public static void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            LiquidLookAndFeel.setShowTableGrids(false);
            LiquidLookAndFeel.setLiquidDecorations(true);

            //tried to change the default JFrame icon (java cup), but no luck

        } catch (ClassNotFoundException ex) {
            log.debug("Cannot get L&F library: " + ex.getMessage());
        } catch (InstantiationException ex) {
            log.debug("Cannot instantiate L&F: " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            log.debug("Cannot access L&F: " + ex.getMessage());
        } catch (UnsupportedLookAndFeelException ex) {
            log.debug("Unsupported L&F: " + ex.getMessage());
        }
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


    @SuppressWarnings("unchecked")
    @Action(enabledProperty = "saveNeeded")
    public Task save() {
        return new SaveTask(getApplication());
    }

    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    @Action
    public Task refresh() {
       return new RefreshTask(getApplication());
    }

    @SuppressWarnings("unchecked")
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
        ProductsListPanel productsListPanel = new ProductsListPanel(getParentId());
        productsListPanel.showFrame();
    }

    @Action
    public void warehouseListAction(){
        WarehouseListPanel listPanel = new WarehouseListPanel(getParentId());
        listPanel.showFrame();
    }

    @Action
    public void productsTotalsAction(){
        ProductsTotalsPanel panel = new ProductsTotalsPanel(getParentId());
        panel.showFrame();
    }

//    @Action
//    public void warehouseProductListAction(){
//        WarehouseProductListPanel listPanel = new WarehouseProductListPanel(null);
//        listPanel.showFrame();
//    }

    @Action
    public void deliveryCertificatesAction()
    {
        DeliveryCertificatesListPanel listPanel = new DeliveryCertificatesListPanel(null);
        listPanel.showFrame();
    }

    @Action
    public void receiptCertificatesAction()
    {
    }

    @Action
    public void reportsAction()
    {
    }

    @Action
    public void patternMaskListAction(){
        PatternMaskFormatListPanel listPanel = new PatternMaskFormatListPanel(getParentId());
        listPanel.showFrame();
    }

    @Action
    public void productCategoriesAction()
    {
        ProductCategoriesTreePanel productCategories = new ProductCategoriesTreePanel(getParentId());
        productCategories.getListPanel().setVisible(Button.Select, false);
        productCategories.getListPanel().setVisible(Button.Unselect, false);
        productCategories.showFrame();
    }

    @Action
    public void assemblingCategoriesAction()
    {
        AssemblingCategoryTreeTablePanel assemblingCategories = new AssemblingCategoryTreeTablePanel(null);
        //AssemblingCategoriesTreePanel assemblingCategories = new AssemblingCategoriesTreePanel(null);
        //assemblingCategories.getListPanel().setVisible(Button.Select, false);
        //assemblingCategories.getListPanel().setVisible(Button.Unselect, false);
        assemblingCategories.showFrame();
    }

    @Action
    public void assemblingSchemasAction()
    {
        log.debug("personsListAction");
        AssemblingSchemasListPanel assemblingSchemas = new AssemblingSchemasListPanel(null);
        assemblingSchemas.showFrame();
    }

    @Action
    public void productAssemblerAction()
    {
    }

    @Action
    public void algorithmsAction()
    {
    }

    @Action
    public void personsListAction(){
        log.debug("personsListAction");
        PersonsListPanel personsListPanel = new PersonsListPanel(getParentId());
        personsListPanel.showFrame();
    }

    @Action
    public void organizationsListAction(){
        log.debug("organizationsListAction");
        OrganizationsListPanel organizationsListPanel = new OrganizationsListPanel(getParentId());
        organizationsListPanel.showFrame();
    }

    @Action
    public void organizationPositionTypesListAction(){
        log.debug("organizationPositionTypesListAction");
        PositionTypesListPanel positionTypesListPanel = new PositionTypesListPanel(Organization.class, getParentId());
        positionTypesListPanel.showFrame();
    }

    @Action
    public void organizationInternalHierarchyAction(){
        PositionsHierarchyTreePanel panel = new PositionsHierarchyTreePanel(getParentId());
        panel.showFrame();
    }

    @Action
    public void businessPartnersListAction(){
        BusinessPartnersListPanel listPanel = new BusinessPartnersListPanel(getParentId());
        listPanel.showFrame();
    }

    @Action
    public void personPositionTypesListAction(){
        log.debug("personPositionTypesListAction");
        PositionTypesListPanel positionTypesListPanel = new PositionTypesListPanel(Person.class, getParentId());
        positionTypesListPanel.showFrame();
    }

    @Action
    public void citiesListAction(){
        log.debug("citiesList");
        CitiesListPanel citiesListPanel = new CitiesListPanel();
        citiesListPanel.showFrame();
    }


    @Action
    public void countriesListAction(){
        log.debug("cointriesList");
        CountriesListPanel countriesListPanel = new CountriesListPanel();
        countriesListPanel.showFrame();
    }

    @Action
    public void classifierGroupsAction() {
        ClassifierGroupsListPanel classifierGroups = new ClassifierGroupsListPanel(null);
        classifierGroups.showFrame();
    }

    @Action
    public void classifiersAction() {
        ClassifiersListPanel classifiers = new ClassifiersListPanel(null, null);
        classifiers.showFrame();
    }

    @Action
    public void classifiedObjectsAction() {
        ClassifiedObjectsPanel panel = new ClassifiedObjectsPanel(null);
        panel.showFrame();
    }

    @Action
    public void usersListAction() {
        UsersListPanel panel = new UsersListPanel(getParentId());
        panel.showFrame();
    }

    @Action
    public void joinOrganizationAction() {
        JoinOrganizationForm panel = new JoinOrganizationForm(getParentId());
        panel.showFrame();
    }

    @Action
    public void leaveOrganizationAction() {
        LeaveOrganizationForm panel = new LeaveOrganizationForm(getParentId());
        panel.showFrame();
    }

    @Action
    public void ordersAction() {
        BigInteger parentId = getParentId();
        PurchaseOrderListPanel panel = new PurchaseOrderListPanel(parentId);
        panel.showFrame();
    }

    @Action
    public void ordersMatchingAction() {
        BigInteger parentId = getParentId();
        OrdersMatchingForm panel = new OrdersMatchingForm(parentId);
        panel.showFrame();
    }

    @Action
    public void orderConfirmationsAction() {
        BigInteger parentId = getParentId();
        OrderConfirmationListPanel panel = new OrderConfirmationListPanel(parentId);
        panel.showFrame();
    }

    @Action
    public void ownOrganizationAction() {
        OrganizationPanel panel = new OrganizationPanel();
        panel.showFrame();
    }

    @Action
    public void invoicesListAction() {
        System.out.println("invoicesListAction");
        InvoicesListPanel invoicesListPanel = new InvoicesListPanel(null);
        invoicesListPanel.showFrame();
    }


    @Action
    public void userGroupsListAction() {
        UserGroupsListPanel panel = new UserGroupsListPanel(getParentId());
        panel.showFrame();
    }

    private ActionMap getActionMap()
    {
        return getContext().getActionMap(this);
    }

    // TODO The result of this method must be returned from Server session bean
    private JBMenuBar createMenuBar()
    {
        JBMenuItem menuItem;

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
        JBMenu assemblingMenu = new JBMenu();
        JBMenu salesMenu = new JBMenu();
        JBMenu ordersMenu = new JBMenu();
        JBMenu reportsMenu = new JBMenu();
        JBMenu helpMenu = new JBMenu();
        JBMenuItem aboutMenuItem = new JBMenuItem();

        /* Contact book menu items */
        JBMenu contactBook = new JBMenu();
        JBMenuItem personsListMenuItem = new JBMenuItem();
        JBMenuItem organizationsListMenuItem = new JBMenuItem();
        JBMenuItem personPositionTypesListMenuItem = new JBMenuItem();
        JBMenuItem organizationPositionTypesListMenuItem = new JBMenuItem();
        JBMenuItem citiesListMenuItem = new JBMenuItem();
        JBMenuItem countriesListMenuItem = new JBMenuItem();
        JBMenuItem joinOrganizationMenuItem = new JBMenuItem();
        JBMenuItem leaveOrganizationMenuItem = new JBMenuItem();
        JBMenuItem ownOrganizationMenuItem = new JBMenuItem();
        JBMenuItem organizationInternalHierarchyMenuItem = new JBMenuItem();
        /* End of contact book menu items */

        JBMenu classifiersMenu = new JBMenu();

        JBMenu adminMenu = new JBMenu();
        JBMenuItem usersListMenuItem = new JBMenuItem();
        JBMenuItem userGroupsMenuItem = new JBMenuItem();

        JBMenuItem patternMasksItem = new JBMenuItem();
        JBMenuItem invoicesListItem = new JBMenuItem();
        JBMenu warehousesMenu = new JBMenu();
        JBMenuItem warehousesItem = new JBMenuItem();
        JBMenuItem productsTotalsItem = new JBMenuItem();

        //JBMenuItem warehouseProductsItem = new JBMenuItem();

        menuBar.setName("menuBar"); // NOI18N

        //org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.cosmos.acacia.crm.gui.AcaciaApplication.class).getContext().getResourceMap(AcaciaApplicationView.class);
        ResourceMap resourceMap = getResourceMap();
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N
        fileMenu.setMnemonic('F');

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

        salesMenu.setText(resourceMap.getString("salesMenu.text")); // NOI18N
        salesMenu.setName("salesMenu"); // NOI18N
        salesMenu.setMnemonic('S');

        invoicesListItem.setAction(actionMap.get("invoicesListAction"));
        salesMenu.add(invoicesListItem);
        menuBar.add(salesMenu);

        ordersMenu.setText(resourceMap.getString("ordersMenu.text")); // NOI18N
        ordersMenu.setName("ordersMenu"); // NOI18N
        ordersMenu.setMnemonic('O');

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("ordersAction"));
        menuItem.setMnemonic('P');
        ordersMenu.add(menuItem);
        menuBar.add(ordersMenu);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("orderConfirmationsAction"));
        menuItem.setMnemonic('C');
        ordersMenu.add(menuItem);

        ordersMenu.add(new Separator());

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("ordersMatchingAction"));
        menuItem.setMnemonic('M');
        ordersMenu.add(menuItem);

        reportsMenu.setText(resourceMap.getString("reportsMenu.text")); // NOI18N
        reportsMenu.setName("reportsMenu"); // NOI18N
        reportsMenu.setMnemonic('R');

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("reportsAction"));
        reportsMenu.add(menuItem);
        menuBar.add(reportsMenu);

        productsMenu.setText(resourceMap.getString("productsMenu.text")); // NOI18N
        productsMenu.setName("productsMenu"); // NOI18N
        productsMenu.setMnemonic('P');

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("productsListAction")); // NOI18N
        productsMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("productCategoriesAction")); // NOI18N
        productsMenu.add(menuItem);

        menuBar.add(productsMenu);

        Separator productSeparator1 = new Separator();
        productsMenu.add(productSeparator1);

        patternMasksItem.setAction(actionMap.get("patternMaskListAction"));
        productsMenu.add(patternMasksItem);

        menuBar.add(productsMenu);


        assemblingMenu.setText(resourceMap.getString("assemblingMenu.text")); // NOI18N
        assemblingMenu.setName("assemblingMenu"); // NOI18N
        assemblingMenu.setMnemonic('A');

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("productAssemblerAction")); // NOI18N
        assemblingMenu.add(menuItem);

        assemblingMenu.add(new JSeparator());

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("assemblingSchemasAction")); // NOI18N
        assemblingMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("assemblingCategoriesAction")); // NOI18N
        assemblingMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("algorithmsAction")); // NOI18N
        assemblingMenu.add(menuItem);

        menuBar.add(assemblingMenu);

        /* Classifiers menu item */

        classifiersMenu.setName("classifiersMenu");
        classifiersMenu.setText(resourceMap.getString("classifiersMenu.text"));

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("classifiersAction"));
        menuItem.setName("classifierMenuItem");
        classifiersMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("classifierGroupsAction"));
        menuItem.setName("classifierGroupsMenuItem");
        classifiersMenu.add(menuItem);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("classifiedObjectsAction"));
        menuItem.setName("classifiedObjectsMenuItem");
        classifiersMenu.add(menuItem);

        menuBar.add(classifiersMenu);

        /* End of classifiers menu item */

        /* Contact book menu items */

        contactBook.setName("contactBookMenu");
        contactBook.setText(resourceMap.getString("contactBookMenu.text"));
        contactBook.setMnemonic('C');

        personsListMenuItem.setAction(actionMap.get("personsListAction"));
        contactBook.add(personsListMenuItem);
        personPositionTypesListMenuItem.setAction(actionMap.get("personPositionTypesListAction"));
        contactBook.add(personPositionTypesListMenuItem);

        Separator contactBookSeparator1 = new Separator();
        contactBook.add(contactBookSeparator1);

        organizationsListMenuItem.setAction(actionMap.get("organizationsListAction"));
        contactBook.add(organizationsListMenuItem);
        organizationPositionTypesListMenuItem.setAction(actionMap.get("organizationPositionTypesListAction"));
        contactBook.add(organizationPositionTypesListMenuItem);
        organizationInternalHierarchyMenuItem.setAction(actionMap.get("organizationInternalHierarchyAction"));
        contactBook.add(organizationInternalHierarchyMenuItem);

        Separator contactBookSeparator2 = new Separator();
        contactBook.add(contactBookSeparator2);

        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("businessPartnersListAction"));
        contactBook.add(menuItem);

        contactBook.add(new Separator());

        citiesListMenuItem.setAction(actionMap.get("citiesListAction"));
        contactBook.add(citiesListMenuItem);
        countriesListMenuItem.setAction(actionMap.get("countriesListAction"));
        contactBook.add(countriesListMenuItem);

        Separator contactBookSeparator3 = new Separator();
        contactBook.add(contactBookSeparator3);

        joinOrganizationMenuItem.setAction(actionMap.get("joinOrganizationAction"));
        contactBook.add(joinOrganizationMenuItem);

        leaveOrganizationMenuItem.setAction(actionMap.get("leaveOrganizationAction"));
        contactBook.add(leaveOrganizationMenuItem);

        Separator contactBookSeparator4 = new Separator();
        contactBook.add(contactBookSeparator4);

        ownOrganizationMenuItem.setAction(actionMap.get("ownOrganizationAction"));
        contactBook.add(ownOrganizationMenuItem);

        menuBar.add(contactBook);

        /* End of contact book menu items */


        /* Admin menu */
        adminMenu.setName("adminMenu");
        adminMenu.setText(resourceMap.getString("adminMenu.text"));
        adminMenu.setMnemonic('D');

        usersListMenuItem.setAction(actionMap.get("usersListAction"));
        adminMenu.add(usersListMenuItem);

        userGroupsMenuItem.setAction(actionMap.get("userGroupsListAction"));
        adminMenu.add(userGroupsMenuItem);

        menuBar.add(adminMenu);
        /* End of admin menu */

        /* Warehouse menu items */
        warehousesMenu.setName("warehousesMenu"); // NOI18N
        warehousesMenu.setText(resourceMap.getString("warehousesMenu.text")); // NOI18N
        warehousesMenu.setMnemonic('W');

        warehousesItem.setAction(actionMap.get("warehouseListAction")); // NOI18N
        warehousesMenu.add(warehousesItem);

        productsTotalsItem.setAction(actionMap.get("productsTotalsAction")); // NOI18N
        warehousesMenu.add(productsTotalsItem);
        //warehouseProductsItem.setAction(actionMap.get("warehouseProductListAction")); // NOI18N
        //warehousesMenu.add(warehouseProductsItem);

        warehousesMenu.add(new JSeparator());

        //menuItem = new JBMenuItem();
        //menuItem.setAction(actionMap.get("deliveryCertificatesAction")); // NOI18N
        //warehousesMenu.add(menuItem);
        JComponent deliveryCertificatesMenu = createDeliveryCertificatesMenu();
        warehousesMenu.add(deliveryCertificatesMenu);


        menuItem = new JBMenuItem();
        menuItem.setAction(actionMap.get("receiptCertificatesAction")); // NOI18N
        warehousesMenu.add(menuItem);

        menuBar.add(warehousesMenu);
        /* End of Warehouse menu items */

        /* Help Menu */
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N
        helpMenu.setMnemonic('H');

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);
         /* End of Help Menu */

        return menuBar;
    }

    // TODO The result of this method must be returned from Server session bean
    private JBToolBar createToolBar()
    {
        JBToolBar toolBar = new JBToolBar();
        toolBar.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        if(GeneralSettings.isDebug()){
            ActionMap actionMap = getActionMap();
            for(Object key : actionMap.allKeys())
            {
                javax.swing.Action action = actionMap.get(key);
                toolBar.add(action);
            }

            return toolBar;
        }

        //toolBar.setBorder(border);
        ActionMap actionMap = getActionMap();

        JButton productsImageButton = new JButton(actionMap.get("productsListAction"));
        productsImageButton.setText("");
        toolBar.add(productsImageButton);

        javax.swing.Action warehousesListAction = actionMap.get("warehouseListAction");
        JButton warehousesImageButton = new JButton(warehousesListAction);
        warehousesImageButton.setText("");
        toolBar.add(warehousesImageButton);

        JButton personsImageButton = new JButton(actionMap.get("personsListAction"));
        personsImageButton.setText("");
        toolBar.add(personsImageButton);

        JButton citiesImageButton = new JButton(actionMap.get("citiesListAction"));
        citiesImageButton.setText("");
        toolBar.add(citiesImageButton);

        JButton countriesImageButton = new JButton(actionMap.get("countriesListAction"));
        countriesImageButton.setText("");
        toolBar.add(countriesImageButton);

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


    /** NOT IMPLEMENTED
     * This method will dynamically populate DeliveryCertificates menu,
     * when implemented.
     * Logged user should be retrieved. Only these warehouses to which the
     * user has access should be listed in the menu.
     * @return
     */
    private JComponent createDeliveryCertificatesMenu(){

        JBMenu menu = new JBMenu();
        ActionMap actionMap = getActionMap();

        menu.setName("DeliveryCertificates");
        menu.setText("DeliveryCertificates(TODO)");

        JBMenuItem warehouse = new JBMenuItem();
        warehouse.setAction(actionMap.get("deliveryCertificatesAction"));
        warehouse.setText("Hardcoded Warehouse");
        warehouse.setActionCommand("warehouse id");

        menu.add(warehouse);
        return menu;
    }

}
