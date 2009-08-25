/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.EntitySequence;
import java.util.UUID;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface EntitySequenceServiceLocal {

    long nextValue(UUID parentEntityId, int dataObjectTypeId, Long initialValue);

    List<EntitySequence> getEntitySequences(UUID entityId);

    EntitySequence getEntitySequence(UUID entityId, int dataObjectTypeId);

    EntitySequence saveEntitySequence(EntitySequence entitySequence);
    void deleteEntitySequence(EntitySequence entitySequence);
}
