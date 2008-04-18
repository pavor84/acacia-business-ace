/*
 * A listener used to inform parent forms for modifications
 * in nested tables
 */

package com.cosmos.swingb.listeners;

/**
 *
 * @author Bozhidar Bozhanov
 */
public interface TableModificationListener {

    public void rowDeleted(Object row);
    
    public void rowModified(Object row);
    
    public void rowAdded(Object row);
}
