/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import com.cosmos.acacia.crm.bl.contactbook.validation.PersonValidatorLocal;
import com.cosmos.acacia.crm.bl.impl.ClassifiersLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.validation.GenericUniqueValidatorLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.ContactPerson;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.Person;
import com.cosmos.acacia.crm.data.PositionType;
import com.cosmos.acacia.crm.data.RegistrationCode;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserGroup;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserOrganizationPK;
import com.cosmos.acacia.crm.data.assembling.AssemblingMessage;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.util.Base64Decoder;
import com.cosmos.util.Base64Encoder;
import java.util.Collection;
import java.util.UUID;

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
    private GenericUniqueValidatorLocal<User> validator;

    @EJB
    private PersonValidatorLocal personValidator;

    @EJB
    private AcaciaSessionLocal acaciaSessionLocal;

    @EJB
    private CallbackLocal callbackHandler;

    @EJB
    private AssemblingLocal assemblingLocal;

    @EJB
    private ClassifiersLocal classifiersManager;


    /** Temporary indicator that operations are concerning password change */
    private boolean passwordChange = false;

    private static final String INCORRECT_LOGIN_DATA = "Login.data.incorrect";
    private static final String TEMPORARY_LOGIN = "temporaryLogin";
    private static final String LOGIN = "login";

    @Override
    public UUID login(String username, char[] password) {
        try {
            return login(username, password, LOGIN);
        } catch (ValidationException ex){
            if (ex.getMessage().equals(INCORRECT_LOGIN_DATA))
                return login(username, password, TEMPORARY_LOGIN);

            throw ex;
        }
    }

    private UUID login(String username, char[] password, String loginType) {
        Query loginQuery = em.createNamedQuery("User." + loginType);
        loginQuery.setParameter("username", username);
        loginQuery.setParameter("password", getHash(new String(password)));

        try {
            User user = (User) loginQuery.getSingleResult();
//            if (!user.getIsActive())
//                throw new ValidationException("Login.account.inactive");

            if (loginType.equals(TEMPORARY_LOGIN)) {
                // Checking whether the validity period hasn't expired
                if (System.currentTimeMillis() > user.getSystemPasswordValidity().getTime())
                    throw new ValidationException(INCORRECT_LOGIN_DATA);

                user.setNextActionAfterLogin(CHANGE_PASSWORD);
            }
            if (!passwordChange)
                return acaciaSessionLocal.login(user);

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
        user.setSystemPassword(getHash(tempPassword));
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
        //TODO: better scheme of forming the code

        SecureRandom random = new SecureRandom();
        BigInteger codeNumber = BigInteger.valueOf(random.nextInt(10000000));

        Query q = em.createNamedQuery("User.findByEmail");
        q.setParameter("email", email);
        if (q.getResultList() != null && q.getResultList().size() > 0)
            throw new ValidationException("email.taken");

        RegistrationCode code = new RegistrationCode();
        code.setEmail(email);
        code.setRegistrationCode(codeNumber);

        try {
            sendMail(email,
                    MessageRetriever.get("registrationCodeMessage", locale) + codeNumber.intValue(),
                    MessageRetriever.get("registrationCodeSubject", locale));
        } catch (ValidationException ex){
            throw ex;
        }

        esm.persist(em, code);

    }

    @SuppressWarnings("null")
    @Override
    public User signup(User user, Organization organization, Address branch, Person person) {

        validator.validate(user, "userName");
        personValidator.validate(person);

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

        user.setUserPassword(getHash(user.getUserPassword()));
        user.setPerson(person);
        user.setCreationTime(new Date());
        esm.persist(em, user);
        user.setCreator(user);
        esm.persist(em, user);


        if (organization != null) {

            UserOrganization uo = new UserOrganization();
            UserOrganizationPK pk = new UserOrganizationPK(user.getId(), organization.getId());
            uo.setUserOrganizationPK(pk);
            uo.setBranch(branch);
            //uo.setUserGroup(branch.getUserGroup());
            uo.setUserActive(false);

            esm.persist(em, uo);

            if (organization.isActive() && (branch == null || person == null))
                throw new ValidationException("User.err.branchAndPersonRequired");

            if (branch != null) {
                ContactPerson cp = new ContactPerson();
                cp.setParentId(branch.getId());
                cp.setContact(person);
                esm.persist(em,cp);
            }
        }

        return user;
    }

    @Override
    public String verifyCode(String code) {
        Query q = em.createNamedQuery("RegistrationCode.findByCode");
        try {
            q.setParameter("code", new BigInteger(code));
        } catch (Exception ex){
            throw new ValidationException("code.incorrect");
        }

        try {
            RegistrationCode rc = (RegistrationCode) q.getSingleResult();
            String email = rc.getEmail();
            esm.remove(em, rc);

            return email;
        } catch (NoResultException ex){
            throw new ValidationException("code.incorrect");
        }
    }


    @Override
    public User createUser() {
        return new User();
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

        List<Organization> organizations = getOrganizationsList(user);
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
                UserOrganization uo = em.find(UserOrganization.class,
                        new UserOrganizationPK(user.getId(), organization.getId()));
                if (uo.isUserActive()){
                    acaciaSessionLocal.setOrganization(organization);
                    acaciaSessionLocal.setBranch(uo.getBranch());
                    acaciaSessionLocal.setPerson(user.getPerson());

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
    public User activateUser(User user, BigInteger parentId, Boolean active) {
        Organization o = em.find(Organization.class, parentId);
        if (o != null) {
            UserOrganization uo = em.find(UserOrganization.class,
                    new UserOrganizationPK(user.getId(), o.getId()));

            uo.setUserActive(active);
            esm.persist(em, uo);
        }

        user.setActive(active);
        return user;
    }

    @Override
    public Organization activateOrganization(Organization organization, Boolean active) {
        organization.setActive(active);
        esm.persist(em, organization);

        return organization;
    }
    private String getHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
        hexString.append(Integer.toHexString(0xFF & array[i]));
    }
        return hexString.toString();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Organization> getOrganizationsList(User user) {
        if (user == null)
            user = acaciaSessionLocal.getUser();

        Query q = em.createNamedQuery("UserOrganization.findByUser");
        q.setParameter("user", user);

        List<UserOrganization> uoList = q.getResultList();
        List<Organization> result = new ArrayList(uoList.size());
        for (UserOrganization uo : uoList) {
            result.add(uo.getOrganization());
        }
        return result;
    }

    private char[] saltChars = new char[] {'!', 'b', '0', 'z', 'h', 'o'};

    private String saltPassword(String password) {
        StringBuffer sb = new StringBuffer();
        char[] chars = password.toCharArray();
        int crystal = 0;
        for (int i = 0; i < chars.length; i ++) {
            sb.append(chars[i]);
            if (i % 2 == 0 && crystal < saltChars.length) {
                sb.append(saltChars[crystal++]);
            }
        }

        return sb.toString();
    }

     private String getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            password = saltPassword(password);
            digest.update(password.getBytes());
            String result = getHexString(digest.digest());
            return result;
        } catch (NoSuchAlgorithmException e) {
            log.error("Hashing algorithm not found");
            return password;
        }
    }

    private static String EMAIL_HOST = "localhost";
    private static String FROM = "admin@acacia.com";

    private void sendMail(String email, String content, String subject) throws ValidationException {
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.smtp.host", EMAIL_HOST);
            javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
            // -- Create a new message --
            Message msg = new MimeMessage(session);
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(FROM));
            msg.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(email, false));

            msg.setSubject(subject);
            msg.setText(content);
            // -- Set some other header information --
            msg.setHeader("X-Mailer", "Acacia email");
            msg.setSentDate(new Date());
            // -- Send the message --
            Transport.send(msg);
        }
        catch (Exception ex)
        {
            if (ex instanceof AddressException)
                throw new ValidationException("email.invalid");

            if (ex instanceof MessagingException)
                log.error(ex); // throw validation exception
        }
     }

    @Override
    public void changePassword(char[] oldPassword, char[] newPassword) {
        try {
            passwordChange = true;

            User user = acaciaSessionLocal.getUser();
            // try to login; if the old pass is incorrect, exception will be thrown
            login(user.getUserName(), oldPassword);

            user.setUserPassword(getHash(new String(newPassword)));
            user.setSystemPassword(null);
            esm.persist(em, user);
        } catch (ValidationException ex) {
            passwordChange = false;
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers(BigInteger parentDataObjectId) {
        List<User> result = null;

        if (parentDataObjectId != null) {
            Organization o = em.find(Organization.class, parentDataObjectId);
            Query q = em.createNamedQuery("UserOrganization.findByOrganization");
            q.setParameter(ORGANIZATION_KEY, o);
            List<UserOrganization> uoList = q.getResultList();
            result = new ArrayList<User>(uoList.size());
            for (UserOrganization uo : uoList) {
                User u = uo.getUser();
                if (uo.getBranch() != null)
                    u.setBranchName(uo.getBranch().getAddressName());

                u.setActive(uo.isUserActive());

                result.add(u);
            }
        } else {
            Query q = em.createNamedQuery("User.findAll");
            result = new ArrayList<User>(q.getResultList());
        }

        return result;
    }

    @Override
    public void joinOrganization(Organization organization, Address branch) {
        User user = acaciaSessionLocal.getUser();
        if (user != null && organization != null) {
            UserOrganization uo = new UserOrganization();
            UserOrganizationPK pk = new UserOrganizationPK(user.getId(), organization.getId());
            uo.setUserOrganizationPK(pk);
            uo.setBranch(branch);
            //uo.setUserGroup(branch.getUserGroup());
            esm.persist(em, uo);
        }
    }

    @Override
    public void leaveOrganization(Organization organization) {
        User user = acaciaSessionLocal.getUser();
        List<Organization> list = getOrganizationsList(user);
        if (list != null && list.size() == 1)
            throw new ValidationException("Leave.impossible");

        UserOrganization uo = new UserOrganization();
        uo.setUserOrganizationPK(new UserOrganizationPK(user.getId(), organization.getId()));
        esm.remove(em, uo);
    }

    @Override
    public int deleteUser(User user) {
        return esm.remove(em, user);
    }

    @Override
    public void setOrganization(Organization organization) {
        User user = acaciaSessionLocal.getUser();
        if (user != null) {
            if (acaciaSessionLocal.getOrganization() == null)
                acaciaSessionLocal.setOrganization(organization);

            if (organization != null) {
                UserOrganization uo = getUserOrganization(user, organization);
                if (uo.isUserActive() && uo.getOrganization().isActive()){
                    acaciaSessionLocal.setOrganization(organization);
                    acaciaSessionLocal.setBranch(uo.getBranch());
                    acaciaSessionLocal.setPerson(user.getPerson());
                } else {
                    throw new ValidationException("Login.account.inactive");
                }
            } else {
                // TODO: free user
            }
        }
    }

    @Override
    public UserOrganization getUserOrganization(User user, Organization organization) {
        UserOrganization uo = em.find(UserOrganization.class,
                        new UserOrganizationPK(user.getId(), organization.getId()));
        return uo;
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
        Query q = em.createNamedQuery("User.findByPerson");
        q.setParameter("person", person);

        try {
            User u = (User) q.getSingleResult();
            return getUserOrganization(u, acaciaSessionLocal.getOrganization());
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
            System.out.println("classifierGroup: " + classifierGroup);
            String code = classifierGroup.getClassifierGroupCode();
            ClassifierGroup entity = classifiersManager.getClassifierGroup(code);
            if(entity == null) {
                entity = (ClassifierGroup)classifierGroup.clone();
                entity = classifiersManager.saveClassifierGroupLocal(entity);
            }

            if(systemCode.equalsIgnoreCase(code))
                systemClassifierGroup = entity;
        }

        Collection<Classifier>constantClassifiers = Classifier.ConstantsMap.values();
        for(Classifier classifier : constantClassifiers) {
            System.out.println("classifier: " + classifier);
            String code = classifier.getClassifierCode();
            Classifier entity;
            if((entity = classifiersManager.getClassifier(code)) == null) {
                entity = (Classifier)classifier.clone();
                entity.setClassifierGroup(systemClassifierGroup);
                entity = classifiersManager.saveClassifierLocal(entity, null);
            }
        }
    }

    @Override
    public UserGroup getUserGroupByPositionType() {
        UserOrganization uo = getUserOrganization(acaciaSessionLocal.getUser(),
                acaciaSessionLocal.getOrganization());
        
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
                if (positionType != null)
                    group = positionType.getUserGroup();
            } catch (NullPointerException ex) {
                // ignored
            }
        }

        return group;
    }
}