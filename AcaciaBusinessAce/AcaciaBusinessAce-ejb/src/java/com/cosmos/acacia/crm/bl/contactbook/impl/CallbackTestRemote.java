/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.contactbook.impl;

import com.cosmos.acacia.callback.CallbackEnabled;
import javax.ejb.Remote;


/**
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface CallbackTestRemote extends CallbackEnabled {

     int calculate(int a, int b);
}
