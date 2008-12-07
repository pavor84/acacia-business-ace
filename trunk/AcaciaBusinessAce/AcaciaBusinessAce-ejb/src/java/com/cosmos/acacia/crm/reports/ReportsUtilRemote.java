package com.cosmos.acacia.crm.bl.reports;

import javax.ejb.Remote;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

@Remote
public interface ReportsUtilRemote {

    JasperReport loadReport(Report report);
    JasperDesign createTableReport(
            Class entityClass,
            String reportName,
            Boolean isSubreport) throws JRException;

    JasperDesign createTableReport(
            Class entityClass,
            Boolean isSubreport) throws JRException;

    JasperDesign createTableReport(Class entityClass) throws JRException;


}