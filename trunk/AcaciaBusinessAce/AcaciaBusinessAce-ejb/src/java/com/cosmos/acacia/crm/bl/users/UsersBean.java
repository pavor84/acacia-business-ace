/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.users;

import java.math.BigInteger;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.bl.validation.GenericUniqueValidatorLocal;
import com.cosmos.acacia.crm.data.RegistrationCode;
import com.cosmos.acacia.crm.data.User;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * The implementation of handling locations (see interface for more info)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class UsersBean implements UsersRemote, UsersLocal {

    protected static Logger log = Logger.getLogger(UsersBean.class);
    
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private GenericUniqueValidatorLocal<User> validator;

    @Override
    public User login(String username, String password) {
        Query loginQuery = em.createNamedQuery("User.login");
        loginQuery.setParameter("username", username);
        loginQuery.setParameter("password", password);

        User user = (User) loginQuery.getSingleResult();
        if (user == null)
            throw new ValidationException("Login.data.incorrect");

        if (!user.getIsActive())
            throw new ValidationException("Login.account.inactive");

        return user;
    }

    @Override
    public void remindPasswordByEmail(String email) {
        Query q = em.createNamedQuery("User.findByEmail");
        q.setParameter("email", email);

        User user = (User) q.getSingleResult();

        if (user == null)
            throw new ValidationException("ForgottenPassword.email.missing");

        //TODO:
    }

    @Override
    public void remindPasswordByUsername(String username) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestRegistration(String email) {
        BigInteger codeNumber = BigInteger.valueOf((int) (10000 + (Math.random() * 89999)));
        RegistrationCode code = new RegistrationCode();
        code.setEmail(email);
        code.setRegistrationCode(codeNumber);
        esm.persist(em, code);

        sendMail(email, 
                MessageRetriever.get("registrationCodeMessage"),
                MessageRetriever.get("registrationCodeSubject"));
    }

    @Override
    public User signup(User user) {

        esm.persist(em, user);
        user.setCreator(user);
        return user;
    }

    @Override
    public String verifyCode(String code) {
        Query q = em.createNamedQuery("RegistrationCode.findByCode");
        q.setParameter("code", code);

        RegistrationCode rc = (RegistrationCode) q.getSingleResult();
        if (rc == null)
            throw new ValidationException("code.incorrect");

        String email = rc.getEmail();
        esm.remove(em, rc);
        
        return email;
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
    
    
    private static String EMAIL_HOST = "localhost";
    private static String FROM = "admin@acacia.com";
    
    private void sendMail(String email, String content, String subject) throws ValidationException {
        try {
            Properties props = System.getProperties();
            // -- Attaching to default Session, or we could start a new one --
            props.put("mail.smtp.host", EMAIL_HOST);
            Session session = Session.getDefaultInstance(props, null);
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
            
            log.error(ex);
        }
     }
}