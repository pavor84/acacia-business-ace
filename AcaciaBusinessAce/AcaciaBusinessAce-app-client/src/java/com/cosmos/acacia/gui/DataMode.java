/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.gui;

/**
 *
 * @author Miro
 */
public enum DataMode {

    /**
     * Query mode allows the end-user to scan through records without updating
     * the records or deleting them.
     * When you open a table, the status mode is Modify. In order to switch to
     * Query mode, you must select Query Records from the Options menu.
     * After switching to Query mode, the word 'Query' appears in the status box
     * of the message line.
     */
    Query,

    /**
     * This mode allows the end-user to edit existing records and delete records.
     * When you are in Modify mode, you can also add data by using the Create a
     * Line function. However, the status changes to Create. This is a specific
     * mode in uniPaaS that is called Create in Modify.  You can move to this
     * mode by using the Ctrl+M keyboard shortcut.
     */
    Modify,

    /**
     * The Create mode allows the end-user to create new records.
     * When you are in Create mode, by selecting Create Records from the Options
     * menu, you can only see the rows that you have added in the current
     * session. To view all of the records, you need to switch to Query or
     * Modify mode.
     */
    Create,

    /**
     * This mode is part of the Modify mode. This mode deletes the current
     * record. The mode has no indication in the status line. You can move to
     * this mode by using the F3 keyboard shortcut.
     */
    Delete,

    Expression
    ;
}
