/**
 * 
 */
package com.cosmos.test.bl;

import static com.cosmos.acacia.gui.AcaciaPanel.getBean;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;

import com.cosmos.acacia.app.AcaciaSessionRemote;
import com.cosmos.acacia.crm.bl.users.UsersRemote;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.gui.AcaciaApplication;

/**
 * Created	:	28.07.2008
 * @author	Petar Milev
 *
 */
public class BaseTest {
    private static Map<String, Object> sessionCache = new HashMap<String, Object>();
    
    protected UsersRemote usersRemote = getBean(UsersRemote.class);
    
    protected AcaciaSessionRemote session = getBean(AcaciaSessionRemote.class);
    
    protected Random random = new Random();
    
    private boolean sessionCaching = true;
    
    @Before
    public void setUp() {
        login("admin", "admin");
    }

    protected void login(String userName, String password) {
        User loggedUser = getUser();
        //already logged - so return
        if ( loggedUser!=null && loggedUser.getUserName().equals(userName))
            return;
        
        Integer sessionid = usersRemote.login(userName, password.toCharArray());
        AcaciaApplication.setSessionId(sessionid);

        User user = session.getUser();
        List<Organization> organizations = usersRemote.getOrganizationsList(user);
        if ( organizations==null || organizations.isEmpty() )
            throw new IllegalStateException("Organizations empty for this user: "+userName);
        
        usersRemote.setOrganization(organizations.get(0));
    }
    
    protected User getUser(){
        User result = (User) sessionCache.get("user");
        if ( result==null ){
            result = session.getUser();
            sessionCache.put("user", result);
        }
        return result;
    }
    
    protected Organization getOrganization(){
        Organization result = (Organization) sessionCache.get("organization");
        if ( result==null ){
            result = session.getOrganization();
            sessionCache.put("organization", result);
        }
        return result;
    }
    
    protected BigInteger getOrganizationId(){
        return getOrganization().getId();
    }

    protected Address getBranch(){
        Address result = (Address) sessionCache.get("branch");
        if ( result==null ){
            result = session.getBranch();
            sessionCache.put("branch", result);
        }
        return result;
    }

    public boolean isSessionCaching() {
        return sessionCaching;
    }
    
    protected void clearSessionCache(){
        sessionCache.clear();
    }

    public void setSessionCaching(boolean sessionCaching) {
        this.sessionCaching = sessionCaching;
        if ( !sessionCaching )
            sessionCache.clear();
    }
}
