package com.cosmos.acacia.crm.validation;

import java.io.Serializable;

/**
 * Created	:	19.03.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Store a message key and message arguments which are related
 * to a given target (usually property).
 *
 */
public class ValidationMessage implements Serializable {
    private String target;
    private String messageKey;
    private Serializable[] arguments;
    
    /**
     * 
     */
    public ValidationMessage() {
        super();
    }
    
    /**
     * 
     * @param messageKey
     */
    public ValidationMessage(String target, String messageKey) {
        super();
        this.target = target;
        this.messageKey = messageKey;
    }
    /**
     * 
     * @param messageKey
     * @param arguments
     */
    public ValidationMessage(String messageKey, Serializable[] arguments) {
        super();
        this.messageKey = messageKey;
        this.arguments = arguments;
    }
    /**
     * 
     * @param messageKey
     * @param arguments
     */
    public ValidationMessage(String target, String messageKey, Serializable[] arguments) {
        super();
        this.messageKey = messageKey;
        this.arguments = arguments;
        this.target = target;
    }
    /**
     * Getter for messageKey
     * @return String
     */
    public String getMessageKey() {
        return messageKey;
    }
    /**
     * Setter for messageKey
     * @param messageKey - String
     */
    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    /**
     * Getter for arguments
     * @return Object[]
     */
    public Object[] getArguments() {
        return arguments;
    }
    /**
     * Setter for arguments
     * @param arguments - Object[]
     */
    public void setArguments(Serializable[] arguments) {
        this.arguments = arguments;
    }

    /**
     * Getter for target
     * @return String
     */
    public String getTarget() {
        return target;
    }

    /**
     * Setter for target
     * @param target - String
     */
    public void setTarget(String target) {
        this.target = target;
    }
}
