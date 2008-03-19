package com.cosmos.acacia.crm.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created : 17.03.2008
 * 
 * @author Petar Milev
 * @version $Id: $ 
 * 
 *          The exception should be thrown when business logic validation
 *          fails. The exception is associated with many {@link ValidationMessage} 
 *          which provide information about all failed validation checks.
 */
public class ValidationException extends RuntimeException {
    
    private List<ValidationMessage> messages = new ArrayList<ValidationMessage>();

    public ValidationException() {
        super();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ValidationException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        
    }

    /**
     * @param arg0
     */
    public ValidationException(String arg0) {
        super(arg0);
        
    }

    /**
     * @param arg0
     */
    public ValidationException(Throwable arg0) {
        super(arg0);
        
    }

    /**
     * Getter for messages
     * @return List<ValidationMessage>
     */
    public List<ValidationMessage> getMessages() {
        return messages;
    }

    /**
     * Setter for messages
     * @param messages - List<ValidationMessage>
     */
    public void setMessages(List<ValidationMessage> messages) {
        this.messages = messages;
    }
    
    /**
     * Add validation message to this exception.
     * @param msg
     */
    public void addMessage(ValidationMessage msg){
        getMessages().add(msg);
    }
    
    /**
     * Add validation message to this exception.
     * @see ValidationMessage
     * @param target
     * @param messageKey
     */
    public void addMessage(String target, String messageKey){
        ValidationMessage msg = new ValidationMessage(target, messageKey);
        addMessage(msg);
    }
    
    /**
     * Add validation message to this exception.
     * @see ValidationMessage
     * @param target
     * @param messageKey
     */
    public void addMessage(String messageKey){
        ValidationMessage msg = new ValidationMessage(null, messageKey);
        addMessage(msg);
    }
    
    /**
     * Add validation message to this exception.
     * @see ValidationMessage 
     * @param target
     * @param messageKey
     * @param arguments
     */
    public void addMessage(String target, String messageKey, Serializable...arguments){
        ValidationMessage msg = new ValidationMessage(target, messageKey, arguments);
        addMessage(msg);
    }
    
    /**
     * Add validation message to this exception.
     * @see ValidationMessage 
     * @param target
     * @param messageKey
     * @param arguments
     */
    public void addMessage(String messageKey, Serializable...arguments){
        ValidationMessage msg = new ValidationMessage(null, messageKey, arguments);
        addMessage(msg);
    }
}
