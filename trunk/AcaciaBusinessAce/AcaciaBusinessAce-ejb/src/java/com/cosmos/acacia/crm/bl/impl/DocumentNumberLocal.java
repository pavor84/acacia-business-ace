package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;

import javax.ejb.Local;
import javax.persistence.Query;

import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObjectBean;

/**
 * 
 * Created	:	10.03.2009
 * @author	Petar Milev
 *
 */
@Local
public interface DocumentNumberLocal{
    /**
     * This number is used when generating the first number for a given document.
     * For example the first Purchase Order number issued by a warehouse with index = 7, will 
     * be 7*{@link #documentIndexMulitplicator}
     */
    BigInteger DOCUMENT_INDEX_MULTIPLICATOR = new BigInteger("1000000000");
    
    /**
     * Generates and sets the document number of the given entity.
     * The entity should be saved in the same transaction.
     * @param entity
     */
    void setDocumentNumber(DataObjectBean entity);

    /**
     * Generates the next document number for the given entity, branch and query to find out the current
     * max document number.
     * 
     * @param entityClass
     * @param branch
     * @param maxNumberQuery
     * @return
     */
    BigInteger getNextDocumentNumber(Class<? extends DataObjectBean> entityClass, Address branch, Query maxNumberQuery);
    
    /**
     * Generates the next document number for the given entity, branch and query to find out the current
     * max document number.
     * 
     * @param entityClass
     * @param branch
     * @param maxNumberQueryName
     * @param queryParameters
     * @return
     */
    BigInteger getNextDocumentNumber(Class<? extends DataObjectBean> entityClass, Address branch, String maxNumberQueryName, Object[] queryParameters);
}
