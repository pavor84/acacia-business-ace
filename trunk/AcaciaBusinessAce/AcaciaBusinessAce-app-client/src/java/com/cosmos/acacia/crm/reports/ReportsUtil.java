package com.cosmos.acacia.crm.reports;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.jdesktop.application.ResourceMap;

@SuppressWarnings("unchecked")
public class ReportsUtil {

    protected static Logger log = Logger.getLogger(ReportsUtil.class);

    protected static final String FS = System.getProperty("file.separator");
    protected static final int TYPE_PRINTER = 0;
    protected static final int TYPE_PDF = 1;
    protected static final int TYPE_HTML = 2;
    protected static final int TYPE_XML = 3;


    public static void print(JasperReport jasperReport,
            JRDataSource ds,
            Component uiComponent,
            ResourceMap resourceMap)
    {
        try {
            Map params = new HashMap();
            params.put("REPORTS_DIR", "reports");

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

    private static String chooseTargetPath(Component uiComponent) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fChoice = fc.showOpenDialog(uiComponent);
        if (fChoice == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
