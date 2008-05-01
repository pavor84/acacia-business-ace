package com.cosmos.test.bl;

/**
 * Created	:	18.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Thrown when a given unit test can not complete its work because
 * of missing needed preset database information or any other reason.
 *
 */
public class UncompleteUnitTestException extends Exception {

    /**
     * 
     */
    public UncompleteUnitTestException() {
        super();
        
    }

    /**
     * @param message
     * @param cause
     */
    public UncompleteUnitTestException(String message, Throwable cause) {
        super(message, cause);
        
    }

    /**
     * @param message
     */
    public UncompleteUnitTestException(String message) {
        super(message);
        
    }

    /**
     * @param cause
     */
    public UncompleteUnitTestException(Throwable cause) {
        super(cause);
        
    }

}
