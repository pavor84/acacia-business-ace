/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.ServerException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.app.SessionContext;
import com.cosmos.acacia.app.SessionFacadeRemote;
import com.cosmos.acacia.crm.bl.impl.WarehouseListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTable;
import javax.swing.JOptionPane;

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
    protected boolean exceptionOccurred = false;
    
    private AcaciaSessionRemote acaciaSession = getBean(AcaciaSessionRemote.class);

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
        log.info("Parent data object id (" + getClass().getName() + ") is: " + (parentDataObjectId != null ? parentDataObjectId.longValue() : "null"));
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
        return getAcaciaSession().getDataObject(getParentDataObjectId());
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
    protected ValidationException extractValidationException(Throwable ex)
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
            {
                e = ((EJBException)e).getCausedByException();
                log.info(e);
            } else
                break;
        }

        return null;
    }

    protected ValidationException extractValidationException(Exception ex) {
        return extractValidationException((Throwable) ex);
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

     class RemoteBeanInvocationHandler<E> implements InvocationHandler {

        private E bean;
        private SessionFacadeRemote sessionFacade;

        public RemoteBeanInvocationHandler(E bean) {
            this.bean = bean;
            try {
                sessionFacade = InitialContext.doLookup(SessionFacadeRemote.class.getName());
            } catch (NamingException ex){
                log.error("", ex);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("Method called: " + method.getName() + " on bean: " + bean);
            try {
                return sessionFacade.call(bean, method.getName(), args, method.getParameterTypes(), AcaciaApplication.getSessionId());
            } catch (Throwable ex) {
                exceptionOccurred = true;
                if (AcaciaPanel.this instanceof BaseEntityPanel || AcaciaPanel.this instanceof AbstractTablePanel)
                    throw ex;

                ValidationException ve = extractValidationException(ex);
                if (ve != null) {
                    JOptionPane.showMessageDialog(AcaciaPanel.this, getResourceMap().getString(ve.getMessage()));
                } else {
                    log.error(ex);
                }
                return null;
            }
        }
    }

    public <T> T getRemoteBean(AcaciaPanel panel, Class<T> remoteInterface)
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

	public AcaciaSessionRemote getAcaciaSession() {
		return acaciaSession;
	}
}