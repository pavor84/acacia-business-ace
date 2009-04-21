/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl;

import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface TestService {

    String helloWorld(String name);
}
