/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.init;

import com.cosmos.acacia.crm.bl.impl.AcaciaEjbInitRemote;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Miro
 */
public class AcaciaInitServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        System.out.println("AcaciaInitServlet.init(config: " + config + ")");
        try {
            AcaciaEjbInitRemote ejbInit = InitialContext.doLookup(AcaciaEjbInitRemote.class.getName());
            ejbInit.init();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Can't initialize the EJB stack.", ex);
        }
    }
}
