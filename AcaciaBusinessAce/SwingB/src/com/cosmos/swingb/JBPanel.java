/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import com.cosmos.swingb.listeners.NestedFormListener;
import com.cosmos.swingb.menus.JBContextMenuCreaetor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jdesktop.swingx.error.ErrorReporter;

/**
 *
 * @author Miro
 */
public class JBPanel
        extends JXPanel {

    private static final Logger logger = Logger.getLogger(JBPanel.class.getName());
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private String title;
    private boolean resizable = true;
    private Container mainContainer;
    private List selectedValues;
    private DialogResponse response;
    private int windowAncestorCount;
    private Dialog.ModalityType modalityType = Dialog.ModalityType.APPLICATION_MODAL;
    private Set<NestedFormListener> nestedFormListeners = new HashSet<NestedFormListener>();
    protected JBDialog dialog;

    public JBPanel() {
    }

    public JBPanel(LayoutManager layoutManager) {
        super(layoutManager);
    }

    protected JFrame getMainFrame() {
        JFrame mainFrame = null;
        Application app = getApplication();
        if (app != null) {
            if (app instanceof SingleFrameApplication) {
                mainFrame = ((SingleFrameApplication) app).getMainFrame();
            }
        }

        return mainFrame;
    }

    public void showFrame() {
        showFrame(getMainFrame());
    }

    public void showFrame(Component parentComponent) {
        addContextMenus();
        JBFrame frame = new JBFrame();
        frame.getContentPane().add(this);
        frame.pack();
        frame.setMinimumSize(frame.getPreferredSize());

        Dimension maximumSize = getMaximumSize();
        Dimension preferredSize = getPreferredSize();
        maximumSize.width = Math.min(maximumSize.width, preferredSize.width);
        maximumSize.height = Math.min(maximumSize.height, preferredSize.height);
        if (!maximumSize.equals(preferredSize)) {
            setPreferredSize(maximumSize);
            setSize(maximumSize);
            maximumSize = frame.getPreferredSize();
            preferredSize = Toolkit.getDefaultToolkit().getScreenSize();
            maximumSize.width = Math.min(maximumSize.width, preferredSize.width);
            maximumSize.height = Math.min(maximumSize.height, preferredSize.height);

            frame.dispose();
            frame.getContentPane().remove(this);

            frame = new JBFrame();
            frame.getContentPane().add(this);
            frame.setPreferredSize(maximumSize);
            frame.pack();
            frame.setMinimumSize(frame.getPreferredSize());
        }

        frame.setDefaultCloseOperation(JBFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(getTitle());
        frame.setLocationRelativeTo(parentComponent);
        frame.setResizable(isResizable());
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent event) {
                JBPanel.this.windowClosed(event);
            }
        });
        mainContainer = frame;
        frame.setVisible(true);
    }

    public DialogResponse showDialog() {
        return showDialog(getMainFrame());
    }

    public DialogResponse showDialog(Component parentComponent) {
        addContextMenus();
        selectedValues = null;
        response = DialogResponse.CLOSE;

        Window window = getWindowAncestor(parentComponent);
        dialog = new JBDialog(window, getTitle());
        Container contentPane = dialog.getContentPane();
        contentPane.removeAll();
        contentPane.add(this, BorderLayout.CENTER);
        dialog.pack();
        dialog.setMinimumSize(dialog.getPreferredSize());

        Dimension maximumSize = getMaximumSize();
        Dimension preferredSize = getPreferredSize();
        maximumSize.width = Math.min(maximumSize.width, preferredSize.width);
        maximumSize.height = Math.min(maximumSize.height, preferredSize.height);
        if (!maximumSize.equals(preferredSize)) {
            setPreferredSize(maximumSize);
            setSize(maximumSize);
            maximumSize = dialog.getPreferredSize();
            preferredSize = Toolkit.getDefaultToolkit().getScreenSize();
            maximumSize.width = Math.min(maximumSize.width, preferredSize.width);
            maximumSize.height = Math.min(maximumSize.height, preferredSize.height);

            dialog.dispose();
            dialog.getContentPane().remove(this);
            dialog = null;

            dialog = new JBDialog(window, getTitle());
            dialog.getContentPane().add(this);
            dialog.setPreferredSize(maximumSize);
            dialog.pack();
            dialog.setMinimumSize(dialog.getPreferredSize());
        }

        Dialog.ModalityType modality = getModalityType();
        if (modality != null) {
            dialog.setModalityType(modality);
        }
        dialog.setLocationRelativeTo(parentComponent);
        mainContainer = dialog;
        dialog.setResizable(isResizable());
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new DialogWindowAdapter());
        dialog.setVisible(true);
        dialog.dispose();
        dialog = null;

        if (response == null) {
            response = DialogResponse.CLOSE;
        }

        return response;
    }

    protected void addContextMenus() {
        Component[] components = this.getComponents();

        for (Component component : components) {
            if (component instanceof JTextComponent) {
                component.addMouseListener(new JBContextMenuCreaetor());
            }
        }
    }

    protected Window getWindowAncestor(Component parentComponent) {
        Window window = null;
        if (parentComponent != null) {
            int counter = getWindowAncestorCount();
            do {
                window = SwingUtilities.getWindowAncestor(parentComponent);
                if (window == null && parentComponent instanceof Window) {
                    return (Window) parentComponent;
                }
                parentComponent = window;
            } while (counter-- > 0 && window != null);
        }

        if (window == null && parentComponent instanceof Window) {
            return (Window) parentComponent;
        }

        return window;
    }

    protected DialogResponse getDialogResponse() {
        return response;
    }

    protected void setDialogResponse(DialogResponse response) {
        this.response = response;
    }

    public List getSelectedValues() {
        return selectedValues;
    }

    protected void setSelectedValues(Object[] selectedValues) {
        setSelectedValues(Arrays.asList(selectedValues));
    }

    protected void setSelectedValues(List selectedValues) {
        this.selectedValues = selectedValues;
    }

    public Object getSelectedValue() {
        List values;
        if ((values = getSelectedValues()) != null && values.size() > 0) {
            return values.get(0);
        }

        return null;
    }

    protected void setSelectedValue(Object selectedValue) {
        setSelectedValues(Arrays.asList(selectedValue));
    }

    public void close() {
        Container container = getMainContainer();
        if (container != null) {
            container.setVisible(false);
            if (container instanceof Window) {
                ((Window) container).dispose();
            } else if (container instanceof JInternalFrame) {
                ((JInternalFrame) container).dispose();
            }
        }
    }

    public String getTitle() {
        if (title == null) {
            ResourceMap resource = getResourceMap();
            if (resource != null) {
                title = resource.getString("Form.title");
            }
        }

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dialog.ModalityType getModalityType() {
        return modalityType;
    }

    public void setModalityType(Dialog.ModalityType modalityType) {
        this.modalityType = modalityType;
    }

    public int getWindowAncestorCount() {
        return windowAncestorCount;
    }

    public void setWindowAncestorCount(int windowAncestorCount) {
        this.windowAncestorCount = windowAncestorCount;
    }

    public ApplicationContext getContext() {
        if (applicationContext == null) {
            Application app = getApplication();
            if (app != null) {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public Class getActionsClass() {
        return this.getClass();
    }

    public Object getActionsObject() {
        return this;
    }

    public ApplicationActionMap getApplicationActionMap() {
        if (applicationActionMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                applicationActionMap = context.getActionMap(getActionsClass(), getActionsObject());
            }
        }

        return applicationActionMap;
    }

    protected Class getResourceStartClass() {
        return this.getClass();
    }

    protected Class getResourceStopClass() {
        return this.getClass();
    }

    public ResourceMap getResourceMap() {
        if (resourceMap == null) {
            ApplicationContext context = getContext();
            if (context != null) {
                resourceMap = context.getResourceMap(getResourceStartClass(), getResourceStopClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        if (application == null) {
            application = Application.getInstance();
        }

        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    protected String getResourceString(String key) {
        return getResourceMap().getString(key);
    }

    protected Icon getResourceIcon(String key) {
        return getResourceMap().getIcon(key);
    }

    protected Boolean getResourceBoolean(String key) {
        return getResourceMap().getBoolean(key);
    }

    protected Integer getResourceInteger(String key) {
        return getResourceMap().getInteger(key);
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public Container getMainContainer() {
        return mainContainer;
    }

    public void windowClosed(WindowEvent event) {
        mainContainer = null;
    }

    protected void dialogWindowClosing(WindowEvent event) {
        JDialog sourceDialog = (JDialog) event.getSource();
        sourceDialog.setVisible(false);
    }

    private class DialogWindowAdapter
            extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent event) {
            dialogWindowClosing(event);
        }
    }

    public void addNestedFormListener(NestedFormListener listener) {
        nestedFormListeners.add(listener);
    }

    public Set<NestedFormListener> getNestedFormListeners() {
        return nestedFormListeners;
    }

    /**
     * Asks all listeners to perform their actions and returns
     * whether all of them allow the nested operation to proceed
     *
     * @return boolean whether the nested operation can proceed
     */
    public boolean canNestedOperationProceed() {
        for (NestedFormListener listener : nestedFormListeners) {
            if (listener.actionPerformed() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * getErrorTitle() is used as a quick reference for the
     * error (for example, it might be used as the
     * title of an error dialog or as the subject of
     * an email message).
     *  @return May return null.
     */
    protected String getErrorTitle(Throwable throwable) {
        return getResourceMap().getString("error.title");
    }

    /**
     * getBasicErrorMessage() is short description of the problem.
     *  @return May return null.
     */
    protected String getBasicErrorMessage(Throwable throwable) {
        if (throwable == null) {
            return getResourceMap().getString("exception.null");
        }

        Throwable ex = throwable;
        String message;
        while (ex != null) {
            if ((message = throwable.getMessage()) != null && (message = message.trim()).length() > 0) {
                return message;
            }

            ex = ex.getCause();
        }

        return throwable.getClass().getName();
    }

    /**
     * getDetailedErrorMessage() is full description of the problem. It is recommended,
     * though not required, that this String contain HTML
     * to improve the look and layout of the detailed
     * error message.
     * @return May return null.
     */
    protected String getDetailedErrorMessage(Throwable throwable) {
        if (throwable == null) {
            return getResourceMap().getString("exception.null");
        }

        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        throwable.printStackTrace(out);
        out.flush();
        return writer.toString();
    }

    /**
     * getErrorCategory() is a category name, indicating where in the application
     * this incident occurred. It is recommended that
     * this be the same value as you would use when logging.
     * @return May return null.
     */
    protected String getErrorCategory(Throwable throwable) {
        return getResourceMap().getString("error.category");
    }

    /**
     * getErrorLevel() is any Level (Level.SEVERE, Level.WARNING, etc).
     * If null, then the level will be set to SEVERE.
     */
    protected Level getErrorLevel(Throwable throwable) {
        return Level.SEVERE;
    }

    /**
     * getErrorState() is the state of the application at the time the incident occured.
     * The standard System properties are automatically added to this
     * state, and thus do not need to be included. This value may be null.
     * If null, the resulting map will contain only the System properties.
     * If there is a value in the map with a key that also occurs in the
     * System properties (for example: sun.java2d.noddraw), then the
     * developer supplied value will be used. In other words, defined
     * parameters override standard ones. In addition, the keys
     * "System.currentTimeMillis" and "isOnEDT" are both defined
     * automatically.
     */
    protected Map<String, String> getErrorState(Throwable throwable) {
        return null;
    }

    /**
     * @param throwable <code>Throwable</code> that can be used as a
     * source for additional information such as call
     * stack, thread name, etc. May be null.
     */
    protected ErrorInfo getErrorInfo(Throwable throwable) {
        return new ErrorInfo(
                getErrorTitle(throwable),
                getBasicErrorMessage(throwable),
                getDetailedErrorMessage(throwable),
                getErrorCategory(throwable),
                throwable,
                getErrorLevel(throwable),
                getErrorState(throwable));
    }

    protected JXErrorPane getErrorPane(ErrorInfo errorInfo) {
        return getErrorPane(errorInfo, null);
    }

    protected JXErrorPane getErrorPane(
            ErrorInfo errorInfo,
            ErrorReporter errorReporter) {
        JBErrorPane errorPane = new JBErrorPane();
        errorPane.setErrorInfo(errorInfo);
        if (errorReporter != null) {
            errorPane.setErrorReporter(errorReporter);
        }
        return errorPane;
    }

    protected final void handleException(Throwable ex) {
        handleException(null, ex);
    }

    protected void handleException(String message, Throwable ex) {
        ErrorInfo errorInfo = getErrorInfo(ex);
        logException(errorInfo.getErrorLevel(), message, ex);
        JBErrorPane.showDialog(this, getErrorPane(errorInfo));
    }

    protected void logException(String message, Throwable ex) {
        logException(null, message, ex);
    }

    protected void logException(Level level, String message, Throwable ex) {
        if (level == null) {
            level = Level.SEVERE;
        }
        logger.log(level, message, ex);
    }
}
