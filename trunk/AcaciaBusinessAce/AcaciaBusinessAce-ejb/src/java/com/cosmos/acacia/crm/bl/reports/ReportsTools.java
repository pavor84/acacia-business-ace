package com.cosmos.acacia.crm.bl.reports;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.UUID;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.PdfFont;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.lowagie.text.pdf.BaseFont;

@SuppressWarnings("unchecked")
@Stateless
public class ReportsTools implements ReportsToolsRemote, ReportsToolsLocal {

    private static final int ID_COLUMN_WIDTH = 5;

    protected static Logger log = Logger.getLogger(ReportsTools.class);

    public static final String FS = System.getProperty("file.separator");
    public static final int TYPE_PRINTER = 0;
    public static final int TYPE_PDF = 1;
    public static final int TYPE_HTML = 2;
    public static final int TYPE_XML = 3;

    protected static final String SUBREPORT_DIR = "reports/";

    protected static String pdfFontPath;
    static {
        try {
            pdfFontPath = new File(ReportsTools.class.getResource("/reports/fonts/times.ttf").toURI())
            .getAbsolutePath().replace("times.ttf", "");
        } catch (URISyntaxException ex) {
            log.error(ex);
        }
    }

    public static Boolean print(JasperReport jasperReport,
            JRDataSource ds,
            Map paramsMap,
            String filename,
            int choice)
    {
        try {
            Map params = new HashMap();

            params.put("REPORTS_DIR", "reports");
            params.putAll(paramsMap);

            JRStyle[] styles = jasperReport.getStyles();
            if (styles != null) {
                for (JRStyle style : styles) {
                    if (style.getName().equals("acaciaReportStyle"))
                        style.setPdfFontName(pdfFontPath + "times.ttf");

                    if (style.getName().equals("acaciaReportBoldStyle"))
                        style.setPdfFontName(pdfFontPath + "timesbd.ttf");
                }
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, params, ds);


            boolean success = true;

            if (choice == TYPE_PRINTER) {
                JasperPrintManager.printReport(jasperPrint, true);
            } else {
                if (filename != null) {
                    if (choice == TYPE_PDF) {
                        filename += ".pdf";

                        JRPdfExporter exporter = new JRPdfExporter();

                        Map fontMap = new HashMap();

                        fontMap.put(new FontKey("times-utf", false, false),
                                new PdfFont(pdfFontPath + "times.ttf", BaseFont.IDENTITY_H, true));

                        fontMap.put(new FontKey("times-utf-b", true, false),
                                new PdfFont(pdfFontPath + "timesbd.ttf", BaseFont.IDENTITY_H, true));

                        fontMap.put(new FontKey("times-utf-i", false, true),
                                new PdfFont(pdfFontPath + "timesi.ttf", BaseFont.IDENTITY_H, true));

                        fontMap.put(new FontKey("times-utf-bi", true, true),
                                new PdfFont(pdfFontPath + "timesbi.ttf", BaseFont.IDENTITY_H, true));

                        //exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);
                        exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
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
                } else {
                    success = false;
                }
            }

            return success;
        } catch (Exception ex) {
            log.error("Error printing", ex);
            return false;
        }
    }

    public JasperDesign createTableReport(Class entityClass, EntityProperties entityProps) throws JRException {
        return createTableReport(entityClass, entityProps, false);
    }

    public JasperDesign createTableReport(Class entityClass, EntityProperties entityProps, Boolean isSubreport) throws JRException {
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

        if (entityProps == null)
            entityProps = new EntityProperties(entityClass, true);

        return createTableReport(entityProps, underscoreSeparatedName, isSubreport);
    }

    public JasperDesign createTableReport(EntityProperties entityProps,
            String reportName, Boolean isSubreport) throws JRException {

        if (entityProps.getKeys().size() == 0)
            return null;

        // Initialization
        JasperDesign design = new JasperDesign();

        // Addint styles
        JRDesignStyle defaultStyle = new JRDesignStyle();
        defaultStyle.setName("acaciaReportStyle");
        defaultStyle.setPdfFontName(pdfFontPath + "times.ttf");
        defaultStyle.setPdfEncoding(BaseFont.IDENTITY_H);
        defaultStyle.setPdfEmbedded(true);
        design.addStyle(defaultStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("acaciaReportBoldStyle");
        boldStyle.setPdfFontName(pdfFontPath + "timesbd.ttf");
        boldStyle.setPdfEncoding(BaseFont.IDENTITY_H);
        boldStyle.setPdfEmbedded(true);
        boldStyle.setBold(true);
        design.addStyle(boldStyle);

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
        for (EntityProperty pd : entityProps.getValues()) {
            JRDesignField field = createDesignField(pd);
            design.addField(field);
        }

        JRDesignBand columnHeader = new JRDesignBand();
        columnHeader.setHeight(15);

        JRDesignBand details = new JRDesignBand();
        details.setHeight(15);
        List<Integer> columnWidths = new ArrayList<Integer>(entityProps.getValues().size());

        if (!isSubreport) {
            JRDesignLine firstLine = new JRDesignLine();
            firstLine.setHeight(columnHeader.getHeight());
            firstLine.setWidth(0); // vertical
            firstLine.setDirection(JRLine.DIRECTION_BOTTOM_UP);
            firstLine.setX(0);
            columnHeader.addElement(firstLine);
        }
        int nextX = 1;
        int i = 0;
        EntityProperty pd = null;
        Iterator<EntityProperty> propertyDetailsIterator = entityProps.getValues().iterator();
        boolean isIdColumn = true;
        // First iteration is for the ID column
        while (pd != null || propertyDetailsIterator.hasNext()) {
            JRDesignExpression expr = new JRDesignExpression();
            JRDesignTextField caption = new JRDesignTextField();
            int columnWidth = 0;
            expr.setValueClass(String.class);
            if (pd == null) {
                expr.setText("$R{id}");
                columnWidth = reportWidth * ID_COLUMN_WIDTH / 100;
            } else {
                expr.setText("$R{" + pd.getPropertyName() + "}");
                columnWidth = reportWidth * pd.getReportColumnWidth() / 100;
                columnWidths.add(columnWidth);
            }

            caption.setExpression(expr);
            caption.setStyleNameReference("acaciaReportBoldStyle");

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

            if (pd == null && !isSubreport) {
                if (propertyDetailsIterator.hasNext())
                    pd = propertyDetailsIterator.next();
                continue;
            }

            if (propertyDetailsIterator.hasNext())
                pd = propertyDetailsIterator.next();
            else
                pd = null;

            columnHeader.addElement(caption);

            //Adding a vertical line
            JRDesignLine line = new JRDesignLine();
            line.setHeight(columnHeader.getHeight());
            line.setWidth(1); // vertical
            line.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
            line.setX(nextX + diff);
            line.setY(0);
            nextX ++;

            if (!isIdColumn)
                i ++;

            isIdColumn = false;

            if ((!isIdColumn && i < columnCount) || !isSubreport)
                columnHeader.addElement(line);
        }

        if (!isSubreport) {
            JRDesignLine topLine = new JRDesignLine();
            topLine.setWidth(reportWidthWithLines);
            topLine.setHeight(0);
            topLine.setDirection(JRLine.DIRECTION_BOTTOM_UP); // is it needed?
            topLine.setX(0);
            topLine.setY(0);
            columnHeader.addElement(topLine);
        }

        design.setColumnHeader(columnHeader);
        i = 0;

        if (!isSubreport) {
            JRDesignLine firstDetailsLine = new JRDesignLine();
            firstDetailsLine.setHeight(details.getHeight());
            firstDetailsLine.setWidth(1); // vertical
            firstDetailsLine.setDirection(JRLine.DIRECTION_BOTTOM_UP);
            firstDetailsLine.setStretchType(JRDesignLine.STRETCH_TYPE_RELATIVE_TO_TALLEST_OBJECT);
            firstDetailsLine.setX(0);
            details.addElement(firstDetailsLine);
        }
        nextX = 1;

        Iterator<JRField> fieldIterator = design.getFieldsList().iterator();
        JRField field = null;
        isIdColumn = true;

        //first iteration is for the ID column
        while (field != null || fieldIterator.hasNext()) {
            JRDesignTextField element = new JRDesignTextField();
            JRDesignExpression expr = new JRDesignExpression();
            int columnWidth = 0;
            if (isIdColumn && !isSubreport) {
                field = fieldIterator.next();
                isIdColumn = false;
                continue;
            }

            if (field == null) {
                columnWidth = reportWidth * ID_COLUMN_WIDTH / 100 ;
                expr.setText("$V{REPORT_COUNT}.toString()");
                expr.setValueClass(String.class);
            } else {
                if (Arrays.binarySearch(getTextFieldClassNames(),
                        field.getValueClass().getName()) < 0) {
                    expr.setValueClass(String.class);
                    expr.setText("$F{" + field.getName() + "}.toString()");

                    if (DbResource.class.isAssignableFrom(field.getValueClass()))
                        expr.setText("ReportsUtil.getEnumText($F{" + field.getName() + "})");

                } else if (field.getValueClass() == UUID.class
                        || field.getValueClass() == BigDecimal.class
                        || field.getValueClass() == Integer.class) {

                    expr.setText("ReportsUtil.format($F{" + field.getName() + "})");
                    expr.setValueClass(String.class);
                    element.setHorizontalAlignment(JRAlignment.HORIZONTAL_ALIGN_RIGHT);
                } else {
                    expr.setText("$F{" + field.getName() + "}");
                    expr.setValueClass(field.getValueClass());
                }
                columnWidth = columnWidths.get(i);
            }

            element.setExpression(expr);
            element.setX(nextX);
            nextX += columnWidth;
            element.setHeight(details.getHeight() - 1);
            element.setKey("textField-" + (i+1));
            element.setWidth(columnWidth);
            setTextFieldProperties(element);

            //If last element, add the width lost due to integer division
            int diff = 0;
            if (i + 1 == columnCount) {
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

            if (!isIdColumn)
                i++;

            isIdColumn = false;

            if ((!isIdColumn && i < columnCount) || !isSubreport)
                details.addElement(line);

            if (fieldIterator.hasNext())
                field = fieldIterator.next();
            else
                field = null;

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

    private JRDesignField createDesignField(EntityProperty pd) {
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

        return field;
    }

    private void setTextFieldProperties(JRDesignTextField element) {
        element.setStretchWithOverflow(true);
        element.setBlankWhenNull(true);
        element.setY(1);
        element.setStyleNameReference("acaciaReportStyle");
    }

    private String pluralize(String str) {
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
                (java.math.BigDecimal.class).getName(), (java.util.UUID.class).getName(), (java.lang.Number.class).getName(), (java.lang.String.class).getName()
            });
            Arrays.sort(textFieldClassNames);
        }
        return textFieldClassNames;
    }

    public JasperReport loadReport(Report report) {
        if (report == null)
            return null;

        String reportName = report.getReportName();

        try {
            String resourceName = "/reports/" + reportName + ".jasper";

            log.info("BaseEntityPanel.print().resourceName: " + resourceName);
            InputStream is = getClass().getResourceAsStream(resourceName);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

            return jasperReport;
        } catch (Exception ex) {
            log.error("Loading error: ", ex);
            return null;
        }
    }

    @Override
    public JasperDesign createTableReport(Class entityClass, Boolean isSubreport)
            throws JRException {
        return createTableReport(entityClass, null, isSubreport);
    }
}
