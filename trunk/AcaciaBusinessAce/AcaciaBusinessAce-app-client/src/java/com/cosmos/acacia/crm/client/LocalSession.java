package com.cosmos.acacia.crm.client;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.app.DeferredListServerRemote;
import com.cosmos.acacia.crm.bl.users.RightsManagerRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.gui.AcaciaPanel;

/**
 * Singleton client session. Caches results from the remote session
 * @author Bozhidar Bozhanov
 */
public class LocalSession implements AcaciaSessionRemote {

    //Keys
    public static final String BRANCH = "BRANCH";
    public static final String ORGANIZATION = "ORGANIZATION";
    public static final String USER = "USER";
    public static final String PERSON = "PERSON";

    public static final String VIEW_DATA_FROM_ALL_BRANCHES
    = "VIEW_DATA_FROM_ALL_BRANCHES";

    private AcaciaSessionRemote remoteSession;

    private DeferredListServerRemote listServer;

    private Map<String, Object> sessionCache = new HashMap<String, Object>();

    private static LocalSession instance;

    private RightsManagerRemote rightsManager;

    private LocalSession() {
            remoteSession = AcaciaPanel.getBean(AcaciaSessionRemote.class, false);
    }

    public static LocalSession instance() {
        if (instance == null)
            instance = new LocalSession();

        return instance;
    }

    public void put(String key, Object value) {
        sessionCache.put(key, value);
    }

    public Object get(String key) {
        return sessionCache.get(key);
    }

    @Override
    public Address getBranch() {
        Address branch = (Address) sessionCache.get(BRANCH);
        if (branch == null) {
            branch = remoteSession.getBranch();
            sessionCache.put(BRANCH, branch);
        }

        return branch;
    }

    @Override
    public DataObject getDataObject(BigInteger dataObjectId) {
       return remoteSession.getDataObject(dataObjectId);
    }

    @Override
    public List<DataObjectType> getDataObjectTypes() {
       return remoteSession.getDataObjectTypes();
    }

    @Override
    public Organization getOrganization() {
        Organization org = (Organization) sessionCache.get(ORGANIZATION);
        if (org == null) {
            org = remoteSession.getOrganization();
            sessionCache.put(ORGANIZATION, org);
        }

        return org;
    }

    @Override
    public Person getPerson() {
        Person person = (Person) sessionCache.get(PERSON);
        if (person == null) {
            person = remoteSession.getPerson();
            sessionCache.put(PERSON, person);
        }

        return person;
    }

    @Override
    public User getUser() {
        User user = (User) sessionCache.get(USER);
        if (user == null) {
            user = remoteSession.getUser();
            sessionCache.put(USER, user);
        }

        return user;
    }

    @Override
    public void setBranch(Address branch) {
        remoteSession.setBranch(branch);
        sessionCache.put(BRANCH, branch);
    }

    @Override
    public Boolean getViewDataFromAllBranches() {
        Boolean value = (Boolean) sessionCache.get(VIEW_DATA_FROM_ALL_BRANCHES);
        if (value == null) {
            value = remoteSession.getViewDataFromAllBranches();
            sessionCache.put(VIEW_DATA_FROM_ALL_BRANCHES, value);
        }

        return value;
    }

    @Override
    public void setViewDataFromAllBranches(Boolean value) {
        remoteSession.setViewDataFromAllBranches(value);
        sessionCache.put(VIEW_DATA_FROM_ALL_BRANCHES, value);
    }

    public RightsManagerRemote getRightsManager() {
        if (rightsManager == null)
            rightsManager = AcaciaPanel.getBean(RightsManagerRemote.class, false);

        return rightsManager;
    }

    public DeferredListServerRemote getListServer() {
        if (listServer == null)
            listServer = AcaciaPanel.getBean(DeferredListServerRemote.class, false);

        return listServer;
    }
}