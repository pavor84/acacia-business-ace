package com.cosmos.acacia.gui;

/**
 * Created	:	22.04.2008
 * @author	Petar Milev
 * @version $Id: $
 * 
 * Communicate common commands specific for the table panel
 */
public interface TablePanelListener {
    /**
     * Event fired when close button is pressed
     */
    public void tablePanelClose();
    
    /**
     * Event fired when the current selection on the data table 
     * is changed
     */
    public void selectionRowChanged();
    
    /**
     * Meaning that select operation is issued.
     */
    public void selectAction();

    /**
     * Refresh operation was done over the table panel
     */
    public void tableRefreshed();
}
