package com.cosmos.acacia.app;

import com.cosmos.acacia.crm.data.Organization;
import javax.ejb.Local;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Local
public interface AcaciaSessionLocal
    extends AcaciaSessionRemote
{
    void setOrganization(Organization organization);
}
