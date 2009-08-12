/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui.entity;

import com.cosmos.acacia.gui.DataMode;
import com.cosmos.acacia.crm.data.document.BusinessDocument;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;

/**
 *
 * @author Miro
 */
public class BusinessDocumentPanel<E extends BusinessDocument> extends EntityPanel<E> {

    public BusinessDocumentPanel(AbstractEntityListPanel entityListPanel, E entity) {
        super(entityListPanel, entity);
    }

    public DocumentStatus getStatus() {
        DbResource status;
        if((status = getEntity().getDocumentStatus()) == null) {
            return null;
        }

        return (DocumentStatus)status.getEnumValue();
    }

    public DocumentType getType() {
        DbResource type;
        if((type = getEntity().getDocumentType()) == null) {
            return null;
        }

        return (DocumentType)type.getEnumValue();
    }

    @Override
    public DataMode getDataMode() {
        if(dataMode == null) {
            if(!DocumentStatus.Draft.equals(getStatus())) {
                dataMode = DataMode.Query;
            } else {
                dataMode = DataMode.Modify;
            }
        }

        return super.getDataMode();
    }
}
