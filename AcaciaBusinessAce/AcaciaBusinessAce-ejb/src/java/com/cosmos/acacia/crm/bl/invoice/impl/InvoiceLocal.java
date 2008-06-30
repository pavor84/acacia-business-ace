/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.data.Invoice;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author rlozanov
 */
@Local
public interface InvoiceLocal {

    Invoice newInvoice();

    Invoice saveInvoice(Invoice invoice);

    EntityProperties getInvoiceEntityProperties();

    BigDecimal calculateVatValue(Invoice invoice);

    Long calculateNextInvoiceNumber();
    
}
