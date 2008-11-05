package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.jdesktop.application.Action;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.app.SessionFacadeRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.reports.CombinedDataSourceObject;
import com.cosmos.acacia.crm.reports.Report;
import com.cosmos.acacia.crm.reports.ReportsUtil;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
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
       return getBean(remoteInterface, true);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> remoteInterface, boolean checkPermissions) {
         try {
             InitialContext ctx = new InitialContext();
             T bean = (T) ctx.lookup(remoteInterface.getName());
             InvocationHandler handler = new RemoteBeanInvocationHandler(bean, checkPermissions);

             T proxy = (T) Proxy.newProxyInstance(AcaciaPanel.class.getClassLoader(),
                 new Class[]{remoteInterface}, handler);

             return proxy;
         } catch (Exception ex){
             ex.printStackTrace();
             return null;
         }
    }

    @SuppressWarnings("unchecked")
    protected <T> T getUnproxiedBean(Class<T> remoteInterface) {
        try {
            InitialContext ctx = new InitialContext();
            T bean = (T) ctx.lookup(remoteInterface.getName());
            return bean;
        } catch (NamingException ex) {
            return null;
        }
    }

     static class RemoteBeanInvocationHandler<E> implements InvocationHandler {

        private E bean;
        private SessionFacadeRemote sessionFacade;
        private boolean checkPermissions = true;

        public RemoteBeanInvocationHandler(E bean, boolean checkPermissions) {
            this.bean = bean;
            this.checkPermissions = checkPermissions;
            try {
                sessionFacade = InitialContext.doLookup(SessionFacadeRemote.class.getName());
            } catch (NamingException ex){
                log.error("", ex);
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("Method called: " + method.getName() + " on bean: " + bean);
            return sessionFacade.call(
                    bean,
                    method.getName(),
                    args,
                    method.getParameterTypes(),
                    AcaciaApplication.getSessionId(), checkPermissions);
        }


    }

    public static AcaciaSessionRemote getAcaciaSession() {
        return LocalSession.instance();
    }

    @Override
    protected void handleException(String message, Throwable ex)
    {
        if(ex instanceof ValidationException)
        {
            logException(message, ex);
            handleBusinessException(message, (ValidationException)ex);
        }
        else
        {
            super.handleException(message, ex);
        }
    }

    public void handleBusinessException(Exception ex) {
        handleBusinessException(null, ex);
    }

    public void handleBusinessException(String message, Exception ex) {
        log.error(message, ex);
        ValidationException ve = extractValidationException(ex);
        if (ve != null) {
            JOptionPane.showMessageDialog(AcaciaPanel.this, getResourceMap().getString(ve.getMessage()));
        }
    }

    public void handleValidationException(Exception ex)
    {
        handleValidationException(null, ex);
    }

    public void handleValidationException(String message, Exception ex){
        log.error(message, ex);
        ValidationException ve = extractValidationException(ex);
        if ( ve!=null ){
            String errorMessage = getValidationErrorsMessage(ve);
            JOptionPane.showConfirmDialog(this.getParent(),
                errorMessage,
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
        return LocalSession.instance().getBranch();
    }

    /**
     * Override this method in order to specify a single report for this form
     * @return the report name (without .jrxml)
     */
    protected Report getReport() {
        return null;
    }

    /**
     * Override this method in order to specify multiple reports for this form.
     * @return the report names (without .jrxml)
     */
    protected Set<Report> getReports() {
        return new LinkedHashSet<Report>();
    }

    protected final Report determineReport() {
        Report report = getReport();

        if (report == null && getReports().size() > 0) {
            String[] reportNames = new String[getReports().size()];
            String[] reportOptions = new String[getReports().size()];
            Report[] reports = new Report[getReports().size()];
            int i = 0;
            for (Report cReport : getReports()) {
                reportOptions[i] = cReport.getLocalizationKey();
                reports[i] = cReport;
                i++;
            }
            int reportChoice = JOptionPane.showOptionDialog(this,
                    getResourceMap().getString("choose.report"),
                    getResourceMap().getString("choose.report"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    reportOptions,
                    reportOptions[0]);

            report = reports[reportChoice];
        }

        return report;
    }

    protected final JasperReport loadReport(Report report) {
        if (report == null)
            return null;

        String reportName = report.getReportName();

        try {
            String resourceName = "/reports/" + reportName + ".jasper";
            log.info("BaseEntityPanel.print().resourceName: " + resourceName);
            InputStream is = this.getClass().getResourceAsStream(resourceName);
            log.info("BaseEntityPanel.print().resourceInputStream: " + is);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

            return jasperReport;
        } catch (JRException ex) {
            handleException(ex);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected final JRDataSource getJasperDataSource(List entities,
            List<Collection> subreports1, List<Collection> subreports2) {

        if ((subreports1 != null && entities.size() != subreports1.size())
                || (subreports2 != null && entities.size() != subreports2.size()))
            throw new RuntimeException("All passed collections must have the same size");

        Iterator<Collection> subreport1Iterator = null;
        if (subreports1 != null)
            subreport1Iterator = subreports1.iterator();

        Iterator<Collection> subreport2Iterator = null;
        if (subreports2 != null)
            subreport2Iterator = subreports2.iterator();

        List<CombinedDataSourceObject> dataList =
            new ArrayList<CombinedDataSourceObject>(entities.size());

        for (Object entity : entities) {
            Collection subreport1 = null;
            if (subreport1Iterator != null)
                subreport1 = subreport1Iterator.next();

            Collection subreport2 = null;
            if (subreport2Iterator != null)
                subreport2 = subreport2Iterator.next();

            CombinedDataSourceObject cdso = new CombinedDataSourceObject();
            cdso.setEntity(entity);
            cdso.setSubreport1(subreport1);
            cdso.setSubreport2(subreport2);

            dataList.add(cdso);
        }
        return new JRBeanCollectionDataSource(dataList);
    }

    /**
     * Triggers the printing process
     */
    @Action
    @SuppressWarnings("unchecked")
    protected final void print() {
        try {
            Report report = determineReport();
            JasperReport jasperReport = loadReport(report);

            Map<String, Object> params = new HashMap<String, Object>();
            if (report.getAutoSubreport1Class() != null) {
                JasperDesign design = ReportsUtil.createTableReport(report.getAutoSubreport1Class());
                JasperReport subreport1 = JasperCompileManager.compileReport(design);
                params.put("SUBREPORT1", subreport1);
            }
            if (report.getAutoSubreport2Class() != null) {
                JasperDesign design = ReportsUtil.createTableReport(report.getAutoSubreport2Class());
                JasperReport subreport2 = JasperCompileManager.compileReport(design);
                params.put("SUBREPORT2", subreport2);
            }

            JRDataSource ds = getJasperDataSource(getEntities(),
                report.getSubreports1Data(), report.getSubreports2Data());


            ReportsUtil.print(jasperReport, ds, this, getResourceMap(), params);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    /**
     * Override this to specify listing behaviour for reporting
     * @return
     */
    protected List getEntities() {
        return new ArrayList();
    }
}