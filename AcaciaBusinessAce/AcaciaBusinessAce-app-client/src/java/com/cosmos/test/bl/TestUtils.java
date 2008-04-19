package com.cosmos.test.bl;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.security.SecureRandom;
import java.util.Random;

import javax.ejb.EJBException;

import com.cosmos.acacia.crm.validation.ValidationException;

/**
 * Created	:	18.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Some useful methods
 *
 */
public abstract class TestUtils {
    private static Random r = new SecureRandom();
    
    /**
     * Nobody wants to make it concrete
     */
    private TestUtils(){
        
    }
    
    /**
     * Get random string with a given length
     * @param charactersLength
     * @return
     */
    public static String getRandomString(int charactersLength) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < charactersLength; i++) {
            int randInt = r.nextInt(Character.MAX_RADIX);
            b.append(Integer.toString(randInt, Character.MAX_RADIX));
        }
        return b.toString();
    }
    
    /**
     * Return random positive integer between 0 and mod-1 inclusive 
     * @param mod
     * @return
     */
    public static int nextInteger(int mod){
        return r.nextInt(mod);
    }
    
    /**
     * Extract the validation exception ( @see {@link ValidationException} )
     * from a {@link RemoteException} or any other exception.
     * The {@link ValidationException} is some inner cause
     * @param ex
     * @return
     */
    public static ValidationException extractValidationException(Exception ex) {
        Throwable e = ex;
        while ( e!=null ){
            if ( e instanceof ValidationException ){
                return (ValidationException) e;
            }
            else if ( e instanceof ServerException || e instanceof RemoteException ){
                e = e.getCause();
            }
            else if ( e instanceof EJBException )
                e = ((EJBException)e).getCausedByException();
            else
                break;
        }
        return null;
    }
}
