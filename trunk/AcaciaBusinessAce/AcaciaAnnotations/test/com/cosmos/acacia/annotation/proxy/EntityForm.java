/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation.proxy;

import com.cosmos.acacia.beans.TestBeanB;
import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.beans.TestListPanel;
import com.cosmos.acacia.beans.TestPanel;
import com.cosmos.acacia.entity.EntityAttributes;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Miro
 */
public enum EntityForm implements EntityAttributes<Property> {

    @Property(title="Test Bean",
        parentContainerName="primaryInfo",
        formComponentPair=@FormComponentPair(
            firstComponent=@Component(
                componentClass=JLabel.class,
                text="Test Bean:"
            ),
            secondComponent=@Component(
                componentClass=JComboBox.class
            )
        )
    )
    TestBeanB(
            TestBeanB.class,
            TestPanel.class.getName(),
            TestListPanel.class.getName(),
            "Position Types",
            "name"),
    ;

    private EntityForm(
            Class entityClass,
            String formClassName,
            String listFormClassName,
            String pluralTitle,
            String customDisplaySuffix) {
        this(entityClass, formClassName, listFormClassName,
                pluralTitle, pluralTitle + ":",
                customDisplaySuffix);
    }

    private EntityForm(
            Class entityClass,
            String formClassName,
            String listFormClassName,
            String pluralTitle,
            String pluralLabelName,
            String customDisplaySuffix) {
        this.entityClass = entityClass;
        this.formClassName = formClassName;
        this.listFormClassName = listFormClassName;
        this.pluralTitle = pluralTitle;
        this.pluralLabelName = pluralLabelName;
        this.customDisplaySuffix = customDisplaySuffix;

        try {
            Field field = getClass().getField(name());
            this.property = field.getAnnotation(Property.class);
            if(property == null) {
                throw new NullPointerException("Can not find Property annotation for field '" + field.getName() + "'");
            }
        } catch(NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        } catch(SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
    //
    private Class entityClass;
    private String formClassName;
    private String listFormClassName;
    private String pluralTitle;
    private String pluralLabelName;
    private String customDisplaySuffix;
    private Property property;

    @Override
    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public String getFormClassName() {
        return formClassName;
    }

    @Override
    public String getListFormClassName() {
        return listFormClassName;
    }

    @Override
    public String getPluralLabelName() {
        return pluralLabelName;
    }

    @Override
    public String getPluralTitle() {
        return pluralTitle;
    }

    @Override
    public String getCustomDisplaySuffix() {
        return customDisplaySuffix;
    }

    @Override
    public Property getAnnotation() {
        return property;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name());
        sb.append("; annotation=").append(getAnnotation());
        sb.append(", formClassName=").append(getFormClassName());
        sb.append(", listFormClassName=").append(getListFormClassName());
        sb.append(", customDisplaySuffix=").append(getCustomDisplaySuffix());
        return sb.toString();
    }

    private static Map<String, EntityAttributes<Property>> entityAttributesMap;

    public static Map<String, EntityAttributes<Property>> getEntityAttributesMap() {
        if(entityAttributesMap == null) {
            entityAttributesMap = new TreeMap<String, EntityAttributes<Property>>();
            for(EntityForm entityForm : values()) {
                entityAttributesMap.put(entityForm.getEntityClass().getName(), entityForm);
            }
        }

        return entityAttributesMap;
    }
}
