/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public enum AccountStatus implements DatabaseResource, EnumClassifier<AccountStatus> {

    Enabled,
    Disabled,
    AwaitingActivation,
    Expired,
    Locked;

    private AccountStatus() {
    }
    //
    private DbResource dbResource;

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    @Override
    public String toShortText() {
        return name();
    }

    @Override
    public String toText() {
        return name();
    }
    private static TreeMap<String, List<AccountStatus>> enumsMap = new TreeMap<String, List<AccountStatus>>();

    @Override
    public List<AccountStatus> getEnums(Object... classifiers) {
        if (classifiers.length < 1 && !(classifiers[0] instanceof Class)) {
            throw new UnsupportedOperationException("Unsupported classifier(s) type: " +
                    Arrays.asList(classifiers));
        }

        Class entityClass = (Class) classifiers[0];
        String entityClassName = entityClass.getName();
        List<AccountStatus> statuses;
        if ((statuses = enumsMap.get(entityClassName)) != null) {
            return statuses;
        }

        List<AccountStatus> list = null;
        if (TeamMember.class == entityClass) {
            list = Arrays.asList(Enabled, Disabled);
        } else if (Team.class == entityClass) {
            list = Arrays.asList(Enabled, Disabled);
        }

        if (list != null) {
            enumsMap.put(entityClassName, list);
            return list;
        }

        throw new UnsupportedOperationException("Unsupported entity class: " + entityClass);
    }
}
