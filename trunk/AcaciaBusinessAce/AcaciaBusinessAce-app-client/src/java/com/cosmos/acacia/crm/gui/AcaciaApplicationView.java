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
import com.cosmos.acacia.data.ui.Widget;
import com.cosmos.acacia.data.ui.Button;
import com.cosmos.acacia.data.ui.Label;
import com.cosmos.acacia.data.ui.Menu;
import com.cosmos.acacia.data.ui.MenuBar;
import com.cosmos.acacia.data.ui.MenuItem;
import com.cosmos.acacia.data.ui.ProgressBar;
import com.cosmos.acacia.data.ui.Separator;
import com.cosmos.acacia.data.ui.StatusBar;
import com.cosmos.acacia.data.ui.SystemAction;
import com.cosmos.acacia.data.ui.ToolBar;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.acacia.gui.entity.EntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBDesktopPane;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBMenu;
import com.cosmos.swingb.JBMenuBar;
import com.cosmos.swingb.JBMenuItem;
import com.cosmos.swingb.JBProgressBar;
import com.cosmos.swingb.JBSeparator;
import com.cosmos.swingb.JBStatusBar;
import com.cosmos.swingb.JBToolBar;
import java.util.Arrays;
import java.util.Set;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
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
        System.out.println("initActions()");
        Set<SecureAction> secureActions = getSession().getSecureActions();
        ResourceMap resourceMap = getResourceMap();
        ApplicationActionMap appActionMap = getActionMap();
        for(SecureAction secureAction : secureActions) {
            System.out.println("secureAction: " + secureAction);
            if(secureAction instanceof EntityAction) {
                try {
                    ApplicationAction action = new EntityApplicationAction(appActionMap, resourceMap, (EntityAction) secureAction);
                    //System.out.println("action: " + action);
                    appActionMap.put(action.getName(), action);
                    System.out.println("appActionMap.get(action.getName()): " + appActionMap.get(action.getName()));
                } catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else if(secureAction instanceof SystemAction) {
            } else {
                throw new UnsupportedOperationException("Unsupported secureAction: " + secureAction);
            }
        }
        //System.out.println("appActionMap.get(\"users\"): " + appActionMap.get("users"));
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

        JBMenuBar jMenuBar = (JBMenuBar) getWidget(menuBar);

        return jMenuBar;
    }

    protected JComponent getWidget(Widget widget) {
        JComponent jComponent;
        if(widget instanceof Menu) {
            jComponent = new JBMenu();
            ((JBMenu) jComponent).setAction(getAction(widget));
        } else if(widget instanceof MenuItem) {
            jComponent = new JBMenuItem();
            ((JBMenuItem) jComponent).setAction(getAction(widget));
        } else if(widget instanceof Separator) {
            jComponent = new JBSeparator();
            ((JBSeparator) jComponent).setOrientation(((Separator) widget).getOrientation());
        } else if(widget instanceof MenuBar) {
            jComponent = new JBMenuBar();
        } else if(widget instanceof ToolBar) {
            jComponent = new JBToolBar();
        } else if(widget instanceof StatusBar) {
            jComponent = new JBStatusBar();
        } else if(widget instanceof Button) {
            jComponent = new JBButton();
            ((JBButton) jComponent).setAction(getAction(widget));
        } else if(widget instanceof Label) {
            jComponent = new JBLabel();
            jComponent.setName(widget.getName());
            initJComponent(jComponent);
        } else if(widget instanceof ProgressBar) {
            jComponent = new JBProgressBar();
            jComponent.setName(widget.getName());
            initJComponent(jComponent);
        } else {
            throw new UnsupportedOperationException("Unsupported menu type: " + widget);
        }
        jComponent.setName(widget.getName());

        for(Widget subWidget : widget.getWidgets()) {
            jComponent.add(getWidget(subWidget));
        }

        return jComponent;
    }

    protected void initJComponent(JComponent jComponent) {
        getResourceMap().injectComponent(jComponent);
    }

    protected Action getAction(Widget menu) {
        String name = menu.getName();
        ResourceMap resourceMap = getResourceMap();
        ApplicationActionMap appActionMap = getActionMap();
        Action action = appActionMap.get(name);
        if("users".equals(name) && action == null) {
            System.out.println("getAction(" + menu + ")");
            System.out.println("menuName=" + name + ", resourceMap=" + resourceMap + ", appActionMap=" + appActionMap);
            System.out.println("action=" + action);
            Object[] keys;
            if((keys = appActionMap.allKeys()) != null) {
                System.out.println("allKeys= " + Arrays.asList(keys));
            }
            if((keys = appActionMap.keys()) != null) {
                System.out.println("keys= " + Arrays.asList(keys));
            }
        }
        if(action == null && menu instanceof Menu) {
            action = new ApplicationAction(appActionMap, resourceMap, name, null, null, null, BlockingScope.NONE);
            System.out.println("new action=" + action);
        }

        return action;
    }

    protected JComponent createStatusBar() {
        StatusBar statusBar = getSession().getStatusBar();
        JBStatusBar jStatusBar = (JBStatusBar) getWidget(statusBar);

        return jStatusBar;
    }

    protected JToolBar createToolBar() {
        ToolBar toolBar = getSession().getToolBar();

        JBToolBar jToolBar = (JBToolBar) getWidget(toolBar);

        return jToolBar;
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
                    entityAction.getName(),
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
