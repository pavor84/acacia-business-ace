/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import com.cosmos.acacia.crm.bl.impl.ProductSessionRemote;
import com.cosmos.acacia.crm.gui.test.HelloWorldFrame;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

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

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HelloWorldFrame().setVisible(true);
            }
        });

    }

}
