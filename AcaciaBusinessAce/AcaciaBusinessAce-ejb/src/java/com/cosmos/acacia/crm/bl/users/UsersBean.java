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

import com.cosmos.acacia.app.AcaciaSession;
import com.cosmos.acacia.app.Session;
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
import java.util.ArrayList;

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

    @Override
    public User login(String username, char[] password) {
        log.info("Locale is : " + locale);
        Query loginQuery = em.createNamedQuery("User.login");
        loginQuery.setParameter("username", username);
        loginQuery.setParameter("password", getHash(new String(password)));

        try {
            User user = (User) loginQuery.getSingleResult();
            if (!user.getIsActive())
                throw new ValidationException("Login.account.inactive");

            Session.getSession().setValue(AcaciaSession.USER_KEY, user);
            return user;
        } catch (NoResultException ex){
            throw new ValidationException("Login.data.incorrect");
        }
    }

    @Override
    public void remindPasswordByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail");
        q.setParameter("email", email);

        try {
            User user = (User) q.getSingleResult();
            //TODO: send a temp password
        } catch (NoResultException ex) {
            throw new ValidationException("ForgottenPassword.email.missing");
        }
    }

    @Override
    public void remindPasswordByUsername(String username) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestRegistration(String email) {
        //TODO: better scheme of forming the code
        //BigInteger codeNumber = BigInteger.valueOf((int) (100000 + (Math.random() * 899999)));
        BigInteger codeNumber = BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits() / 1000);

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
            Session.getSession().setValue(AcaciaSession.ORGANIZATION_KEY, organization);
        } else {
            // free user
        }
    }

    @Override
    public void activateUser(User user, boolean active) {
        user.setIsActive(active);
        esm.persist(em, user);
        
        List<Organization> organizations = getOrganizationsList(user);
        for (Organization o : organizations) {
            o.setActive(active);
            esm.persist(em, o);
        }
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
            return getHexString(digest.digest());
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

}