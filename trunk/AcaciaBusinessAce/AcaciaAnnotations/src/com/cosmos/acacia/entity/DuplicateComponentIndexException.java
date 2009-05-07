/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.entity;

import com.cosmos.acacia.annotation.FormContainer;

/**
 *
 * @author Miro
 */
public class DuplicateComponentIndexException extends EntityFormException {

    private int componentIndex;
    private FormContainer firstFormContainer;
    private FormContainer secondFormContainer;
    private String message;

    public DuplicateComponentIndexException(int componentIndex, FormContainer firstFormContainer, FormContainer secondFormContainer) {
        this.componentIndex = componentIndex;
        this.firstFormContainer = firstFormContainer;
        this.secondFormContainer = secondFormContainer;
    }

    public int getComponentIndex() {
        return componentIndex;
    }

    public FormContainer getFirstFormContainer() {
        return firstFormContainer;
    }

    public FormContainer getSecondFormContainer() {
        return secondFormContainer;
    }

    @Override
    public String toString() {
        if(message == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("index=").append(componentIndex);
            sb.append("; firstContainerName=").append(firstFormContainer.name());
            sb.append("; secondContainerName=").append(secondFormContainer.name());

            message = sb.toString();
        }

        return message;
    }
}
