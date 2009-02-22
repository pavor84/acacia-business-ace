/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.util.EventListener;

/**
 *
 * @author Miro
 */
public interface TextListener extends EventListener {

    void textChanged(TextEvent event);
}
