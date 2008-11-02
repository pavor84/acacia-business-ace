package com.cosmos.acacia.crm.reports;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class Report {
    private String reportName;
    private Collection subreport1;
    private Collection subreport2;
    private String key;

    public Report() {

    }

    public Report(String reportName, Collection subreport1, Collection subreport2) {
        super();
        this.reportName = reportName;
        this.subreport1 = subreport1;
        this.subreport2 = subreport2;
    }
    public String getReportName() {
        return reportName;
    }
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    public Collection getSubreport1() {
        return subreport1;
    }
    public void setSubreport1(Collection subreport1) {
        this.subreport1 = subreport1;
    }
    public Collection getSubreport2() {
        return subreport2;
    }
    public void setSubreport2(Collection subreport2) {
        this.subreport2 = subreport2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
