/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponent;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Layout;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.beans.PropertyChangeNotificationBroadcaster;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTabbedPane;
import com.cosmos.swingb.JBTextPane;
import com.cosmos.util.ImageUtils;
import java.awt.BorderLayout;
import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.persistence.Transient;

/**
 *
 * @author Miro
 */
@Form(
    mainContainer=@FormContainer(
        name=DataObjectBean.MAIN_TABBED_PANE,
        container=@Component(
            componentClass=JBTabbedPane.class
        )
    ),
    formContainers={
        @FormContainer(
            name=DataObjectBean.PRIMARY_INFO,
            title="Primary Info",
            container=@Component(
                componentClass=JBPanel.class
            ),
            componentIndex=1
        ),
        @FormContainer(
            name=DataObjectBean.NOTES,
            title="Notes",
            container=@Component(
                componentClass=JBPanel.class
            ),
            layout=@Layout(layoutClass=BorderLayout.class),
            componentIndex=DataObjectBean.NOTES_PANEL_INDEX
        )
    }
)
public abstract class DataObjectBean implements DataObjectEntity,
        PropertyChangeNotificationBroadcaster, Comparable<DataObjectBean> {

    public static final String MAIN_TABBED_PANE = "mainTabbedPane";
    public static final String PRIMARY_INFO = "primaryInfo";
    public static final String NOTES = "notes";
    //
    public static final int NOTES_PANEL_INDEX = Property.CUSTOM_INDEX_VALUE + 30000 * Property.STEP_VALUE;
    public static final int NOTES_COMPONENT_INDEX = NOTES_PANEL_INDEX + Property.STEP_VALUE;
    //
    private transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private URI smallImageURI;
    private Image smallImage;
    private URI mediumImageURI;
    private Image mediumImage;
    //
    @Transient
    @Property(title="Notes",
        formComponent=@FormComponent(
            component=@Component(
                componentClass=JBTextPane.class,
                componentConstraints=BorderLayout.CENTER,
                scrollable=true
            ),
            parentContainerName="notes"
        ), index=NOTES_COMPONENT_INDEX
    )
    private String notes;

    @Override
    public abstract DataObject getDataObject();

    public abstract void setDataObject(DataObject dataObject);

    @Override
    public abstract UUID getId();

    public abstract void setId(UUID id);

    public abstract UUID getParentId();
    //public abstract void setParentId(UUID parentId);

    public void setParentId(UUID parentId) {
        getEntityDataObject().setParentDataObjectId(parentId);
    }

    public abstract String getInfo();

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (oldValue == newValue || (oldValue == null && newValue == null)) {
            return;
        }

        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
        if (oldValue == newValue) {
            return;
        }

        firePropertyChange(propertyName, new Integer(oldValue), new Integer(newValue));
    }

    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        if (oldValue == newValue) {
            return;
        }

        firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
    }

    protected DataObjectBean getDataObject(ClassifiedObject classifiedObject) {
        // TODO: Retreive the classified object from LinkedDataObjectId
        if (classifiedObject != null) {
        }

        return null;
    }

    private DataObject getEntityDataObject() {
        DataObject dataObject;
        if ((dataObject = getDataObject()) == null) {
            dataObject = new DataObject();
            setDataObject(dataObject);
        }

        return dataObject;
    }

    public Integer getOrderPosition() {
        return getEntityDataObject().getOrderPosition();
    }

    public void setOrderPosition(Integer orderPosition) {
        getEntityDataObject().setOrderPosition(orderPosition);
    }

    public String getNotes() {
        return getEntityDataObject().getNotes();
    }

    public void setNotes(String notes) {
        getEntityDataObject().setNotes(notes);
    }

    public URI getSmallImageURI() {
        if (smallImageURI == null) {
            String uriString;
            if ((uriString = getEntityDataObject().getSmallImageUri()) != null &&
                    (uriString = uriString.trim()).length() > 0) {
                try {
                    smallImageURI = new URI(uriString);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return smallImageURI;
    }

    public void setSmallImageURL(URL imageURL) {
        try {
            if (imageURL != null) {
                setSmallImageURI(imageURL.toURI());
            } else {
                setSmallImageURI(null);
            }
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setSmallImageURI(URI smallImageURI) {
        this.smallImageURI = smallImageURI;
        if (smallImageURI != null) {
            getEntityDataObject().setSmallImageUri(smallImageURI.toASCIIString());
        } else {
            getEntityDataObject().setSmallImageUri(null);
        }
    }

    public Image getSmallImage() {
        if (smallImage == null) {
            byte[] ba;
            if ((ba = getEntityDataObject().getSmallImage()) != null && ba.length > 0) {
                try {
                    smallImage = ImageIO.read(new ByteArrayInputStream(ba));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                URI imageURI;
                if ((imageURI = getSmallImageURI()) != null) {
                    try {
                        smallImage = ImageIO.read(imageURI.toURL());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        return smallImage;
    }

    public void setSmallImage(Image smallImage) {
        this.smallImage = smallImage;
        if (smallImage != null) {
            try {
                byte[] ba = ImageUtils.toByteArray(smallImage);
                getEntityDataObject().setSmallImage(ba);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            getEntityDataObject().setSmallImage(null);
        }
    }

    public URI getMediumImageURI() {
        if (mediumImageURI == null) {
            String uriString;
            if ((uriString = getEntityDataObject().getMediumImageUri()) != null &&
                    (uriString = uriString.trim()).length() > 0) {
                try {
                    mediumImageURI = new URI(uriString);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return mediumImageURI;
    }

    public void setMediumImageURL(URL imageURL) {
        try {
            if (imageURL != null) {
                setMediumImageURI(imageURL.toURI());
            } else {
                setMediumImageURI(null);
            }
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setMediumImageURI(URI mediumImageURI) {
        this.mediumImageURI = mediumImageURI;
        if (mediumImageURI != null) {
            getEntityDataObject().setMediumImageUri(mediumImageURI.toASCIIString());
        } else {
            getEntityDataObject().setMediumImageUri(null);
        }
    }

    public Image getMediumImage() {
        if (mediumImage == null) {
            byte[] ba;
            if ((ba = getEntityDataObject().getMediumImage()) != null && ba.length > 0) {
                try {
                    mediumImage = ImageIO.read(new ByteArrayInputStream(ba));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                URI imageURI;
                if ((imageURI = getMediumImageURI()) != null) {
                    try {
                        mediumImage = ImageIO.read(imageURI.toURL());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        return mediumImage;
    }

    public void setMediumImage(Image mediumImage) {
        this.mediumImage = mediumImage;
        if (mediumImage != null) {
            try {
                byte[] ba = ImageUtils.toByteArray(mediumImage);
                getEntityDataObject().setMediumImage(ba);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            getEntityDataObject().setMediumImage(null);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append("[id=").append(getId()).append("]@");
        sb.append(Integer.toHexString(super.hashCode()));

        return sb.toString();
    }

    @Override
    public int hashCode() {
        UUID id;
        if((id = getId()) != null) {
            return id.hashCode();
        }

        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (object == null || !(object instanceof DataObjectBean)) {
            return false;
        }
        if(!getClass().isAssignableFrom(object.getClass())) {
            return false;
        }
        DataObjectBean other = (DataObjectBean) object;
        UUID thisId = getId();
        UUID otherId = other.getId();
        if((thisId == null && otherId != null) || (thisId != null && !thisId.equals(otherId))) {
            return false;
        }
        return true;
    }

    @Override
    public DataObjectBean clone() {
        try {
            return (DataObjectBean)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int compareTo(DataObjectBean other) {
        return getId().compareTo(other.getId());
    }
}
