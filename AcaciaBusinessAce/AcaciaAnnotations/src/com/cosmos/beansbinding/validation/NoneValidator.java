/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.validation;

import java.io.Serializable;
import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Miro
 */
public class NoneValidator
    extends Validator
    implements Serializable
{

    @Override
    public Validator.Result validate(Object value) {
        return null;
    }
}
