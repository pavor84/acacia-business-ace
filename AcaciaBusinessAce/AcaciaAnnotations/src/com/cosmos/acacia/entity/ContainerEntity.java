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

    public ContainerEntity(JComponent jContainer, RelationshipType relationshipType, Class entityClass) {
        this.jContainer = jContainer;
        this.relationshipType = relationshipType;
        this.entityClass = entityClass;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContainerEntity[jContainer=[");
        if(jContainer == null) {
            sb.append(jContainer);
        } else {
            sb.append("class=").append(jContainer.getClass());
            sb.append(", name=").append(jContainer.getName());
        }
        sb.append("], relationshipType=").append(relationshipType);
        sb.append(", entityClass=").append(entityClass);
        sb.append("]");

        return sb.toString();
    }
}
