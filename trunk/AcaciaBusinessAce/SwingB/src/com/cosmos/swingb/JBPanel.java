/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.jdesktop.application.Application;
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
    private ResourceMap resourceMap;
    private String title;
    private boolean resizable = true;


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
        super();
        this.application = application;
    }

    public void showFrame()
    {
        Component parentComponent = null;
        Application app = getApplication();
        if(app != null)
        {
            if(app instanceof SingleFrameApplication)
                parentComponent = ((SingleFrameApplication)app).getMainFrame();
        }

        showFrame(parentComponent);
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
        frame.setVisible(true);
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

    public ResourceMap getResourceMap()
    {
        if(resourceMap == null)
        {
            Application app = getApplication();
            if(app != null)
            {
                resourceMap = app.getContext().getResourceMap(this.getClass());
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

    public void windowClosed(WindowEvent event)
    {
    }


    /*
    public void showForm(Component parentComponent)
    {
        frame = new JBFrame();
        frame.setDefaultCloseOperation(JBFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(getTitle());
        frame.getContentPane().add(this);
        frame.pack();
        frame.setMinimumSize(frame.getPreferredSize());
        frame.setLocationRelativeTo(parentComponent);
        frame.setResizable(isFormResizable());
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent event)
            {
                frame = null;
            }
        });
        frame.setVisible(true);
    }

    public ResponseType showDialog(Component parentComponent)
    {
        selectedValue = null;
        selectedValues = null;
        dialogSelectedAction = null;
        response = null;

        Window window = null;
        if(parentComponent != null)
            window = SwingUtilities.getWindowAncestor(parentComponent);
        dialog = new JBDialog(window, getTitle());

        dialogPanel = new DialogPanel(this, dialog);

        Container contentPane = dialog.getContentPane();
        contentPane.removeAll();
        contentPane.add(dialogPanel, BorderLayout.CENTER);

        Dialog.ModalityType modalityType = getModalityType();
        if(modalityType != null)
        {
            dialog.setModalityType(modalityType);
        }

        dialog.pack();
        dialog.setMinimumSize(dialog.getPreferredSize());
        dialog.setMaximumSize(getMaximumSize());
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setResizable(isFormResizable());

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new DialogWindowAdapter());

        dialog.setVisible(true);
        dialog.dispose();
        dialog = null;

        Action action = getDialogSelectedAction();
        if(action != null)
        {
            response = (ResponseType)action.getValue(PropertyAction.RESOURCE_RESPONSE_TYPE);
            if(response != null)
                return response;
        }

        response = ResponseType.CLOSE;
        return response;
    }
    */
}
