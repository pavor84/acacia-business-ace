/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import com.cosmos.beansbinding.validation.*;
import java.io.Serializable;

/**
 * Not used
 * @author Bozhidar Bozhanov
 */
@Deprecated
public class PasswordValidator extends BaseValidator implements Serializable {
    
    private char[] horizontalKeyboardSequence = new char[] 
        {'`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=',
         'q','w','e','r','t','y','u','i','o','p','[',']','\\',
         'a','s','d','f','g','h','j','k','l',';',
         'z','x','c','v','b','n','m',',','.','/'};
    
    private char[] verticalKeyboardSequence = new char[] 
        {'1','q','a','z','2','w','s','x','3','e','d','c','4','r','f','v','5','t','g','b','6','y','h','n',
         '7','u','j','m','8','i','k',',','9','o','l','.','0','p',';','/','-','[','\\','=',']'};

    public PasswordValidator() {
    }
}
