/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.beans;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Miro
 */
public interface PropertyChangeNotificationBroadcaster {

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
