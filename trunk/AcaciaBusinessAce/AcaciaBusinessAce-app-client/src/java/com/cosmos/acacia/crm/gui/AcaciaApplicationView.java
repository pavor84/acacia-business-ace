/*
 * DesktopApplication1View.java
 */
package com.cosmos.acacia.crm.gui;


import javax.ejb.EJB;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.gui.users.LoginForm;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.swingb.JBDesktopPane;
import com.cosmos.swingb.JBMenuBar;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBToolBar;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import org.jdesktop.application.ApplicationAction;
import org.jdesktop.application.ApplicationActionMap;

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
        Set<ApplicationAction> actions = getSession().getApplicationActions();
        ApplicationActionMap appActionMap = getActionMap();
        for(ApplicationAction action : actions) {
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
        JBMenuBar menuBar = new JBMenuBar();

        return menuBar;
    }

    protected JComponent createStatusBar() {
        JBPanel statusPanel = new JBPanel();

        return statusPanel;
    }

    protected JToolBar createToolBar() {
        JBToolBar toolBar = new JBToolBar();

        return toolBar;
    }
}
