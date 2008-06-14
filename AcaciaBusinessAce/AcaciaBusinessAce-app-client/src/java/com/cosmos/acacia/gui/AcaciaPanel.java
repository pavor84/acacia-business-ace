/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import com.cosmos.acacia.app.AcaciaSession;
import com.cosmos.acacia.app.AppSession;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTable;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.ServerException;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;

/**
 *
 * @author miro
 */
public abstract class AcaciaPanel
    extends JBPanel
{

    protected static Logger log = Logger.getLogger(AcaciaPanel.class);

    private BigInteger parentDataObjectId;
    private DataObjectBean mainDataObject;


    AcaciaPanel()
    {
        super(AcaciaApplication.class);
    }

    public AcaciaPanel(BigInteger parentDataObjectId)
    {
        this();
        this.parentDataObjectId = parentDataObjectId;
    }

    public AcaciaPanel(DataObjectBean mainDataObject)
    {
        this(mainDataObject != null ? mainDataObject.getParentId() : (BigInteger)null);
        this.mainDataObject = mainDataObject;
    }

    public BigInteger getParentDataObjectId()
    {
        return parentDataObjectId;
    }

    public void setParentDataObjectId(BigInteger parentDataObjectId)
    {
        this.parentDataObjectId = parentDataObjectId;
    }

    public DataObjectBean getMainDataObject()
    {
        return mainDataObject;
    }

    public void setMainDataObject(DataObjectBean mainDataObject)
    {
        this.mainDataObject = mainDataObject;
    }

    public DataObject getParentDataObject()
    {
        return AppSession.getDataObject(getParentDataObjectId());
    }

    protected abstract void initData();

    protected void setFonts()
    {
        Component[] components = this.getComponents();
        // Get a font from config?
        Font font = new Font("Tahoma", Font.PLAIN, 11);
        //setFontToComponents(components, font);
    }

    protected void setFontToComponents(Component[] components, Font font)
    {
        for (Component component : components)
        {
            if (component instanceof Container)
            {
                setFontToComponents(((Container) component).getComponents(), font);
            }
            if (component instanceof JBTable)
            {
                ((JBTable) component).getTableHeader().setFont(font);
            }
            if (component instanceof JBPanel)
            {
                Border border = ((JBPanel) component).getBorder();
                if (border instanceof TitledBorder)
                {
                    ((TitledBorder) border).setTitleFont(font);
                }
            }
            component.setFont(font);
        }
    }

    /**
     * If {@link ValidationException} is thrown by the EJB, it will be set as some inner 'cause' of
     * an EJB exception. That is way it is a little bit tricky to get it. This method implements this
     * logic by checking if some of the causes for the main exception is actually a {@link ValidationException}
     * @param ex
     * @return - the ValidationException if some 'caused by' exception is {@link ValidationException},
     * null otherwise
     */
    protected ValidationException extractValidationException(Exception ex)
    {
        Throwable e = ex;
        while(e != null)
        {
            if(e instanceof ValidationException)
            {
                return (ValidationException)e;
            }
            else if(e instanceof ServerException || e instanceof RemoteException)
            {
                e = e.getCause();
            }
            else if(e instanceof EJBException)
                e = ((EJBException)e).getCausedByException();
            else
                break;
        }

        return null;
    }

    /**
     * Iterate over all validation messages and compose one string - message per line.
     * @param ve
     * @return
     */
    protected String getValidationErrorsMessage(ValidationException ve)
    {
        String errorMessagesHeader = getResourceMap().getString("ValidationException.errorsListFollow");
        StringBuilder msg = new StringBuilder();
        if ( errorMessagesHeader!=null )
            msg.append(errorMessagesHeader);
        msg.append("\n\n");
        int i = 1;
        for (ValidationMessage validationMessage : ve.getMessages()) {
            String currentMsg = null;
            if ( validationMessage.getArguments()!=null )
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey(), validationMessage.getArguments());
            else
                currentMsg = getResourceMap().getString(validationMessage.getMessageKey());

            msg.append(i).append(": ").append(currentMsg).append("\n\n");
            i++;
        }
        return msg.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class getResourceStopClass() {
        return AcaciaPanel.class;
    }

    public <T> T getBean(Class<T> remoteInterface) {
        try {
            InitialContext ctx = new InitialContext();
            T bean = (T) ctx.lookup(remoteInterface.getName());
            InvocationHandler handler = new RemoteBeanInvocationHandler(bean);

            T proxy = (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{remoteInterface}, handler);

            return proxy;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    static class RemoteBeanInvocationHandler<E> implements InvocationHandler {

        private E bean;

        public RemoteBeanInvocationHandler(E bean) {
            this.bean = bean;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            AcaciaSession session = AppSession.get();

            log.info("Method called: " + method.getName());

            return method.invoke(bean, args);
        }
    }

    public static <T> T getRemoteBean(AcaciaPanel panel, Class<T> remoteInterface)
    {
        try
        {
            T bean = (T)InitialContext.doLookup(remoteInterface.getName());
            InvocationHandler handler = new RemoteBeanInvocationHandler(bean);

            T proxy = (T)Proxy.newProxyInstance(
                panel.getClass().getClassLoader(),
                new Class[]{remoteInterface},
                handler);

            return proxy;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

}