package com.cosmos.acacia.crm.bl.impl;

import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface EntityManagerFacadeRemote {

    void persist(Object entity);
    List getNamedQueryResult(String queryName, Map<String, Object> params);
}
