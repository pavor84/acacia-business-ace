/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface UpdateOperation {

    /**
     * 
     * @return the the variable which will be updated formatted as ELProperty string.
     */
    String variable();

    /**
     * 
     * @return the expression formatted as ELProperty string. The result of this
     * expression is posted to the updated variable.
     */
    String with();

    /**
     * Acacia enables you to incrementally update a variable, meaning that the
     * update expression value is evaluated and added or subtracted from the
     * updated value based on a set of rules (explained below).
     * When you use the Incremental Update, always place it in the Record Suffix
     * section. This way, Acacia executes the Update operation once and only
     * once after the record has been edited.
     * You can use the Incremental Update to accumulate totals in parent tasks
     * or in variables of linked records. In these cases, set the Undo property
     * to N to prevent end-user cancellation of the parent records after the
     * subtask records have already been accepted.
     * This property has the following values:
     * No - The updated variable is replaced by the evaluated value of the
     * update expression. This is the normal way to update a variable.
     * Yes - The incremental update is relevant only for Online tasks. For
     * Incremental, Acacia evaluates the update expression and adds this value
     * to or subtracts this value from the updated variable according to the
     * following rules:
     * (1) If the current mode of operation of the task is "Create", Acacia adds
     * the value of the expression to the updated variable.
     * (2) If the current mode of operation of the task is "Modify", Acacia
     * subtracts the old (a) value of the update expression from the updated
     * variable, then adds to it the new (b) value of the update expression.
     * Old value of the update expression means: the value resulting from
     * the update expression evaluated when the values of its component
     * variables are those present when the record is read.
     * New value of the update expression means: the value resulting from the
     * update expression evaluated when the Update operation is executed.
     * (3) If the current mode of operation of the task is "Delete", Acacia
     * subtracts the value of the update expression from the updated variable.
     * (4) If the updated variable is a variable selected within a link
     * definition, and if the link criteria are changed during the record
     * interaction, resulting in a new linked record being read, Acacia will
     * still maintain the integrity of data as follows: Acacia will decrement
     * the Old value of the update expression from the original variable of the
     * original linked record, and Acacia will increment the new variable from
     * the newly-linked records by the New value of the update expression.
     * For example, in an online Order Entry program running in Modify mode, the
     * user may replace the customer number, which is used as a link expression
     * to the customer's file in order to accumulate the orders' total value. In
     * this case when Acacia executes the Update operation, Acacia subtracts the
     * total for the current (modified) order from the original customer record
     * and adds it to the new customer record, regardless of whether any other
     * changes in the order's total value have been made.
     * (5) As a rule, do not use constants in the update expression for an
     * Incremental Update operation. When the task is in Modify mode, such
     * constants are canceled by the process of decrementing and incrementing.
     * However, such an expression will have full effect while the task is in
     * the Create or Delete mode. For example, the constant 1 may be used as the
     * expression of an Incremental Update operation, to keep count of the
     * number of records in a subtask's data view, where the count is held in a
     * variable selected in a parent task or from a linked table in the current
     * task. As long as the subtask is in Modify mode, no change will occur to
     * the count, but when a record is deleted from the subtask's data view or a
     * new record is created in it, the count will immediately be updated
     * correctly.
     *
     * Example of incremental update usage
     * Assume you have two files: a Receive File, and a linked Item File. You
     * want to update your stock count, which is kept in the Stock field in the
     * linked Item File by the amount of stock you receive, which is kept in the
     * Quantity variable in the Receive File. Stock is variable A and Quantity
     * is variable B. The Operation is: Update A with Expression B in
     * Incremental mode. If the contents of Stock (A1) is 30 when you start,
     * then:
     * In Create Mode, if Quantity (B1)is entered as 5, Stock will end up (A2)
     * as 35.
     * In Modify mode, if Quantity (B1) is currently 5 and you modify (B2) it to
     * 7, Stock will be 35-5+7=37, that is (A2)-(B1)+(B2) = A3.
     * In Delete mode, if you delete the Receiving transaction record and it
     * still has 7 (B2) in the Quantity variable, stock will be 37-7=30, that is
     * (A3)-(B2)=(A4).
     * Assume that you did not yet delete the record with Quantity = 7 and that
     * Stock = 37, and then you change the linked Item Record to another one
     * whose Stock = 50. The result will be: Stock of the original record will
     * be 37-7=30 and Stock of the new linked record will be 50+7=57.
     * This special behavior of Incremental update saves you having to implement
     * additional procedures to preserve data integrity.
     * 
     * @return
     */
    boolean incremental() default false;

    boolean forceUpdate() default false;

    FlowMode flowMode() default FlowMode.Step;

    FlowDirection flowDirection() default FlowDirection.Forward;

    /**
     * Yes (the default) - means that the logical condition is always True and
     * that the execution of the operation depends on the Flow property.
     *
     * No the logical condition is False. At runtime the operation is not executed.
     * 
     * Expression a logical expression to control the execution of the operation. If the
     * expression evaluates to False, the operation is skipped. Zoom to the
     * Expression Editor to type the expression.
     * 
     * @return the boolean value
     */
    String condition() default "Yes";
}
