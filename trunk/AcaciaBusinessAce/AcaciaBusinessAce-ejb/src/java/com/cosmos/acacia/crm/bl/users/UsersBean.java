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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

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
import javax.security.auth.callback.CallbackHandler;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.validation.GenericUniqueValidatorLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.data.RegistrationCode;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.data.UserOrganization;
import com.cosmos.acacia.crm.data.UserOrganizationPK;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.util.Base64Decoder;
import com.cosmos.util.Base64Encoder;

/**
 * The implementation of handling users (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class UsersBean implements UsersRemote, UsersLocal {

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
    private AcaciaSessionLocal acaciaSessionLocal;

    /** Temporary indicator that operations are concerning password change */
    private boolean passwordChange = false;
    
    private static final String INCORRECT_LOGIN_DATA = "Login.data.incorrect";
    private static final String TEMPORARY_LOGIN = "temporaryLogin";
    private static final String LOGIN = "login";
    
    @Override
    public Integer login(String username, char[] password) {
        try {
            return login(username, password, LOGIN);
        } catch (ValidationException ex){
            if (ex.getMessage().equals(INCORRECT_LOGIN_DATA))
                return login(username, password, TEMPORARY_LOGIN);
            else
                throw ex;
        }
    }

    private Integer login(String username, char[] password, String loginType) {
        Query loginQuery = em.createNamedQuery("User." + loginType);
        loginQuery.setParameter("username", username);
        loginQuery.setParameter("password", getHash(new String(password)));
        
        try {
            User user = (User) loginQuery.getSingleResult();
            if (!user.getIsActive())
                throw new ValidationException("Login.account.inactive");
            
            if (loginType.equals(TEMPORARY_LOGIN)) {
                // Checking whether the validity period hasn't expired
                if (System.currentTimeMillis() > user.getSystemPasswordValidity().getTime())
                    throw new ValidationException(INCORRECT_LOGIN_DATA);
                
                user.setNextActionAfterLogin(CHANGE_PASSWORD);
            }
            if (!passwordChange)
                return acaciaSessionLocal.login(user);
            else
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
        
        log.info("Pass: " + tempPassword);
        
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
        BigInteger codeNumber = BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits() / 10000);
        
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

    @Override
    public User signup(User user, Organization organization, Address branch) {

        validator.validate(user, "userName");

        user.setUserPassword(getHash(user.getUserPassword()));
        user.setCreationTime(new Date());
        esm.persist(em, user);
        user.setCreator(user);
        esm.persist(em, user);

        if (organization != null) {
            UserOrganization uo = new UserOrganization();
            UserOrganizationPK pk = new UserOrganizationPK(user.getId(), organization.getId());
            uo.setUserOrganizationPK(pk);
            uo.setBranch(branch);
            esm.persist(em, uo);
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
    public void updateOrganization(User user, CallbackHandler callbackHandler) {
        List<Organization> organizations = getOrganizationsList(user);

        if (organizations.size() > 1) {
           //callbackHandler.handle(null); // add support for the message display
        } else if (organizations.size() == 1) {
            Organization organization = organizations.get(0);
            acaciaSessionLocal.setOrganization(organization);
        } else {
            // free user
        }
    }

    @Override
    public void activateUser(User user, boolean active) {
        user.setIsActive(active);
        esm.persist(em, user);
    }
    
    @Override
    public Organization activateOrganization(Organization organization, boolean active) {
        organization.setActive(active);
        esm.persist(em, organization);
        
        List<User> users = getUsersList(organization);
        for (User u : users) {
            u.setIsActive(active);
            esm.persist(em, u);
        }
        
        return organization;
    }
    private String getHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
        hexString.append(Integer.toHexString(0xFF & array[i]));
    }
        return hexString.toString();
    }
     
    private List<Organization> getOrganizationsList(User user) {
        Query q = em.createNamedQuery("UserOrganization.findByUser");
        q.setParameter("user", user);

        List<UserOrganization> uoList = q.getResultList();
        List<Organization> result = new ArrayList(uoList.size());
        for (UserOrganization uo : uoList) {
            result.add(uo.getOrganization());
        }
        return result;
    }
    
    private List<User> getUsersList(Organization organization) {
        Query q = em.createNamedQuery("UserOrganization.findByOrganization");
        q.setParameter("organization", organization);

        List<UserOrganization> uoList = q.getResultList();
        List<User> result = new ArrayList(uoList.size());
        for (UserOrganization uo : uoList) {
            result.add(uo.getUser());
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
            log.info("pr: |" + password + "|" + result);
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

}