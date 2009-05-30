/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author Miro
 */
public enum UnitType {

    /**
     * Task Prefix and Suffix – These logic units call the operations that the
     * engine executes, at the beginning or ending of the task. The operations
     * for the Task Prefix logic unit are used for the task’s initialization
     * procedures. The operations for the Task Suffix logic unit are used for
     * the task’s exit procedures; update parameters for a calling task, or
     * print report totals.
     */
    Task(LogicUnitType.Prefix, LogicUnitType.Suffix),

    /**
     * Group Prefix and Suffix – These logic units for batch task Call operations
     * that the engine executes after the break parameter value has changed. The
     * Group Prefix logic unit is called at the beginning of a group of records
     * for a batch task when a new value is entered in the break parameter. This
     * logic unit is typically used to print report group header. The Group
     * Suffix logic unit is called when the engine executes operations for a
     * batch task before processing the record with a break parameter value that
     * has changed. This logic unit is typically used for the printing of report
     * group footers.
     */
    Group(LogicUnitType.Prefix, LogicUnitType.Suffix),

    /**
     * Record Prefix and Suffix – The Record Prefix logic unit executes the
     * operations for every record immediately after the record is read from disk
     * and before interaction starts. The Record Prefix operations are used at
     * the initialization stage of a record, such as the procedural operations
     * that are required before the engine can start processing the record’s
     * parameters. The Record Suffix logic unit executes operations used for
     * updating records that have changed. If the record has not changed, the
     * Record Suffix operations will not be executed. Note that the batch task
     * is executed every time Acacia encounters a Record Suffix logic unit.
     */
    Record(LogicUnitType.Prefix, LogicUnitType.Suffix),

    /**
     * Control Prefix, Verification, Suffix – The Control Prefix logic unit
     * executes the operations required for an insertion point to move into a
     * particular control. Control Prefix operations are used at the
     * initialization stage of a specific control type. A Control logic unit can
     * be called only after a Record logic unit. The Control Verification
     * operations are executed when the engine that is parked in a control, as
     * displayed by the insertion point placement, moves away from the control.
     * These operations are also executed when the control is passed through in
     * Fast mode before the Control Suffix logic unit. The Control Suffix logic
     * unit executes operations used for the control’s exit procedure, that is,
     * just before the engine leaves the control to reset, update, or print a
     * control value.
     */
    Control(LogicUnitType.Prefix, LogicUnitType.Verification, LogicUnitType.Suffix),

    /**
     * Variable – This logic unit lets you select a variable from the Variable
     * list that you want to associate with a specific control type. If you
     * select a variable, the Variable Expression property is disabled. For the
     * specified control, you can place the operations that the engine executes.
     * The Variable handler is for online tasks only.
     */
    Variable(LogicUnitType.None),

    /**
     * Event – A non-editable field that lets you zoom to the Event Selection
     * dialog box and displays the selected event. When no event is selected the
     * field is blank. For more information, see User-Defined Events.
     */
    Event(LogicUnitType.None),

    /**
     * Function – Lets you define a user-defined function. For more information,
     * see User-Defined Functions.
     */
    Function(LogicUnitType.None);

    private UnitType(LogicUnitType logicUnitType, LogicUnitType... logicUnitTypes) {
        if (logicUnitTypes != null && logicUnitTypes.length > 0) {
            applicableUnitTypes = EnumSet.<LogicUnitType>of(logicUnitType, logicUnitTypes);
        } else {
            applicableUnitTypes = Collections.emptySet();
        }
    }
    private Set<LogicUnitType> applicableUnitTypes;

    public Set<LogicUnitType> getApplicableUnitTypes() {
        return applicableUnitTypes;
    }

    public boolean isApplicable(LogicUnitType logicUnitType) {
        return applicableUnitTypes.contains(logicUnitType);
    }
}
