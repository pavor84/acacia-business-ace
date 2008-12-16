package com.cosmos.acacia.crm.bl.reports;

import javax.ejb.Remote;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import com.cosmos.beansbinding.EntityProperties;

@Remote
public interface ReportsToolsRemote {

    JasperReport loadReport(Report report);

    JasperDesign createTableReport(
            EntityProperties entityProps,
            String reportName,
            Boolean isSubreport) throws JRException;

    JasperDesign createTableReport(
            Class entityClass,
            EntityProperties entityProps,
            Boolean isSubreport) throws JRException;

    JasperDesign createTableReport(
            Class entityClass,
            Boolean isSubreport) throws JRException;

    JasperDesign createTableReport(Class entityClass,
            EntityProperties entityProps) throws JRException;

}