package com.cosmos.acacia.crm.bl.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmos.beansbinding.EntityProperties;

@SuppressWarnings("unchecked")
public class Report implements Serializable {

    /**
     * The name of the already compiled report file (without '.jasper'), or,
     * in case reportClass is set, the name of the generated report
     *
     */
    private String reportName;

    /**
     * The list of collections that will be used as subreports (first)
     * for this report
     */
    private List<Collection> subreports1Data;

    /**
     * The list of collections that will be used as subreports (second)
     * for this report
     */
    private List<Collection> subreports2Data;

    /**
     * The name for the first subreport file, if it is not to be
     * automatically generated.
     */
    private String subreport1Name;

    /**
     * The name for the second subreport file, if it is not to be
     * automatically generated.
     */
    private String subreport2Name;

    /**
     * The Class on the basis of which an automatic
     * table subreport (first) will be generated
     */
    private Class autoSubreport1Class;

    /**
     * The Class on the basis of which an automatic
     * table subreport (second) will be generated
     */
    private Class autoSubreport2Class;


    /**
     * The EntityProperties on the basis of which an automatic
     * table subreport (first) will be generated
     */
    private EntityProperties autoSubreport1Properties;

    /**
     * The EntityProperties on the basis of which an automatic
     * table subreport (second) will be generated
     */
    private EntityProperties autoSubreport2Properties;


    /**
     * The key for the name of the report shown in front of the user,
     *  in case he has to choose from more than one reports
     */
    private String localizationKey;

    /**
     * The Class on the basis of which an automatic
     * table report will be generated
     */
    private Class reportClass;


    private Map<String, Object> parameters = new HashMap<String, Object>();

    public Report() {

    }

    /**
     * Convenient constructor for single-entity report
     * @param reportName
     * @param subreport1
     * @param subreport2
     */
    public Report(String reportName, Collection subreport1, Collection subreport2) {
        super();
        this.reportName = reportName;
        subreports1Data = new ArrayList<Collection>(1);
        subreports2Data = new ArrayList<Collection>(1);
        subreports1Data.add(subreport1);
        subreports2Data.add(subreport2);
    }


    public Report(String reportName, Collection subreport1) {
        super();
        this.reportName = reportName;
        subreports1Data = new ArrayList<Collection>(1);
        subreports1Data.add(subreport1);
        subreports2Data = new ArrayList<Collection>(1);
        subreports2Data.add(null);
    }

    public Report(String reportName) {
        super();
        this.reportName = reportName;
        subreports1Data = new ArrayList<Collection>();
        subreports2Data = new ArrayList<Collection>();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public List<Collection> getSubreports1Data() {
        return subreports1Data;
    }

    public void setSubreports1Data(List<Collection> subreports1Data) {
        this.subreports1Data = subreports1Data;
    }

    public List<Collection> getSubreports2Data() {
        return subreports2Data;
    }

    public void setSubreports2Data(List<Collection> subreports2Data) {
        this.subreports2Data = subreports2Data;
    }

    public String getSubreport1Name() {
        return subreport1Name;
    }

    public void setSubreport1Name(String subreport1Name) {
        this.subreport1Name = subreport1Name;
    }

    public String getSubreport2Name() {
        return subreport2Name;
    }

    public void setSubreport2Name(String subreport2Name) {
        this.subreport2Name = subreport2Name;
    }

    public String getLocalizationKey() {
        return localizationKey;
    }

    public void setLocalizationKey(String localizationKey) {
        this.localizationKey = localizationKey;
    }

    public Class getAutoSubreport1Class() {
        return autoSubreport1Class;
    }

    public void setAutoSubreport1Class(Class autoSubreport1Class) {
        this.autoSubreport1Class = autoSubreport1Class;
    }

    public Class getAutoSubreport2Class() {
        return autoSubreport2Class;
    }

    public void setAutoSubreport2Class(Class autoSubreport2Class) {
        this.autoSubreport2Class = autoSubreport2Class;
    }

    public Class getReportClass() {
        return reportClass;
    }

    public void setReportClass(Class reportClass) {
        this.reportClass = reportClass;
    }

    public EntityProperties getAutoSubreport1Properties() {
        return autoSubreport1Properties;
    }

    public void setAutoSubreport1Properties(
            EntityProperties autoSubreport1Properties) {
        this.autoSubreport1Properties = autoSubreport1Properties;
    }

    public EntityProperties getAutoSubreport2Properties() {
        return autoSubreport2Properties;
    }

    public void setAutoSubreport2Properties(
            EntityProperties autoSubreport2Properties) {
        this.autoSubreport2Properties = autoSubreport2Properties;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
