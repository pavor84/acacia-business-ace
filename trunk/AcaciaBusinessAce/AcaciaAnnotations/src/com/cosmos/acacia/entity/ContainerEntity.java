/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.entity;

import com.cosmos.acacia.annotation.RelationshipType;
import javax.swing.JComponent;

/**
 *
 * @author Miro
 */
public class ContainerEntity {

    private JComponent jContainer;
    private RelationshipType relationshipType;
    private Class entityClass;
    private Class listPanelClass;

    public ContainerEntity(JComponent jContainer, RelationshipType relationshipType,
            Class entityClass, Class listPanelClass) {
        this.jContainer = jContainer;
        this.relationshipType = relationshipType;
        this.entityClass = entityClass;
        this.listPanelClass = listPanelClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public JComponent getJContainer() {
        return jContainer;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public Class getListPanelClass() {
        return listPanelClass;
    }
}
