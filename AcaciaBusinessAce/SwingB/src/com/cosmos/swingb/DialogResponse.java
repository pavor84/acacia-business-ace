/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

/**
 *
 * @author miro
 */
public enum DialogResponse {
    YES("Yes"),
    OK("OK"),
    NO("No"),
    CANCEL("Cancel"),
    CLOSE("Close"),
    SAVE("Save"),
    LOGIN("Login"),
    SELECT("Select"),
    ;

    private DialogResponse(String response)
    {
        this.response = response;
    }

    private String response;

    public String getResponse() {
        return response;
    }


}
