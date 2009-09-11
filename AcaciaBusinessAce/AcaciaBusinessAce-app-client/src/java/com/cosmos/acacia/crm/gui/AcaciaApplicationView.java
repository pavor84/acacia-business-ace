/*
 * DesktopApplication1View.java
 */
package com.cosmos.acacia.crm.gui;


import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.data.ui.EntityAction;
import com.cosmos.acacia.data.ui.SecureAction;
import com.cosmos.acacia.crm.gui.users.LoginForm;
import com.cosmos.acacia.data.ui.AbstractMenu;
import com.cosmos.acacia.data.ui.MenuBar;
import com.cosmos.acacia.data.ui.Separator;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.JBDesktopPane;
import com.cosmos.swingb.JBMenu;
import com.cosmos.swingb.JBMenuBar;
import com.cosmos.swingb.JBMenuItem;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBSeparator;
import com.cosmos.swingb.JBToolBar;
import java.util.Arrays;
import java.util.Set;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task.BlockingScope;

/**
 * The application's main frame.
 */
public class AcaciaApplicationView extends FrameView {

    protected static Logger log = Logger.getLogger(AcaciaApplicationView.class);
    //
    @EJB
    private AcaciaSessionRemote acaciaSession;

    public AcaciaApplicationView(SingleFrameApplication app) {
        super(app);
        initActions();
        setLookAndFeel();
    }

    protected void initActions() {
        Set<SecureAction> secureActions = getSession().getSecureActions();
        ResourceMap resourceMap = getResourceMap();
        ApplicationActionMap appActionMap = getActionMap();
        for(SecureAction secureAction : secureActions) {
            ApplicationAction action = null;
            if(secureAction instanceof EntityAction) {
                try {
                    action = new EntityApplicationAction(appActionMap, resourceMap, (EntityAction) secureAction);
                } catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            appActionMap.put(action.getName(), action);
        }
    }

    public void init() {
        //getFrame().setExtendedState(getFrame().getExtendedState() | JFrame.MAXIMIZED_BOTH);
        initComponents();
    }

    private void initComponents() {
        setComponent(createMainComponent());
        setMenuBar(createMenuBar());
        setStatusBar(createStatusBar());
        setToolBar(createToolBar());
    }

    private ApplicationActionMap getActionMap() {
        return getContext().getActionMap(this);
    }

    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            LiquidLookAndFeel.setShowTableGrids(true);
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

    private AcaciaSessionRemote getSession() {
        if (acaciaSession == null) {
            AcaciaSessionRemote.class.getName();
            acaciaSession = AcaciaPanel.getBean(AcaciaSessionRemote.class);
        }

        return acaciaSession;
    }

    public static void login() {
        LoginForm loginForm = new LoginForm();
        loginForm.showFrame();
    }

    protected JComponent createMainComponent() {
        JBDesktopPane desktopPane = new JBDesktopPane();
        desktopPane.setName("desktopPane"); // NOI18N

        return desktopPane;
    }

    protected JMenuBar createMenuBar() {
        MenuBar menuBar = getSession().getMenuBar();

        JBMenuBar jMenuBar = (JBMenuBar) getMenu(menuBar);

        return jMenuBar;
    }

    protected JComponent getMenu(AbstractMenu menu) {
        JComponent jComponent;
        switch(menu.getType()) {
            case Menu:
                jComponent = new JBMenu();
                ((JBMenu) jComponent).setAction(getAction(menu));
                break;

            case MenuItem:
                jComponent = new JBMenuItem();
                ((JBMenuItem) jComponent).setAction(getAction(menu));
                break;

            case Separator:
                jComponent = new JBSeparator();
                ((JBSeparator) jComponent).setOrientation(((Separator) menu).getOrientation());
                break;

            case MenuBar:
                jComponent = new JBMenuBar();
                break;

            default:
                throw new UnsupportedOperationException("Unsupported menu type: " + menu.getType());
        }
        jComponent.setName(menu.getName());

        for(AbstractMenu subMenu : menu.getMenus()) {
            jComponent.add(getMenu(subMenu));
        }

        return jComponent;
    }

    protected Action getAction(AbstractMenu menu) {
        String name = menu.getName();
        ResourceMap resourceMap = getResourceMap();
        ApplicationActionMap appActionMap = getActionMap();
        Action action = appActionMap.get(name);
        if(action == null && AbstractMenu.Type.Menu.equals(menu.getType())) {
            action = new ApplicationAction(appActionMap, resourceMap, name, null, null, null, BlockingScope.NONE);
        }

        return action;
    }

    protected JComponent createStatusBar() {
        JBPanel statusPanel = new JBPanel();

        return statusPanel;
    }

    protected JToolBar createToolBar() {
        JBToolBar toolBar = new JBToolBar();

        return toolBar;
    }

    protected Form getForm(Class entityClass) {
        return (Form) entityClass.getAnnotation(Form.class);
    }

    protected Class<? extends EntityPanel> getEntityFormClass(Class entityClass) {
        Form form;
        if((form = getForm(entityClass)) == null) {
            return null;
        }

        String className;
        if((className = form.entityFormClassName()) == null || className.length() == 0) {
            return null;
        }

        try {
            return (Class<? extends EntityPanel>) Class.forName(className);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Class<? extends EntityListPanel> getEntityListFormClass(Class entityClass) {
        Form form;
        if((form = getForm(entityClass)) == null) {
            return null;
        }

        String className;
        if((className = form.entityListFormClassName()) == null || className.length() == 0) {
            return null;
        }

        try {
            return (Class<? extends EntityListPanel>) Class.forName(className);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public class EntityApplicationAction extends ApplicationAction {

        private Class entityClass;
        private SecureAction.Show show;

        public EntityApplicationAction(
                ApplicationActionMap actionMap,
                ResourceMap resourceMap,
                EntityAction entityAction) throws Exception {
            super(actionMap, resourceMap,
                    entityAction.getActionName(),
                    null,
                    entityAction.getEnabledProperty(),
                    entityAction.getSelectedProperty(),
                    entityAction.getBlock());
            entityClass = entityAction.getEntityClass();
            show = entityAction.getShow();
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            Class<? extends EntityListPanel> entityListPanelClass = getEntityListFormClass(entityClass);
            EntityListPanel listPanel;
            try {
                listPanel = entityListPanelClass.newInstance();
            } catch(Exception ex) {
                throw new RuntimeException(ex);
            }
            if(SecureAction.Show.Dialog.equals(show)) {
                listPanel.showDialog();
            } else {
                listPanel.showFrame();
            }
        }
    }
}
