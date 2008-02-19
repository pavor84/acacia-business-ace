/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.test.bl.DataObjectTest;
import java.util.Date;
import org.junit.runner.JUnitCore;

/**
 *
 * @author Miro
 */
public class Main {

    //@EJB
    //private static ProductSessionRemote prodSess;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(
            "\n*********************************" +
            "\n\t Hello World." +
            "\n\t Current time is: " + new Date() +
            "\n*********************************");

        /*SecurityManager sm = System.getSecurityManager();
        System.out.println("sm: " + sm);
        if (sm == null)
            System.setSecurityManager(new RMISecurityManager());*/

        /*try
        {
            DataObjectHelperRemote doh;
            doh = InitialContext.doLookup(DataObjectHelperRemote.class.getName());
            System.out.println("doh: " + doh);
            String dotName = "Miro";

            DataObjectType dot = new DataObjectType();
            dot.setDataObjectType(dotName);
            doh.persist(dot);
            System.out.println("dot: " + dot);
        }
        catch(Throwable ex)
        {
            ex.printStackTrace();
        }*/


        /*System.out.println("prodSess: " + prodSess);
        if(prodSess != null)
        {
            List data = prodSess.getProducts();
            System.out.println("prodSess.getProducts(): " + data);
            if(data != null)
            {
                System.out.println("data.className: " + data.getClass().getName());
            }
        }*/

        //mainTest();

        /*EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HelloWorldFrame().setVisible(true);
            }
        });*/

        AcaciaApplication.launch(AcaciaApplication.class, args);

    }

    public static void mainTest() {
        String[] tests = new String[]
        {
            DataObjectTest.class.getName()
        };

        JUnitCore.main(tests);
    }
}
