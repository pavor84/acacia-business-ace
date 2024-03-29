/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.document.BusinessDocument;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;
import java.util.Arrays;

/**
 *
 * @author Miro
 */
public class BusinessDocumentListPanel<E extends BusinessDocument> extends EntityListPanel<E> {

    public BusinessDocumentListPanel(Class<E> entityClass, Classifier classifier) {
        super(entityClass, Arrays.asList(classifier));
    }

    public DocumentStatus getStatus(E entity) {
        DbResource status;
        if(entity == null || (status = entity.getDocumentStatus()) == null) {
            return null;
        }

        return (DocumentStatus)status.getEnumValue();
    }

    public DocumentType getType(E entity) {
        DbResource type;
        if(entity == null || (type = entity.getDocumentType()) == null) {
            return null;
        }

        return (DocumentType)type.getEnumValue();
    }

    @Override
    public boolean canModify(E rowObject) {
        if(!super.canModify(rowObject)) {
            return false;
        }

        return isModifiable((E)rowObject);
    }

    @Override
    public boolean canDelete(E rowObject) {
        if(!super.canDelete(rowObject)) {
            return false;
        }

        return isModifiable((E)rowObject);
    }

    protected boolean isModifiable(E entity) {
        return DocumentStatus.Draft.equals(getStatus(entity));
    }
}
