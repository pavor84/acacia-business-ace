/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.callback.assembling.ValueInputCallback;
import com.cosmos.acacia.crm.assembling.ConstraintRow;
import com.cosmos.swingb.DialogResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.log4j.Logger;

/**
 *
 * @author Miro
 */
public class AssemblerCallbackHandler
    implements CallbackHandler
{
    private static final Logger logger = Logger.getLogger(AssemblerCallbackHandler.class);

    @Override
    public void handle(Callback[] callbacks)
        throws IOException,
        UnsupportedCallbackException
    {
        logger.info("handle(" + Arrays.asList(callbacks) + ").");

        if(callbacks == null || callbacks.length == 0)
            throw new IllegalArgumentException("At least one callback must be passed.");

        for(Callback callback : callbacks)
        {
            if(callback instanceof ChoiceCallback)
            {
                ChoiceCallback choiceCallback = (ChoiceCallback)callback;
                List<ConstraintRow> choices = choiceCallback.getChoices();
                if(choices == null || choices.size() == 0)
                    throw new IllegalArgumentException("Invalid ChoiceCallback: " + 
                        choiceCallback.getAssemblingSchemaItem() +
                        ". The choices must contain at least one element.");

                SelectionCallbackPanel callbackPanel = new SelectionCallbackPanel(choiceCallback);
                DialogResponse response = callbackPanel.showDialog();
                if(DialogResponse.SELECT.equals(response))
                {
                    List<ConstraintRow> selectedRows = callbackPanel.getSelectedValues();
                    logger.info("selectedRows: " + selectedRows);
                    choiceCallback.setSelectedRows(selectedRows);
                }
            }
            else if(callback instanceof ValueInputCallback)
            {
                ValueInputCallback inputCallback = (ValueInputCallback)callback;
                InputCallbackPanel callbackPanel = new InputCallbackPanel(inputCallback);
                DialogResponse response = callbackPanel.showDialog();
                if(DialogResponse.OK.equals(response))
                {
                    Serializable value = (Serializable)callbackPanel.getSelectedValue();
                    logger.info("value: " + value);
                    inputCallback.setValue(value);
                }
            }
            else
            {
                throw new UnsupportedCallbackException(callback);
            }
        }
    }
}
