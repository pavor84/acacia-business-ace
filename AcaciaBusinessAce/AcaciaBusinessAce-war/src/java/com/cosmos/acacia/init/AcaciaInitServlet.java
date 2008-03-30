/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.init;

import com.cosmos.acacia.crm.bl.impl.AcaciaEjbInitRemote;
import java.io.*;
import java.net.*;

import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Miro
 */
public class AcaciaInitServlet
    extends HttpServlet
{

    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        System.out.println("AcaciaInitServlet.init(config: " + config + ")");
        try
        {
            AcaciaEjbInitRemote ejbInit = InitialContext.doLookup(AcaciaEjbInitRemote.class.getName());
            ejbInit.init();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new ServletException("Can't initialize the EJB stack.", ex);
        }
    }
}
