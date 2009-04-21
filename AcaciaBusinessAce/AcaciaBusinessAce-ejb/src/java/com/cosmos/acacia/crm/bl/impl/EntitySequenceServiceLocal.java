/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.EntitySequence;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Miro
 */
@Local
public interface EntitySequenceServiceLocal {

    long nextValue(BigInteger parentEntityId, int dataObjectTypeId, Long initialValue);

    List<EntitySequence> getEntitySequences(BigInteger entityId);

    EntitySequence getEntitySequence(BigInteger entityId, int dataObjectTypeId);

    EntitySequence saveEntitySequence(EntitySequence entitySequence);
    void deleteEntitySequence(EntitySequence entitySequence);
}
