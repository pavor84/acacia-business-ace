package com.cosmos.acacia.crm.bl.invoice.impl;

import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectLink;
import com.cosmos.acacia.crm.data.Invoice;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author Radoslav Lozanov
 */
@Local
public interface InvoicesListLocal
    extends InvoicesListRemote
{

    DataObjectLink newDataObjectLink(DataObjectBean linkeObject);

    BigDecimal calculateVatValue(Invoice invocie);
}
