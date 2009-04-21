/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.bl.TestService;
import javax.ejb.Stateless;

/**
 *
 * @author Miro
 */
@Stateless(mappedName="HelloService")
public class TestServiceBean implements TestService {

    @Override
    public String helloWorld(String name) {
        return "Hello " + name + " !!!";
    }

}
