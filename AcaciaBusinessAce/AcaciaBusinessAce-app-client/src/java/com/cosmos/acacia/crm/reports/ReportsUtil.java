package com.cosmos.acacia.crm.reports;

import java.awt.Component;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.PdfFont;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.beansbinding.BeansBindingHelper;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.resource.BeanResource;

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
                        filename += ".pdf";

                        JRPdfExporter exporter = new JRPdfExporter();

//                        Map fontMap = new HashMap();
//
//                        fontMap.put(new FontKey("Helvetica", false, false),
//                         new PdfFont("Helvetica", "Identity-H", false));
//
//                        log.info("SIZE : " + ((Map) exporter.getParameter(JRExporterParameter.FONT_MAP)).size());
                        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
//                        exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
                        exporter.exportReport();

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
        return createTableReport(entityClass, false);
    }

    public static JasperDesign createTableReport(Class entityClass, boolean isSubreport) throws JRException {
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
        return createTableReport(entityClass, underscoreSeparatedName, isSubreport);
    }

    public static JasperDesign createTableReport(Class entityClass,
            String reportName, boolean isSubreport) throws JRException {

        EntityProperties entityProps =
            BeansBindingHelper.createEntityProperties(entityClass, true);

        if (entityProps.getKeys().size() == 0)
            return null;

        //TODO Add first column - id of item (1,2,3, etc)

        // Initialization
        JasperDesign design = new JasperDesign();
        int columnCount = entityProps.getValues().size();

        design.setName(reportName);
        design.setColumnCount(1); //columnCount
        design.setColumnSpacing(0);
        design.setPrintOrder(JasperDesign.PRINT_ORDER_VERTICAL);
        design.setOrientation(JasperDesign.ORIENTATION_PORTRAIT);
        design.setPageWidth(595);
        design.setPageHeight(842);
        design.setColumnSpacing(0);
        design.setLeftMargin(20);
        design.setRightMargin(20);
        int topAndBottomMargin = 20;
        if (isSubreport)
            topAndBottomMargin = 0;
        design.setTopMargin(topAndBottomMargin);
        design.setBottomMargin(topAndBottomMargin);
        design.setWhenNoDataType(JasperDesign.WHEN_NO_DATA_TYPE_NO_PAGES);
        design.setTitleNewPage(false);
        design.setSummaryNewPage(false);
        design.setResourceBundle(SUBREPORT_DIR + reportName);

        int reportWidth = design.getPageWidth()
        - (design.getLeftMargin() + design.getRightMargin() + columnCount + 1);

        int reportWidthWithLines = reportWidth + columnCount + 1;
        design.setColumnWidth(reportWidthWithLines);

        // iReport properties, in case manual editing is required
        design.setProperty("ireport.scriptlethandling", "2");
        design.setProperty("ireport.encoding", "UTF-8");

        // Required imports
        design.addImport("java.util.*");
        design.addImport("net.sf.jasperreports.engine.*");
        design.addImport("net.sf.jasperreports.engine.data.*");
        design.addImport("com.cosmos.acacia.crm.reports.*");

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

        JRDesignBand columnHeader = new JRDesignBand();
        columnHeader.setHeight(15);

        JRDesignBand details = new JRDesignBand();
        details.setHeight(15);
        List<Integer> columnWidths = new ArrayList<Integer>(entityProps.getValues().size());

        JRDesignLine firstLine = new JRDesignLine();
        firstLine.setHeight(columnHeader.getHeight());
        firstLine.setWidth(0); // vertical
        firstLine.setDirection(JRLine.DIRECTION_BOTTOM_UP);
        firstLine.setX(0);
        columnHeader.addElement(firstLine);
        int nextX = 1;
        int i = 0;
        for (PropertyDetails pd : entityProps.getValues()) {
            JRDesignTextField caption = new JRDesignTextField();
            JRDesignExpression expr = new JRDesignExpression();
            expr.setValueClass(String.class);
            expr.setText("$R{" + pd.getPropertyName() + "}");
            caption.setExpression(expr);
            caption.setBold(true);
            int columnWidth = reportWidth * pd.getReportColumnWidth() / 100;
            columnWidths.add(columnWidth);

            caption.setWidth(columnWidth);
            caption.setHeight(columnHeader.getHeight() - 2);
            caption.setX(nextX);
            nextX += columnWidth;

            //If last element, add the width lost due to integer division
            int diff = 0;
            if (i + 1 == columnCount) {
                diff = reportWidthWithLines - nextX - 1;
                caption.setWidth(caption.getWidth() + diff);
            }

            columnHeader.addElement(caption);

            //Adding a vertical line
            JRDesignLine line = new JRDesignLine();
            line.setHeight(columnHeader.getHeight());
            line.setWidth(1); // vertical
            line.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
            line.setX(nextX + diff);
            line.setY(0);
            nextX ++;
            columnHeader.addElement(line);
            i ++;
        }

        JRDesignLine topLine = new JRDesignLine();
        topLine.setWidth(reportWidthWithLines);
        topLine.setHeight(0);
        topLine.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
        topLine.setX(0);
        topLine.setY(0);
        columnHeader.addElement(topLine);

        design.setColumnHeader(columnHeader);
        i = 0;

        JRDesignLine firstDetailsLine = new JRDesignLine();
        firstDetailsLine.setHeight(details.getHeight());
        firstDetailsLine.setWidth(1); // vertical
        firstDetailsLine.setDirection(JRLine.DIRECTION_BOTTOM_UP);
        firstDetailsLine.setStretchType(JRDesignLine.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT);
        firstDetailsLine.setX(0);
        details.addElement(firstDetailsLine);
        nextX = 1;

        for (JRField field : design.getFields()) {
            JRDesignTextField element = new JRDesignTextField();
            JRDesignExpression expr = new JRDesignExpression();

            if (Arrays.binarySearch(getTextFieldClassNames(),
                    field.getValueClass().getName()) < 0) {
                expr.setValueClass(String.class);
                expr.setText("$F{" + field.getName() + "}.toString()");

                if (DbResource.class.isAssignableFrom(field.getValueClass()))
                    expr.setText("ReportsUtil.getEnumText($F{" + field.getName() + "})");

            } else if (field.getValueClass() == BigInteger.class
                    || field.getValueClass() == BigDecimal.class
                    || field.getValueClass() == Integer.class) {

                expr.setText("ReportsUtil.format($F{" + field.getName() + "})");
                expr.setValueClass(String.class);
                element.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
            } else {
                expr.setText("$F{" + field.getName() + "}");
                expr.setValueClass(field.getValueClass());
            }


            element.setExpression(expr);
            element.setStretchWithOverflow(true);
            element.setBlankWhenNull(true);
            element.setY(1);
            element.setX(nextX);
            nextX += columnWidths.get(i);
            element.setHeight(details.getHeight() - 1);
            element.setKey("textField-" + (i+1));
            element.setWidth(columnWidths.get(i));

            //If last element, add the width lost due to integer division
            int diff = 0;
            if (i + 1== columnCount) {
                diff = reportWidthWithLines - nextX - 1;
                element.setWidth(element.getWidth() + diff);
            }


            details.addElement(element);

            //Adding a vertical line
            JRDesignLine line = new JRDesignLine();
            line.setHeight(details.getHeight());
            line.setStretchType(JRDesignLine.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT);
            line.setWidth(1); // vertical
            line.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
            line.setX(nextX + diff);
            line.setY(0);
            nextX ++;
            details.addElement(line);
            i++;
        }

        JRDesignLine detailsTopLine = new JRDesignLine();
        detailsTopLine.setWidth(reportWidthWithLines);
        detailsTopLine.setHeight(1);
        detailsTopLine.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
        detailsTopLine.setX(0);
        detailsTopLine.setY(0);
        details.addElement(detailsTopLine);

        design.setDetail(details);

        JRDesignBand footer = new JRDesignBand();
        footer.setHeight(1);

        JRDesignLine bottomLine = new JRDesignLine();
        bottomLine.setWidth(reportWidthWithLines);
        bottomLine.setHeight(1);
        bottomLine.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
        bottomLine.setX(0);
        bottomLine.setY(0);
        footer.addElement(bottomLine);

        design.setColumnFooter(footer);

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
                (java.math.BigDecimal.class).getName(), (java.math.BigInteger.class).getName(), (java.lang.Number.class).getName(), (java.lang.String.class).getName()
            });
            Arrays.sort(textFieldClassNames);
        }
        return textFieldClassNames;
    }

    public static String getEnumText(DbResource resource) {
        return new BeanResource(AcaciaApplication.class).getShortName(resource);
    }

    public static String format(Number number) {
        if (number == null)
            number = BigDecimal.ZERO;

        return AcaciaUtils.getDecimalFormat().format(number,
                new StringBuffer(),
                new FieldPosition(0)).toString();
    }

    public static String formatPercent(Number number) {
        if (number == null)
            number = BigDecimal.ZERO;

        return AcaciaUtils.getPercentFormat().format(number,
                new StringBuffer(),
                new FieldPosition(0)).toString();
    }

    public static String formatDate(Date date) {
        if (date == null)
            return "";

        return AcaciaUtils.getShortDateFormat().format(date,
                new StringBuffer(),
                new FieldPosition(0)).toString();
    }
}
