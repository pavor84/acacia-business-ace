/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.swingb.listeners.NestedFormListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Miro
 */
public class JBPanel
    extends JXPanel
{
    private Application application;
    private ApplicationContext applicationContext;
    private ApplicationActionMap applicationActionMap;
    private ResourceMap resourceMap;
    private String title;
    private boolean resizable = true;
    private Container mainContainer;
    private List selectedValues;
    private DialogResponse response;
    private Dialog.ModalityType modalityType = Dialog.ModalityType.APPLICATION_MODAL;
    private Set<NestedFormListener> nestedFormListeners = new HashSet<NestedFormListener>();
    
    public JBPanel()
    {
        super();
    }

    public JBPanel(Class<? extends Application> applicationClass)
    {
        this(Application.getInstance(applicationClass));
    }

    public JBPanel(Application application)
    {
        this();
        this.application = application;
    }

    protected JFrame getMainFrame()
    {
        JFrame mainFrame = null;
        Application app = getApplication();
        if(app != null)
        {
            if(app instanceof SingleFrameApplication)
                mainFrame = ((SingleFrameApplication)app).getMainFrame();
        }

        return mainFrame;
    }

    public void showFrame()
    {
        showFrame(getMainFrame());
    }

    public void showFrame(Component parentComponent)
    {
        JBFrame frame = new JBFrame();
        frame.setDefaultCloseOperation(JBFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(getTitle());
        frame.getContentPane().add(this);
        frame.pack();
        frame.setMinimumSize(frame.getPreferredSize());
        frame.setLocationRelativeTo(parentComponent);
        frame.setResizable(isResizable());
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent event)
            {
                JBPanel.this.windowClosed(event);
            }
        });
        mainContainer = frame;
        frame.setVisible(true);
    }

    public DialogResponse showDialog()
    {
        return showDialog(getMainFrame());
    }

    public DialogResponse showDialog(Component parentComponent)
    {
        selectedValues = null;
        response = DialogResponse.CLOSE;

        Window window = null;
        if(parentComponent != null)
            window = SwingUtilities.getWindowAncestor(parentComponent);
        JBDialog dialog = new JBDialog(window, getTitle());
        mainContainer = dialog;

        Container contentPane = dialog.getContentPane();
        contentPane.removeAll();
        contentPane.add(this, BorderLayout.CENTER);

        Dialog.ModalityType modalityType = getModalityType();
        if(modalityType != null)
        {
            dialog.setModalityType(modalityType);
        }

        dialog.pack();
        dialog.setMinimumSize(dialog.getPreferredSize());
        dialog.setMaximumSize(getMaximumSize());
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setResizable(isResizable());

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new DialogWindowAdapter());

        dialog.setVisible(true);
        dialog.dispose();
        dialog = null;

        if(response == null)
            response = DialogResponse.CLOSE;

        return response;
    }

    protected DialogResponse getDialogResponse()
    {
        return response;
    }

    protected void setDialogResponse(DialogResponse response)
    {
        this.response = response;
    }

    public List getSelectedValues()
    {
        return selectedValues;
    }

    protected void setSelectedValues(Object[] selectedValues)
    {
        setSelectedValues(Arrays.asList(selectedValues));
    }

    protected void setSelectedValues(List selectedValues)
    {
        this.selectedValues = selectedValues;
    }

    public Object getSelectedValue()
    {
        List values;
        if((values = getSelectedValues()) != null && values.size() > 0)
            return values.get(0);

        return null;
    }

    protected void setSelectedValue(Object selectedValue)
    {
        setSelectedValues(Arrays.asList(selectedValue));
    }


    public void close()
    {
        Container mainContainer = getMainContainer();
        if(mainContainer != null)
        {
            mainContainer.setVisible(false);
            if(mainContainer instanceof Window)
                ((Window)mainContainer).dispose();
            else if(mainContainer instanceof JInternalFrame)
                ((JInternalFrame)mainContainer).dispose();
        }
    }

    public String getTitle()
    {
        if(title == null)
        {
            ResourceMap resource = getResourceMap();
            if(resource != null)
            {
                title = resource.getString("Form.title");
            }
        }

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dialog.ModalityType getModalityType()
    {
        return modalityType;
    }

    public void setModalityType(Dialog.ModalityType modalityType)
    {
        this.modalityType = modalityType;
    }

    public ApplicationContext getContext()
    {
        if(applicationContext == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                applicationContext = app.getContext();
            }
        }

        return applicationContext;
    }

    public ApplicationActionMap getApplicationActionMap()
    {
        if(applicationActionMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                applicationActionMap = context.getActionMap(this);
            }
        }

        return applicationActionMap;
    }

    protected Class getResourceStartClass()
    {
        return this.getClass();
    }

    protected Class getResourceStopClass()
    {
        return this.getClass();
    }

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            ApplicationContext context = getContext();
            if(context != null)
            {
                resourceMap = context.getResourceMap(getResourceStartClass(), getResourceStopClass());
            }
        }

        return resourceMap;
    }

    public void setResourceMap(ResourceMap resourceMap) {
        this.resourceMap = resourceMap;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
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


    public void windowClosed(WindowEvent event)
    {
        mainContainer = null;
    }

    protected void dialogWindowClosing(WindowEvent event)
    {
        JDialog dialog = (JDialog)event.getSource();
        dialog.setVisible(false);
    }

    private class DialogWindowAdapter
        extends WindowAdapter
    {
        public void windowClosing(WindowEvent event)
        {
            dialogWindowClosing(event);
        }
    }
    
    public void addNestedFormListener(NestedFormListener listener)
    {
        nestedFormListeners.add(listener);
    }
    
    public Set<NestedFormListener> getNestedFormListeners()
    {
        return nestedFormListeners;
    }
    /**
     * Asks all listeners to perform their actions and returns 
     * whether all of them allow the nested operation to proceed
     * 
     * @return boolean whether the nested operation can proceed
     */
    public boolean canNestedOperationProceed()
    {
        for (NestedFormListener listener: nestedFormListeners)
        {
            if (listener.actionPerformed() == false)
                return false;
        }
        return true;
    }
}
