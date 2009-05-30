/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.annotation;

/**
 * The Flow Mode lets you make the execution of an operation dependent on how
 * the end-user interacts with the task. This property appears for the Control
 * Verification logic unit, the RM Compatible logic unit, and for the Event
 * logic unit.
 * 
 * @author Miro
 */
public enum FlowMode {

    /**
     *  This is the default scanning mode. The engine remains in Step mode only
     * as long as the end-user moves the insertion point from the current
     * parkable control to the previous or next parkable control in the same
     * record using the Next Field and Previous Field events.
     */
    Step,

    /**
     * This option is used when there is a specific destination determined by a
     * mouse-click or a triggered event. Scanning in Fast mode means that the
     * engine scans the record tables, executes only those operations flagged
     * for execution in Fast or Combined Interaction mode, and recomputes Init
     * and Link expressions if needed.
     */
    Fast,

    /**
     * This option lets the you select both Step and Fast.
     */
    Combine,

    /**
     * If you select this option, the engine executes the operation if the
     * end-user zooms (F5) in the previous control. This option is only available
     * for the RM Compatible logic unit.
     */
    Before,

    /**
     * If you select this option, the engine executes the operation if the
     * end-user zooms (F5) in the next control. This option is only available
     * for the RM Compatible logic unit.
     */
    After;
}
