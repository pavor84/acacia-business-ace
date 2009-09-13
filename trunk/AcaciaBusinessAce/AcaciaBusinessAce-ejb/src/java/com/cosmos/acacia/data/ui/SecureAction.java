/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.data.ui;

import java.io.Serializable;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jdesktop.application.Task.BlockingScope;

/**
 *
 * @author Miro
 */
public abstract class SecureAction extends AbstractItem implements Serializable {

    public enum Show {
        Frame,
        Dialog
    }

    public static final String ATTR_SHOW = "show";
    //
    private String enabledProperty;
    private String selectedProperty;
    private BlockingScope block;
    private Show show;

    public SecureAction() {
    }

    @Override
    public void readXML(XMLStreamReader xmlReader) throws XMLStreamException {
        super.readXML(xmlReader);

        if(Show.Dialog.name().equalsIgnoreCase(getAttributes(xmlReader).get(ATTR_SHOW))) {
            show = Show.Dialog;
        } else {
            show = Show.Frame;
        }
    }

    public BlockingScope getBlock() {
        return block;
    }

    public String getEnabledProperty() {
        return enabledProperty;
    }

    public String getSelectedProperty() {
        return selectedProperty;
    }

    public Show getShow() {
        return show;
    }
}
