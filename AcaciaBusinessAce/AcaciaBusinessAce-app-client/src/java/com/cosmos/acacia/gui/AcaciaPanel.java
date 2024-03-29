package com.cosmos.acacia.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.util.UUID;
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
import java.util.prefs.Preferences;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;

//import org.apache.log4j.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.bl.impl.EnumResourceRemote;
import com.cosmos.acacia.crm.bl.reports.CombinedDataSourceObject;
import com.cosmos.acacia.crm.bl.reports.Report;
import com.cosmos.acacia.crm.bl.reports.ReportsTools;
import com.cosmos.acacia.crm.bl.reports.ReportsToolsRemote;
import com.cosmos.acacia.crm.bl.security.SecurityServiceRemote;
import com.cosmos.acacia.crm.client.LocalSession;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.enums.DatabaseResource;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.acacia.crm.validation.ValidationMessage;
import com.cosmos.acacia.service.ServiceManager;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTable;
import javax.ejb.EJB;

/**
 *
 * @author miro
 */
public class AcaciaPanel extends JBPanel {
    //protected static Logger log = Logger.getLogger(AcaciaPanel.class);

    @EJB
    private static SecurityServiceRemote securityService;
    @EJB
    private static ClassifiersRemote classifiersManager;
    private UUID parentDataObjectId;
    private DataObjectBean mainDataObject;
    private UUID organizationDataObjectId;
    //
    private EnumResourceRemote enumResourceRemote;
    private Map<Class<? extends DatabaseResource>, List<DbResource>> enumResourcesCache =
            new HashMap<Class<? extends DatabaseResource>, List<DbResource>>();

    public AcaciaPanel() {
    }

    public AcaciaPanel(UUID parentDataObjectId) {
        this();
        this.parentDataObjectId = parentDataObjectId;
    }

    public AcaciaPanel(DataObjectBean mainDataObject) {
        this(mainDataObject != null ? mainDataObject.getParentId() : (UUID) null);
        this.mainDataObject = mainDataObject;
    }

    protected void initData() {
    }

    public UUID getParentDataObjectId() {
        //log.info("Parent data object id (" + getClass().getName() + ") is: " + (parentDataObjectId != null ? parentDataObjectId.longValue() : "null"));
        return parentDataObjectId;
    }

    public void setParentDataObjectId(UUID parentDataObjectId) {
        this.parentDataObjectId = parentDataObjectId;
    }

    public DataObjectBean getMainDataObject() {
        return mainDataObject;
    }

    public void setMainDataObject(DataObjectBean mainDataObject) {
        this.mainDataObject = mainDataObject;
        if(mainDataObject != null) {
            setParentDataObjectId(mainDataObject.getParentId());
        } else {
            setParentDataObjectId(null);
        }
    }

    public DataObject getParentDataObject() {
        if (getParentDataObjectId() != null) {
            return getAcaciaSession().getDataObject(getParentDataObjectId());
        }

        return null;
    }

    public UUID getOrganizationDataObjectId() {

        // Quitting in case there is no organization data object

        if (organizationDataObjectId == null) {
            try {
                organizationDataObjectId = getAcaciaSession().getOrganization().getId();
            } catch (NullPointerException ex) {
                AcaciaApplication.getApplication().exit();
            }
        }

        if (organizationDataObjectId == null) {
            AcaciaApplication.getApplication().exit();
        }

        return organizationDataObjectId;
    }

    protected void setFonts() {
        //Component[] components = this.getComponents();
        // Get a font from config?
        //Font font = new Font("Tahoma", Font.PLAIN, 11);
        //setFontToComponents(components, font);
    }

    protected void setFontToComponents(Component[] components, Font font) {
        for (Component component : components) {
            if (component instanceof Container) {
                setFontToComponents(((Container) component).getComponents(), font);
            }
            if (component instanceof JBTable) {
                ((JBTable) component).getTableHeader().setFont(font);
            }
            if (component instanceof JBPanel) {
                Border border = ((JBPanel) component).getBorder();
                if (border instanceof TitledBorder) {
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
    protected ValidationException extractValidationException(Throwable ex) {
        Throwable e = ex;
        while (e != null) {
            if (e instanceof ValidationException) {
                return (ValidationException) e;
            } else if (e instanceof ServerException || e instanceof RemoteException) {
                e = e.getCause();
            } else if (e instanceof EJBException) {
                e = ((EJBException) e).getCausedByException();
                //log.info(e);
            } else {
                break;
            }
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
    protected String getValidationErrorsMessage(ValidationException ve) {
        String errorMessagesHeader = getResourceMap().getString("ValidationException.errorsListFollow");
        StringBuilder msg = new StringBuilder();
        if (errorMessagesHeader != null) {
            msg.append(errorMessagesHeader);
        }
        msg.append("\n\n");
        int i = 1;
        for (ValidationMessage validationMessage : ve.getMessages()) {
//            String currentMsg = null;
//            if ( validationMessage.getArguments()!=null )
//                currentMsg = getResourceMap().getString(validationMessage.getMessageKey(), validationMessage.getArguments());
//            else
//                currentMsg = getResourceMap().getString(validationMessage.getMessageKey());
            String currentMsg = getResourceMap().getString(validationMessage.getMessageKey());
            if (validationMessage.getArguments() != null) {
                Object[] params = validationMessage.getArguments();
                for (int index = 0; index < params.length; index++) {
                    currentMsg = currentMsg.replace("{" + index + "}", params[index].toString());
                }
            }


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
            return ServiceManager.getRemoteService(remoteInterface, checkPermissions);
        } catch(NoSuchMethodError error) {
            error.printStackTrace();
            try {
                return (T) InitialContext.doLookup(remoteInterface.getName());
            } catch(NamingException ex) {
                throw new RuntimeException(ex);
            }
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

    public static AcaciaSessionRemote getAcaciaSession() {
        return LocalSession.instance();
    }

    public List<DbResource> getEnumResources(Class<? extends DatabaseResource> enumClass) {
        List<DbResource> result = enumResourcesCache.get(enumClass);
        if (result == null) {
            result = getEnumResourceRemote().getEnumResources(enumClass);
            enumResourcesCache.put(enumClass, result);
        }
        return result;
    }

    @Override
    protected void handleException(String message, Throwable ex) {
        if (ex instanceof ValidationException) {
            logException(message, ex);
            handleBusinessException(message, (ValidationException) ex);
        } else {
            super.handleException(message, ex);
        }
    }

    public void handleBusinessException(Exception ex) {
        handleBusinessException(null, ex);
    }

    public void handleBusinessException(String message, Exception ex) {
        //log.error(message, ex);
        ValidationException ve = extractValidationException(ex);
        if (ve != null) {
            JOptionPane.showMessageDialog(AcaciaPanel.this, getResourceMap().getString(ve.getMessage()));
        }
    }

    public void handleValidationException(Exception ex) {
        handleValidationException(null, ex);
    }

    public void handleValidationException(String message, Exception ex) {
        //log.error(message, ex);
        ValidationException ve = extractValidationException(ex);
        if (ve != null) {
            String errorMessage = getValidationErrorsMessage(ve);
            JOptionPane.showConfirmDialog(this.getParent(),
                    errorMessage,
                    getResourceMap().getString("ValidationException.errorsListFollow"),
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        } else {
            ex.printStackTrace();
        }
    }

    /**
     * Gets logged user's branch
     * @return
     */
    public Address getUserBranch() {
        return getAcaciaSession().getBranch();
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
        if (report == null && getReports().size() == 1) {
            report = getReports().iterator().next();
        }

        if (report == null && getReports().size() > 1) {
            //String[] reportNames = new String[getReports().size()];
            String[] reportOptions = new String[getReports().size()];
            Report[] reports = new Report[getReports().size()];
            int i = 0;
            for (Report cReport : getReports()) {
                reportOptions[i] = getResourceMap().getString(cReport.getLocalizationKey());
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

    @SuppressWarnings("unchecked")
    protected final JRDataSource getJasperDataSource(List entities,
            List<Collection> subreports1, List<Collection> subreports2, Object headerEntity) {

        if ((subreports1 != null && entities.size() != subreports1.size()) || (subreports2 != null && entities.size() != subreports2.size())) {
            throw new RuntimeException("All passed collections must have the same size");
        }

        Iterator<Collection> subreport1Iterator = null;
        if (subreports1 != null) {
            subreport1Iterator = subreports1.iterator();
        }

        Iterator<Collection> subreport2Iterator = null;
        if (subreports2 != null) {
            subreport2Iterator = subreports2.iterator();
        }

        List<CombinedDataSourceObject> dataList =
                new ArrayList<CombinedDataSourceObject>(entities.size());

        for (Object entity : entities) {
            Collection subreport1 = null;
            if (subreport1Iterator != null) {
                subreport1 = subreport1Iterator.next();
            }

            Collection subreport2 = null;
            if (subreport2Iterator != null) {
                subreport2 = subreport2Iterator.next();
            }

            CombinedDataSourceObject cdso = new CombinedDataSourceObject();
            cdso.setEntity(entity);
            cdso.setHeader(headerEntity);
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
            ReportsToolsRemote reportsUtil = getBean(ReportsToolsRemote.class, false);

            Report report = determineReport();
            JasperReport jasperReport = reportsUtil.loadReport(report);

            Map<String, Object> params = new HashMap<String, Object>();
            if (report.getAutoSubreport1Class() != null) {
                EntityProperties entityProps = report.getAutoSubreport1Properties();
                JasperDesign design = reportsUtil.createTableReport(report.getAutoSubreport1Class(), entityProps, true);

                JasperReport subreport1 = JasperCompileManager.compileReport(design);
                params.put("SUBREPORT1", subreport1);
            }

            if (report.getAutoSubreport2Class() != null) {
                EntityProperties entityProps2 = report.getAutoSubreport2Properties();
                JasperDesign design = reportsUtil.createTableReport(report.getAutoSubreport2Class(), entityProps2, true);
                JasperReport subreport2 = JasperCompileManager.compileReport(design);
                params.put("SUBREPORT2", subreport2);
            }

            //Adding additional parameters
            for (String key : report.getParameters().keySet()) {
                params.put(key, report.getParameters().get(key));
            }

            JRDataSource ds = getJasperDataSource(getEntities(),
                    report.getSubreports1Data(), report.getSubreports2Data(), getReportHeader());

            String[] exportTypes = getExportTypes();
            int choice = chooseReportType(exportTypes);
            if (choice != JOptionPane.CLOSED_OPTION) {
                String targetPath = "";
                if (choice != ReportsTools.TYPE_PRINTER) {
                    targetPath = chooseTargetPath();
                }

                if (targetPath != null) {
                    String filename = targetPath + ReportsTools.FS;
                    if (report.getExportFileName() != null) {
                        filename += report.getExportFileName();
                    } else {
                        filename += jasperReport.getName();
                    }

                    boolean success = ReportsTools.print(jasperReport, ds, params, filename, choice);

                    String message = exportTypes[choice];
                    if (choice != ReportsTools.TYPE_PRINTER) {
                        message += ": " + filename;
                    }

                    if (success) {
                        JOptionPane.showMessageDialog(this,
                                getResourceMap().getString("export.successful") +
                                " " + message);
                    }
                }
            }
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    private String[] getExportTypes() {
        ResourceMap resourceMap = getResourceMap();
        String[] exportTypes = new String[4];
        exportTypes[ReportsTools.TYPE_PRINTER] = resourceMap.getString("export.printer");
        exportTypes[ReportsTools.TYPE_PDF] = resourceMap.getString("export.pdf");
        exportTypes[ReportsTools.TYPE_HTML] = resourceMap.getString("export.html");
        exportTypes[ReportsTools.TYPE_XML] = resourceMap.getString("export.xml");

        return exportTypes;
    }

    private int chooseReportType(String[] exportTypes) {
        ResourceMap resourceMap = getResourceMap();
        int choice = JOptionPane.showOptionDialog(this,
                resourceMap.getString("choose.export.type"),
                resourceMap.getString("choose.export.type"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                exportTypes,
                exportTypes[0]);

        return choice;
    }
    private static final String REPORT_PATH = "reportPath";

    private String chooseTargetPath() {
        String lastPath = Preferences.systemRoot().get(
                getAcaciaSession().getUser().getUserName() + REPORT_PATH, null);

        JFileChooser fc = new JFileChooser();
        if (lastPath != null) {
            fc.setCurrentDirectory(new File(lastPath));
        }

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fChoice = fc.showOpenDialog(this);
        if (fChoice == JFileChooser.APPROVE_OPTION) {
            String dir = fc.getSelectedFile().getAbsolutePath();
            Preferences.systemRoot().put(
                    getAcaciaSession().getUser().getUserName() + REPORT_PATH,
                    dir);
            return dir;
        }
        return null;
    }

    /**
     * Override this to specify listing behaviour for reporting
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getEntities() {
        return new ArrayList();
    }

    /**
     * Override this to specify a header entity for reports;
     * that is the entity used for header/footer data of each page
     *
     * @return
     */
    protected Object getReportHeader() {
        return null;
    }

    public EnumResourceRemote getEnumResourceRemote() {
        if(enumResourceRemote == null) {
            enumResourceRemote = getBean(EnumResourceRemote.class);
        }

        return enumResourceRemote;
    }

    protected void showMessageDialog(String messageKey) {
        ResourceMap resourceMap = getResourceMap();
        String message = resourceMap.getString(messageKey);
        JOptionPane.showMessageDialog(this, message);
    }

    protected void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    protected static SecurityServiceRemote getSecurityService() {
        if (securityService == null) {
            securityService = getBean(SecurityServiceRemote.class);
        }

        return securityService;
    }

    protected static ClassifiersRemote getClassifiersManager() {
        if (classifiersManager == null) {
            classifiersManager = getBean(ClassifiersRemote.class);
        }

        return classifiersManager;
    }

    protected static boolean isAdministrator() {
        return getAcaciaSession().isAdministrator();
    }

    protected static boolean isSystemAdministrator() {
        return getAcaciaSession().isSystemAdministrator();
    }

    protected static boolean isOrganizationAdministrator() {
        return getAcaciaSession().isOrganizationAdministrator();
    }

    protected static boolean isBranchAdministrator() {
        return getAcaciaSession().isBranchAdministrator();
    }

    protected static Classifier getClassifier(String classifierCode) {
        return getAcaciaSession().getClassifier(classifierCode);
    }

    public boolean isSystemOrganization(Organization organization) {
        return getAcaciaSession().isSystemOrganization(organization);
    }
}
