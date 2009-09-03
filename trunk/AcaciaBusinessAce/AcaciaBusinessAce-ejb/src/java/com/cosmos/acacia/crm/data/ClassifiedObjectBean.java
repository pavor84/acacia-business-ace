/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.util.PersistentEntity;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Bozhidar Bozhanov
 */

public class ClassifiedObjectBean implements Serializable, PersistentEntity<ClassifiedObjectBean, UUID> {

    private UUID id;

    @Property(title="Title")
    private String title;
    
    @Property(title="Type")
    private String type;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public ClassifiedObjectBean clone() {
        try {
            return (ClassifiedObjectBean)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
