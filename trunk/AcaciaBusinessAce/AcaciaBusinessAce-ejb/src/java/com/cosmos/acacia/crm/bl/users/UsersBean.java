/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import java.util.Set;
import javax.crypto.Cipher;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.callback.CallbackHandler;
import com.cosmos.acacia.callback.CallbackLocal;
import com.cosmos.acacia.callback.CallbackTransportObject;
import com.cosmos.acacia.crm.bl.assembling.AssemblingLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.contacts.Address;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.contacts.ContactPerson;
import com.cosmos.acacia.crm.data.contacts.Organization;
import com.cosmos.acacia.crm.data.contacts.Person;
import com.cosmos.acacia.crm.data.contacts.PositionType;
import com.cosmos.acacia.crm.data.users.RegistrationCode;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.users.UserGroup;
import com.cosmos.acacia.crm.data.users.UserOrganization;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRatePK;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoiceItem;
import com.cosmos.acacia.crm.data.users.BusinessUnit;
import com.cosmos.acacia.crm.data.users.BusinessUnitAddress;
import com.cosmos.acacia.crm.data.users.UserGroupMember;
import com.cosmos.acacia.crm.enums.BusinessUnitType;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.enums.MailType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.base64.Base64Decoder;
import com.cosmos.base64.Base64Encoder;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.mail.MessageParameters;
import com.cosmos.util.SecurityUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.UUID;
import javax.ejb.Stateless;

/**
 * The implementation of handling users (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class UsersBean implements UsersRemote, UsersLocal {

    public static final String ORGANIZATIONS_KEY = "organizations";

    public static final String ORGANIZATION_KEY = "organization";

    public static final String ENCODING = "ISO-8859-1";

    protected static Logger log = Logger.getLogger(UsersBean.class);

    private Locale locale;

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private AcaciaSessionLocal session;

    @EJB
    private CallbackLocal callbackHandler;

    @EJB
    private AssemblingLocal assemblingLocal;

    @EJB
    private ClassifiersLocal classifiersManager;

    @EJB
    private UsersServiceLocal usersService;

    /** Temporary indicator that operations are concerning password change */
    private boolean passwordChange = false;

    private static final String INCORRECT_LOGIN_DATA = "Login.data.incorrect";
    private static final String TEMPORARY_LOGIN = "temporaryLogin";
    private static final String LOGIN = "login";

    @Override
    public UUID login(String username, char[] password) {
        try {
            return login(username, password, false);
        } catch (ValidationException ex) {
            if (ex.getMessage().equals(INCORRECT_LOGIN_DATA)) {
                return login(username, password, true);
            }

            throw ex;
        }
    }

    private UUID login(String username, char[] password, boolean temporaryLogin) {
        Query loginQuery;
        if(temporaryLogin) {
            loginQuery = em.createNamedQuery(User.NQ_FIND_BY_USERNAME_AND_SYSTEM_PASSWORD);
        } else {
            loginQuery = em.createNamedQuery(User.NQ_FIND_BY_USERNAME_AND_PASSWORD);
        }
        loginQuery.setParameter("username", username);
        loginQuery.setParameter("password", SecurityUtils.getHash(new String(password)));

        try {
            User user = (User) loginQuery.getSingleResult();

            if (temporaryLogin) {
                // Checking whether the validity period hasn't expired
                if (System.currentTimeMillis() > user.getSystemPasswordValidity().getTime())
                    throw new ValidationException(INCORRECT_LOGIN_DATA);

                user.setNextActionAfterLogin(CHANGE_PASSWORD);
            }

            if (!passwordChange) {
                UUID sessionId = session.login(user);
                setOrganization(session.getSystemOrganization());
                return sessionId;
            }

            return null;
        } catch (NoResultException ex){
            throw new ValidationException(INCORRECT_LOGIN_DATA);
        }
    }

    @Override
    public void remindPasswordByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail");
        q.setParameter("email", email);

        try {
            User user = (User) q.getSingleResult();
            remindPassword(user);
        } catch (NoResultException ex) {
            throw new ValidationException("ForgottenPasswordForm.invalid.email");
        }
    }

    @Override
    public void remindPasswordByUsername(String username) {
        Query q = em.createNamedQuery("User.findByUserName");
        q.setParameter("userName", username);

        try {
            User user = (User) q.getSingleResult();
            remindPassword(user);
        } catch (Exception ex) {
            throw new ValidationException("ForgottenPasswordForm.invalid.username");
        }
    }

    private static final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    private void remindPassword(User user) {
        String tempPassword = generateTemporaryPassword();
        user.setSystemPassword(SecurityUtils.getHash(tempPassword));
        user.setSystemPasswordValidity(new Date(System.currentTimeMillis() + MILLIS_IN_DAY));
        esm.persist(em, user);

        sendMail(user.getEmailAddress(),
                MessageRetriever.get("forgottenPasswordContent", locale, tempPassword),
                MessageRetriever.get("forgottenPasswordSubjecet", locale));
    }

    private String generateTemporaryPassword() {
        int length = 6 + (int) (Math.random() * 6);
        byte[] chars = new byte[length];
        for (int i = 0; i < chars.length; i ++) {
            chars[i] = (byte) (33 + (Math.random() * 93));
        }
        return new String(chars).trim();
    }

    @Override
    public void requestRegistration(String email) {
        Query q = em.createNamedQuery(User.NQ_FIND_BY_EMAIL);
        q.setParameter("emailAddress", email);
        try {
            q.getSingleResult();
            throw new ValidationException("email.taken");
        } catch(NoResultException ex) {
        }

        UUID codeNumber;
        q = em.createNamedQuery(RegistrationCode.NQ_FIND_BY_EMAIL);
        q.setParameter("emailAddress", email);
        try {
            RegistrationCode registrationCode = (RegistrationCode) q.getSingleResult();
            codeNumber = registrationCode.getRegistrationCode();
        } catch(NoResultException ex) {
            codeNumber = UUID.randomUUID();
            RegistrationCode code = new RegistrationCode(codeNumber);
            code.setEmailAddress(email);
            esm.persist(em, code);
        }

        try {
            sendMail(email,
                    MessageRetriever.get("registrationCodeMessage", locale) + " # " + codeNumber.toString(),
                    MessageRetriever.get("registrationCodeSubject", locale));
        } catch (ValidationException ex){
            throw ex;
        }
    }

    @Override
    public String verifyCode(String code) {
        System.out.println("verifyCode(" + code + ")");
        Query q = em.createNamedQuery(RegistrationCode.NQ_FIND_BY_CODE);
        q.setParameter("code", UUID.fromString(code));
        RegistrationCode rc;
        try {
            rc = (RegistrationCode) q.getSingleResult();
            return rc.getEmailAddress();
        } catch (NoResultException ex){
            throw new ValidationException("code.incorrect");
        }
    }

    @SuppressWarnings("null")
    @Override
    public User signup(User user, Organization organization, Address branch, Person person) {
        Query q = em.createQuery("SELECT p FROM Person p where p.firstName=:firstName and " +
                "p.secondName=:secondName and " +
                "p.lastName=:lastName and " +
                "p.extraName=:extraName");

        q.setParameter("firstName", person.getFirstName());
        q.setParameter("secondName", person.getSecondName());
        q.setParameter("lastName", person.getLastName());
        q.setParameter("extraName", person.getExtraName());

        try {
            q.getSingleResult();
            // if there is an existing person with this combination,
            // set the username as extra name
            if (person.getExtraName() == null
                    || person.getExtraName().length() == 0) {

                person.setExtraName(user.getUserName());
            }

        } catch (Exception ex) {
            // No existing user found.
        }
        esm.persist(em, person);

        user.setUserPassword(SecurityUtils.getHash(user.getUserPassword()));
        user.setPerson(person);
        user.setCreationTime(new Date());
        esm.persist(em, user);
        user.setCreator(user);
        esm.persist(em, user);


        if (organization != null) {

            UserOrganization uo = new UserOrganization(user, organization);
            uo.setBranch(branch);
            uo.setUserActive(false);

            esm.persist(em, uo);

            if (organization.isActive() && (branch == null || person == null))
                throw new ValidationException("User.err.branchAndPersonRequired");

            if (branch != null) {
                ContactPerson cp = new ContactPerson();
                cp.setParentId(branch.getId());
                cp.setPerson(person);
                esm.persist(em,cp);
            }
        }

        return user;
    }

    @Override
    public User createUser(String emailAddress) {
        if(emailAddress == null || (emailAddress = emailAddress.trim()).length() == 0) {
            throw new NullPointerException("The email addesss " + emailAddress + " can not be null or empty.");
        }
        User user = usersService.newUser();
        user.setEmailAddress(emailAddress);

        return user;
    }

    @Override
    public EntityProperties getUserEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(User.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public Locale[] serveLocalesList() {
        return new Locale[]{new Locale("en")};
    }
    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String encryptPassword(char[] password) {
        try {
            byte[] bytes = new String(password).getBytes(ENCODING);

            PublicKey key = SecurityManager.getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String base64String = Base64Encoder.toBase64String(cipher.doFinal(bytes));

            return base64String;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public char[] decryptPassword(String password) {
        try {
            byte[] bytes = Base64Decoder.toByteArray(password);
            PrivateKey key = SecurityManager.getPrivateKey();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(bytes), ENCODING).toCharArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public void updateOrganization(User user, CallbackHandler handler) {

        List<Organization> organizations = getActiveOrganizations(user);
        Organization organization = null;

        if (organizations != null && organizations.size() > 0) {
            log.info("Organizations size: " + organizations.size());
            if (organizations.size() > 1) {
                try {
                    CallbackTransportObject request = new CallbackTransportObject();
                    request.put(ORGANIZATIONS_KEY, organizations);
                    CallbackTransportObject result = callbackHandler.handle(handler, request);
                    organization = (Organization) result.get(ORGANIZATION_KEY);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                log.info("Organization = " + organization);
            } else if (organizations.size() == 1) {
                organization = organizations.get(0);
            }

            if (organization != null) {
                UserOrganization uo = usersService.getUserOrganization(user, organization);
                if (uo.isUserActive()){
                    session.setOrganization(organization);
                    session.setBranch(uo.getBranch());
                    session.setPerson(user.getPerson());

                }
                else
                    throw new ValidationException("Login.account.inactive");
            }
        }

        if (organization == null) {
            // TODO: free user
        }
    }


    @Override
    public User activateUser(User user, UUID parentId, Boolean active) {
        Organization o = em.find(Organization.class, parentId);
        if (o != null) {
            UserOrganization uo = usersService.getUserOrganization(user, o);

            uo.setUserActive(active);
            esm.persist(em, uo);
        }

        return user;
    }

    @Override
    public Organization activateOrganization(Organization organization, Boolean active) {
        organization.setActive(active);
        esm.persist(em, organization);

        return organization;
    }

    @Override
    public List<Organization> getActiveOrganizations(User user) {
        return usersService.getActiveOrganizations(user);
    }

    @Override
    public List<Organization> getOrganizations(User user) {
        return usersService.getOrganizations(user);
    }

    private static String EMAIL_HOST = "localhost";
    private static String FROM = "admin@acacia.com";

    private void sendMail(String email, String content, String subject) throws ValidationException {
        try {
            List<javax.mail.Address> to =
                    Arrays.<javax.mail.Address>asList(InternetAddress.parse(email, false));
            MessageParameters messageParameters = new MessageParameters(to, content, subject);
            session.sendMail(MailType.System, messageParameters);
        }
        catch (Exception ex) {
            if (ex instanceof AddressException) {
                throw new ValidationException("email.invalid");
            }
            if (ex instanceof MessagingException) {
                throw new ValidationException(ex);
            }
        }
     }

    @Override
    public void changePassword(char[] oldPassword, char[] newPassword) {
        try {
            passwordChange = true;

            User user = session.getUser();
            // try to login; if the old pass is incorrect, exception will be thrown
            login(user.getUserName(), oldPassword);

            user.setUserPassword(SecurityUtils.getHash(new String(newPassword)));
            user.setSystemPassword(null);
            esm.persist(em, user);
        } catch (ValidationException ex) {
            passwordChange = false;
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(Organization organization) {
        return usersService.getUsers(organization);
    }

    @Override
    public void joinOrganization(Organization organization, Address branch) {
        User user = session.getUser();
        if (user != null && organization != null) {
            UserOrganization uo = new UserOrganization(user, organization);
            uo.setBranch(branch);
            esm.persist(em, uo);
        }
    }

    @Override
    public void leaveOrganization(Organization organization) {
        User user = session.getUser();
        List<Organization> list = getActiveOrganizations(user);
        if (list != null && list.size() == 1)
            throw new ValidationException("Leave.impossible");

        UserOrganization uo = usersService.getUserOrganization(user, organization);
        esm.remove(em, uo);
    }

    @Override
    public int deleteUser(User user) {
        return esm.remove(em, user);
    }

    @Override
    public void setOrganization(Organization organization) {
        User user = session.getUser();
        if (user != null) {
            if (session.getOrganization() == null)
                session.setOrganization(organization);

            if (organization != null) {
                UserOrganization uo = getUserOrganization(user, organization);
                if (uo.isUserActive() && uo.getOrganization().isActive()){
                    session.setOrganization(organization);
                    session.setBranch(uo.getBranch());
                    session.setPerson(user.getPerson());
                } else {
                    throw new ValidationException("Login.account.inactive");
                }
            } else {
                // TODO: free user
            }
        }
    }

    private UserOrganization getUserOrganization(Organization organization) {
        return getUserOrganization(session.getUser(), organization);
    }

    private UserOrganization getUserOrganization(User user) {
        return getUserOrganization(user, session.getOrganization());
    }

    @Override
    public UserOrganization getUserOrganization(User user, Organization organization) {
        return usersService.getUserOrganization(user, organization);
    }

    @Override
    public UserOrganization saveUserOrganization(UserOrganization uo) {
        esm.persist(em, uo);
        return uo;
    }

    @Override
    public EntityProperties getUserOrganizationEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(UserOrganization.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public void changeBranch(User user, Address oldBranch, Address newBranch) {
        Query q = em.createNamedQuery("ContactPerson.findByPersonAndParentDataObject");
        q.setParameter("parentDataObjectId", oldBranch.getId());
        q.setParameter("person", user.getPerson());
        try {
            ContactPerson cp = (ContactPerson) q.getSingleResult();
            cp.setParentId(newBranch.getId());
            esm.persist(em, cp);

//            UserOrganization uo = getUserOrganization(user, acaciaSessionLocal.getOrganization());
//
//            // only if the default user group for the user is in use
//            if (uo.getUserGroup().equals(oldBranch.getUserGroup())) {
//                uo.setUserGroup(newBranch.getUserGroup());
//                esm.persist(em, uo);
//            }

        } catch (Exception ex) {
            // Ignored
        }

    }

    @Override
    public UserOrganization getUserOrganization(Person person) {
        Query q = em.createNamedQuery(User.NQ_FIND_BY_PERSON);
        q.setParameter("person", person);

        try {
            User user = (User) q.getSingleResult();
            return getUserOrganization(user);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void init() {
        try {
            List<AssemblingMessage> messages = assemblingLocal.getAssemblingMessages();
            if(messages == null || messages.size() == 0) {
                for(AssemblingMessage.Unit unit : AssemblingMessage.Unit.values()) {
                    AssemblingMessage message = assemblingLocal.newAssemblingMessage();
                    unit.initAssemblingMessage(message);
                    assemblingLocal.saveAssemblingMessage(message);
                }
            }
        } catch(Exception ex) {
            log.error("Error during initialization of user/organization/branch resources.", ex);
        }

        try {
            initUserData();
        } catch(Exception ex) {
            log.error("Error during initialization of Classifiers resources.", ex);
        }
    }

    private void initUserData() {
        String systemCode = ClassifierGroup.System.getClassifierGroupCode();
        ClassifierGroup systemClassifierGroup = null;
        for(ClassifierGroup classifierGroup : ClassifierGroup.ConstantsMap.values()) {
            String code = classifierGroup.getClassifierGroupCode();
            ClassifierGroup entity;
            if((entity = classifiersManager.getClassifierGroup(code)) == null) {
                entity = (ClassifierGroup)classifierGroup.clone();
                entity = classifiersManager.saveClassifierGroupLocal(entity);
                log.info("New ClassifierGroup '" + entity + "' was added.");
            }

            if(systemCode.equalsIgnoreCase(code))
                systemClassifierGroup = entity;
        }

        Collection<Classifier>constantClassifiers = Classifier.ConstantsMap.values();
        for(Classifier classifier : constantClassifiers) {
            String code = classifier.getClassifierCode();
            Classifier entity;
            if((entity = classifiersManager.getClassifier(code)) == null) {
                entity = (Classifier)classifier.clone();
                entity.setClassifierGroup(systemClassifierGroup);
                entity = classifiersManager.saveClassifierLocal(entity);
                log.info("New Classifier '" + entity + "' was added.");
            }
        }

        initCurrency();
        initExpressions();
        initBusinessUnits();
        initUserGroupsAndRights();
    }

    private void initCurrency() {
        CurrencyExchangeRatePK cerPK = new CurrencyExchangeRatePK(
                session.getOrganization().getId(),
                new Date(99, 6, 5),
                Currency.EUR.getDbResource().getResourceId(),
                Currency.BGN.getDbResource().getResourceId());
        CurrencyExchangeRate cer = em.find(CurrencyExchangeRate.class, cerPK);
        if(cer == null) {
            cer = new CurrencyExchangeRate(cerPK);
            cer.setExchangeRate(BigDecimal.valueOf(195583, 5));
            cer.setFixedExchangeRate(true);
            em.persist(cer);
        }
    }

    private void initExpressions() {
        Class beanClass = PurchaseInvoiceItem.class;
        String propertyName = "receivedPrice";
        if(session.getExpression(beanClass, propertyName) == null) {
            session.saveExpression(beanClass, propertyName, PurchaseInvoiceItem.RECEIVED_PRICE_CALCULATION_EXPRESSION);
        }
        propertyName = "extendedPrice";
        if(session.getExpression(beanClass, propertyName) == null) {
            session.saveExpression(beanClass, propertyName, PurchaseInvoiceItem.EXTENDED_PRICE_CALCULATION_EXPRESSION);
        }
    }

    private void initBusinessUnits() {
        List<BusinessUnit> businessUnits;
        if((businessUnits = usersService.getChildrenBusinessUnits(null)) != null && businessUnits.size() > 0) {
            return;
        }

        BusinessUnit root = usersService.newEntity(BusinessUnit.class);
        root = usersService.save(root);
        BusinessUnitAddress buAddress = usersService.newItem(root, BusinessUnitAddress.class);
        for(BusinessUnitType buType : BusinessUnitType.values()) {
            if(BusinessUnitType.Administrative.equals(buType)) {
                continue;
            }

            BusinessUnit businessUnit = usersService.newEntity(BusinessUnit.class);
            businessUnit.setBusinessUnitType(buType.getDbResource());
            String name = buType.name();
            businessUnit.setBusinessUnitName(name);
            businessUnit.setDivisionName(name);
            usersService.save(businessUnit);
        }
    }

    private void initUserGroupsAndRights() {
    }

    @Override
    public UserGroup getUserGroupByPositionType() {
        UserOrganization uo = session.getUserOrganization();
        
        UserGroup group = null;
        Address branch = uo.getBranch();
        User user = uo.getUser();
        if (branch != null) {
            ContactPerson contact = null;
            Query q = em.createNamedQuery("ContactPerson.findByPersonAndParentDataObject");
            q.setParameter("person", user.getPerson());
            q.setParameter("parentDataObjectId", branch.getAddressId());
            try {
                contact = (ContactPerson) q.getSingleResult();
            } catch (Exception ex) {
                // No match
            }

            try {
                PositionType positionType = contact.getPositionType();
                throw new UnsupportedOperationException("ToDo");
//                if (positionType != null)
//                    group = positionType.getUserGroup();
            } catch (NullPointerException ex) {
                // ignored
            }
        }

        return group;
    }

    /**
     * UserGroupMember
     * 
     */
    
    @Override
    public UserGroupMember getUserGroupMember(UserGroup userGroup, User user) {
        Query q = em.createNamedQuery(UserGroupMember.NQ_IS_USER_MEMBER_OF_USER_GROUP);
        q.setParameter("userGroup", userGroup);
        q.setParameter("user", getUserOrganization(user));

        try {
            return (UserGroupMember)q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<UserGroupMember> getUserGroupMembers() {
        Query q = em.createNamedQuery(UserGroupMember.NQ_FIND_ALL);
        q.setParameter("organizationId", session.getOrganization().getId());

        return new ArrayList<UserGroupMember>(q.getResultList());
    }

    @Override
    public List<UserGroupMember> getUserGroupMembers(User user) {
        Organization organization = session.getOrganization();
        UserOrganization userOrganization = getUserOrganization(user, organization);
        Query q = em.createNamedQuery(UserGroupMember.NQ_FIND_BY_USER_ORGANIZATION);
        q.setParameter("userOrganization", userOrganization);

        return new ArrayList<UserGroupMember>(q.getResultList());
    }

    @Override
    public List<UserGroupMember> getUserGroupMembers(UserGroup userGroup) {
        Query q = em.createNamedQuery(UserGroupMember.NQ_FIND_BY_USER_GROUP);
        q.setParameter("userGroup", userGroup);

        return new ArrayList<UserGroupMember>(q.getResultList());
    }

    @Override
    public boolean isMemberOf(User user, UserGroup userGroup) {
        Query q = em.createNamedQuery(UserGroupMember.NQ_IS_USER_MEMBER_OF_USER_GROUP);
        q.setParameter("userGroup", userGroup);
        q.setParameter("user", user);

        try {
            q.getSingleResult();
            return true;
        } catch(NoResultException ex) {
            return false;
        }
    }

    @Override
    public boolean isMemberOf(UserGroup userGroup) {
        return isMemberOf(session.getUser(), userGroup);
    }

    @Override
    public UserGroupMember newUserGroupMember() {
        return newUserGroupMember(session.getUser());
    }

    @Override
    public UserGroupMember newUserGroupMember(User user) {
        UserOrganization userOrganization = getUserOrganization(user);
        UserGroupMember userGroupMember = new UserGroupMember();
        userGroupMember.setUserOrganization(userOrganization);
        return userGroupMember;
    }

    @Override
    public UserGroupMember saveUserGroupMember(UserGroupMember userGroupMember) {
        if(userGroupMember.getUserOrganization() == null) {
            userGroupMember.setUserOrganization(session.getUserOrganization());
        }

        esm.persist(em, userGroupMember);

        return userGroupMember;
    }

    @Override
    public void deleteUserGroupMember(UserGroupMember userGroupMember) {
        esm.remove(em, userGroupMember);
    }

    @Override
    public Set<UserGroup> getUserGroups(User user) {
        List<UserGroupMember> groupMembers;
        if((groupMembers = getUserGroupMembers(user)) == null || groupMembers.size() == 0) {
            return Collections.emptySet();
        }

        TreeSet<UserGroup> userGroups = new TreeSet<UserGroup>();
        for(UserGroupMember groupMember : groupMembers) {
            userGroups.add(groupMember.getUserGroup());
        }

        return userGroups;
    }

    @Override
    public Set<UserGroup> getUserGroups() {
        return getUserGroups(session.getUser());
    }

    @Override
    public Set<User> getUsers(UserGroup userGroup) {
        List<UserGroupMember> groupMembers;
        if((groupMembers = getUserGroupMembers(userGroup)) == null || groupMembers.size() == 0) {
            return Collections.emptySet();
        }

        TreeSet<User> userGroups = new TreeSet<User>();
        for(UserGroupMember groupMember : groupMembers) {
            userGroups.add(groupMember.getUserOrganization().getUser());
        }

        return userGroups;
    }

    @Override
    public boolean addUserToUserGroup(User user, UserGroup userGroup) {
        UserGroupMember groupMember;
        if((groupMember = getUserGroupMember(userGroup, user)) != null) {
            return false;
        }

        groupMember = newUserGroupMember(user);
        groupMember.setUserGroup(userGroup);
        esm.persist(em, groupMember);
        return true;
    }

    @Override
    public boolean deleteUserFromUserGroup(User user, UserGroup userGroup) {
        UserGroupMember groupMember;
        if((groupMember = getUserGroupMember(userGroup, user)) == null) {
            return false;
        }

        esm.remove(em, groupMember);
        return true;
    }
}