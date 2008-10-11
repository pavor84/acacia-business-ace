/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.callback.assembling.ChoiceCallback;
import com.cosmos.acacia.crm.assembling.ConstraintRow;
import com.cosmos.swingb.DialogResponse;
import java.io.IOException;
import java.util.ArrayList;
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

        List<String> errorMessages = new ArrayList<String>();

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

                //choiceCallback.setSelectedRow(choices.get(0));
                SelectionCallbackPanel callbackPanel = new SelectionCallbackPanel(choiceCallback);
                DialogResponse response = callbackPanel.showDialog();
                System.out.println("response: " + response);
                if(DialogResponse.SELECT.equals(response))
                {
                    List<ConstraintRow> selectedRows = callbackPanel.getSelectedValues();
                    logger.info("selectedRows: " + selectedRows);
                    choiceCallback.setSelectedRows(selectedRows);
                }
            }
        }

        if(errorMessages.size() > 0)
        {
            System.out.println("AssemblerCallbackHandler.handle(" + Arrays.asList(callbacks) + ")");
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
