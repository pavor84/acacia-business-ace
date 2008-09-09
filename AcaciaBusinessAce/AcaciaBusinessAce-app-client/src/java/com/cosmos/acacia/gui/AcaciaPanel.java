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
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.app.SessionFacadeRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.acacia.security.PermissionsManager;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTable;

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
    private BigInteger organizationDataObjectId;

    private static AcaciaSessionRemote acaciaSession = getBean(AcaciaSessionRemote.class);
    private static PermissionsManager permissionsManager;

    {
        if (permissionsManager == null)
            try {
                permissionsManager =
                    new PermissionsManager(acaciaSession.getDataObjectTypes());
            } catch (Exception ex) {
                //
            }
    }

    private static final String SESSION_BRANCH = "SESSION_BRANCH";
    private static Map<String, Object> sessionCache = new HashMap<String, Object>();

    public AcaciaPanel()
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
        if (getParentDataObjectId() != null)
            return getAcaciaSession().getDataObject(getParentDataObjectId());

        return null;
    }

    public BigInteger getOrganizationDataObjectId() {

        // Quitting in case there is no organization data object

        if (organizationDataObjectId == null)
            try {
                organizationDataObjectId = getAcaciaSession().getOrganization().getId();
            } catch (NullPointerException ex) {
                AcaciaApplication.getApplication().exit();
            }

        if (organizationDataObjectId == null)
            AcaciaApplication.getApplication().exit();

        return organizationDataObjectId;
    }

    protected abstract void initData();

    protected void setFonts()
    {
        //Component[] components = this.getComponents();
        // Get a font from config?
        //Font font = new Font("Tahoma", Font.PLAIN, 11);
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

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> remoteInterface) {
        try {
            InitialContext ctx = new InitialContext();
            T bean = (T) ctx.lookup(remoteInterface.getName());
            InvocationHandler handler = new RemoteBeanInvocationHandler(bean);

            T proxy = (T) Proxy.newProxyInstance(AcaciaPanel.class.getClassLoader(),
                new Class[]{remoteInterface}, handler);

            return proxy;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

     static class RemoteBeanInvocationHandler<E> implements InvocationHandler {

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
            Object result = null;

            if (permissionsManager == null) {
                result = sessionFacade.call(bean, method.getName(), args, method.getParameterTypes(), AcaciaApplication.getSessionId());
            } else {
                boolean isAllowed = permissionsManager.isAllowedPreCall(method, args);

                if (isAllowed) {
                    result = sessionFacade.call(bean, method.getName(), args, method.getParameterTypes(), AcaciaApplication.getSessionId());
                    isAllowed = permissionsManager.isAllowedPostCall(result);

                    if (isAllowed)
                        result = permissionsManager.filterResult(result);
                }

                if (!isAllowed) {
                    return null; // display error message.. or throw exception ?
                }
            }
            return result;
        }


    }

    @SuppressWarnings("unchecked")
    public static <T> T getRemoteBean(Object obj, Class<T> remoteInterface)
    {
        try
        {
            T bean = (T) InitialContext.doLookup(remoteInterface.getName());
            InvocationHandler handler = new RemoteBeanInvocationHandler(bean);

            T proxy = (T) Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
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

    public void handleBusinessException(Exception ex) {
        ValidationException ve = extractValidationException(ex);
        if (ve != null) {
            JOptionPane.showMessageDialog(AcaciaPanel.this, getResourceMap().getString(ve.getMessage()));
        } else {
            log.error("error", ex);
        }
    }

    public void handleValidationException(Exception ex){
        ValidationException ve = extractValidationException(ex);
        if ( ve!=null ){
            String message = getValidationErrorsMessage(ve);
            JOptionPane.showConfirmDialog(this.getParent(),
                message,
                getResourceMap().getString("ValidationException.errorsListFollow"),
                JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        } else{
            ex.printStackTrace();
        }
    }

    /**
     * Gets logged user's branch
     * @return
     */
    public Address getUserBranch() {
        Address branch = (Address) sessionCache.get(SESSION_BRANCH);
        if ( branch == null ){
            branch = getAcaciaSession().getBranch();
            sessionCache.put(SESSION_BRANCH, branch);
        }
        return branch;
    }
}