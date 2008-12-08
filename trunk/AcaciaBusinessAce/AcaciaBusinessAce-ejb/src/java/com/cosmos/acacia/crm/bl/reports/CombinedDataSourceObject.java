package com.cosmos.acacia.crm.bl.reports;

import java.io.Serializable;
import java.util.Collection;

public class CombinedDataSourceObject implements Serializable {
    private Object entity;
    private Object header;
    private Collection subreport1;
    private Collection subreport2;

    public Object getEntity() {
        return entity;
    }
    public void setEntity(Object entity) {
        this.entity = entity;
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
    public Object getHeader() {
        return header;
    }
    public void setHeader(Object header) {
        this.header = header;
    }
}