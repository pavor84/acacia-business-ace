package com.cosmos.acacia.crm.reports;

import java.awt.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.beansbinding.BeansBindingHelper;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;

@SuppressWarnings("unchecked")
public class ReportsUtil {

    protected static Logger log = Logger.getLogger(ReportsUtil.class);

    protected static final String FS = System.getProperty("file.separator");
    protected static final int TYPE_PRINTER = 0;
    protected static final int TYPE_PDF = 1;
    protected static final int TYPE_HTML = 2;
    protected static final int TYPE_XML = 3;

    protected static final String SUBREPORT_DIR = "reports/";

    public static void print(JasperReport jasperReport,
            JRDataSource ds,
            Component uiComponent,
            ResourceMap resourceMap,
            Map paramsMap)
    {
        try {
            Map params = new HashMap();
            params.put("REPORTS_DIR", "reports");
            params.putAll(paramsMap);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, params, ds);

            String[] exportTypes = new String[4];
            exportTypes[TYPE_PRINTER] = resourceMap.getString("export.printer");
            exportTypes[TYPE_PDF] = resourceMap.getString("export.pdf");
            exportTypes[TYPE_HTML] = resourceMap.getString("export.html");
            exportTypes[TYPE_XML] = resourceMap.getString("export.xml");

            int choice = JOptionPane.showOptionDialog(uiComponent,
                        resourceMap.getString("choose.export.type"),
                        resourceMap.getString("choose.export.type"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        exportTypes,
                        exportTypes[0]);

            String message = exportTypes[choice];
            boolean success = true;

            if (choice == TYPE_PRINTER) {
                JasperPrintManager.printReport(jasperPrint, true);
            } else {
                String targetPath = chooseTargetPath(uiComponent);
                String filename = targetPath + FS + jasperReport.getName();
                if (targetPath != null) {
                    if (choice == TYPE_PDF) {
                        filename += ".pdf";;
                        JasperExportManager.exportReportToPdfFile(
                            jasperPrint, filename);
                    }
                    if (choice == TYPE_HTML) {
                        filename += ".html";
                        JasperExportManager.exportReportToHtmlFile(
                            jasperPrint, filename);
                    }
                    if (choice == TYPE_XML) {
                        filename += ".xml";
                        JasperExportManager.exportReportToXmlFile(
                            jasperPrint, filename, false);
                    }
                    message += ": " + filename;
                } else {
                    success = false;
                }
            }

            if (success) {
                JOptionPane.showMessageDialog(uiComponent,
                    resourceMap.getString("export.successful") +
                    " " + message);
            }
        } catch (Exception ex) {
            log.error("Error printing", ex);
        }
    }

    public static JasperDesign createTableReport(Class entityClass) throws JRException {
        String underscoreSeparatedName = "";
        String className = entityClass.getSimpleName();
        String prefix = "";
        for (int i = 0; i < className.length(); i ++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c)) {
                underscoreSeparatedName += prefix + Character.toLowerCase(c);
            } else {
                underscoreSeparatedName += c;
            }
            prefix = "_";
        }
        underscoreSeparatedName = pluralize(underscoreSeparatedName);
        return createTableReport(entityClass, underscoreSeparatedName);
    }

    public static JasperDesign createTableReport(Class entityClass,
            String reportName) throws JRException {

        EntityProperties entityProps =
            BeansBindingHelper.createEntityProperties(entityClass, true);

        // Initialization
        JasperDesign design = new JasperDesign();
        design.setName(reportName);
        design.setColumnCount(entityProps.getKeys().size());
        design.setPrintOrder(JasperDesign.PRINT_ORDER_VERTICAL);
        design.setOrientation(JasperDesign.ORIENTATION_PORTRAIT);
        design.setPageWidth(595);
        design.setPageHeight(842);
        design.setColumnSpacing(0);
        design.setLeftMargin(20);
        design.setRightMargin(20);
        design.setTopMargin(20);
        design.setBottomMargin(20);
        design.setColumnWidth((design.getPageWidth()
                - (design.getLeftMargin() + design.getRightMargin()))
                / design.getColumnCount());
        design.setWhenNoDataType(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES);
        design.setTitleNewPage(false);
        design.setSummaryNewPage(false);
        design.setResourceBundle(SUBREPORT_DIR + reportName);

        // iReport properties, in case manual editing is required
        design.setProperty("ireport.scriptlethandling", "2");
        design.setProperty("ireport.encoding", "UTF-8");

        // Required imports
        design.addImport("java.util.*");
        design.addImport("net.sf.jasperreports.engine.*");
        design.addImport("net.sf.jasperreports.engine.data.*");

        // Fields
        for (PropertyDetails pd : entityProps.getValues()) {
            JRDesignField field = new JRDesignField();
            String fieldName = "";
            if (pd.getCustomDisplay() != null) {
                // Removing EL
                fieldName = pd.getCustomDisplay().replace("${", "");
                fieldName = fieldName.replace("}", "");
            } else {
                fieldName = pd.getPropertyName();
            }
            field.setName(fieldName);
            Class fieldClass = null;
            // Checking whether the property is a bean =>
            // transformed to string by customDisplay
            if (DataObjectBean.class.isAssignableFrom(pd.getPropertyClass())) {

                fieldClass = String.class;
            } else {
                fieldClass = pd.getPropertyClass();
            }
            field.setValueClass(fieldClass);
            design.addField(field);
        }

        // Bands TODO: widths modifications

        JRDesignBand columnHeader = new JRDesignBand();
        columnHeader.setHeight(24);

        JRDesignBand details = new JRDesignBand();
        details.setHeight(24);

        int i = 0;
        for (PropertyDetails pd : entityProps.getValues()) {
            JRDesignTextField caption = new JRDesignTextField();
            JRDesignExpression expr = new JRDesignExpression();
            expr.setValueClass(String.class);
            expr.setText("$R{" + pd.getPropertyName() + "}");
            caption.setExpression(expr);
            caption.setBold(true);
            caption.setX(i * design.getColumnWidth());
            caption.setWidth(design.getColumnWidth());
            caption.setHeight(columnHeader.getHeight());
            columnHeader.addElement(caption);
            i++;
        }
        design.setColumnHeader(columnHeader);
        i = 0;
        for (JRField field : design.getFields()) {
            JRDesignTextField element = new JRDesignTextField();
            JRDesignExpression expr = new JRDesignExpression();

            if (Arrays.binarySearch(getTextFieldClassNames(),
                    field.getValueClass().getName()) < 0) {
                expr.setValueClass(String.class);
                expr.setText("$F{" + field.getName() + "}.toString()");

                // Handling enums: TODO: improve
                if (DbResource.class.isAssignableFrom(field.getValueClass()))
                    expr.setText("$F{" + field.getName() + "}.getEnumValue().toString()");

            } else {
                expr.setText("$F{" + field.getName() + "}");
                expr.setValueClass(field.getValueClass());
            }

            element.setExpression(expr);
            element.setBlankWhenNull(true);
            element.setY(0);
            element.setX(i * design.getColumnWidth());
            element.setWidth(design.getColumnWidth());
            element.setHeight(details.getHeight());
            element.setKey("textField-" + (i+1));
            details.addElement(element);
            i++;
        }
        design.setDetail(details);

        return design;
    }

    private static String chooseTargetPath(Component uiComponent) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fChoice = fc.showOpenDialog(uiComponent);
        if (fChoice == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private static String pluralize(String str) {
        String result = str;
        if (result.endsWith("s") || result.endsWith("sh") || result.endsWith("ch"))
            result += "es";
        else
            result += "s";

        return result;
    }

    // Copied from JRValidator (private there)

    private static String[] textFieldClassNames = null;

    private static String[] getTextFieldClassNames() {
        if(textFieldClassNames == null)
        {
            textFieldClassNames = (new String[] {
                (java.lang.Boolean.class).getName(), (java.lang.Byte.class).getName(), (java.util.Date.class).getName(), (java.sql.Timestamp.class).getName(), (java.sql.Time.class).getName(), (java.lang.Double.class).getName(), (java.lang.Float.class).getName(), (java.lang.Integer.class).getName(), (java.lang.Long.class).getName(), (java.lang.Short.class).getName(),
                (java.math.BigDecimal.class).getName(), (java.lang.Number.class).getName(), (java.lang.String.class).getName()
            });
            Arrays.sort(textFieldClassNames);
        }
        return textFieldClassNames;
    }
}
