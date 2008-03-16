package com.cosmos.swingb;

/**
 * A wrapping class of the DatePickerCellEditor, providing the option
 * to create a new instance with an existing datePicker
 * 
 * @author Bozhidar Bozhanov
 */
import org.jdesktop.swingx.table.DatePickerCellEditor;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class JBDatePickerCellEditor
    extends DatePickerCellEditor 
{

    public JBDatePickerCellEditor(JBDatePicker datePicker)
    {
        super();
        this.datePicker = datePicker;
    }
}
