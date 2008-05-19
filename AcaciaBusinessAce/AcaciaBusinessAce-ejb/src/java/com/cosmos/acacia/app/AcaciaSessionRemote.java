package com.cosmos.acacia.app;

import javax.ejb.Remote;

/**
 * Created	:	19.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Remote
public interface AcaciaSessionRemote {

    public Object getValue(String name);

    public void setValue(String name, Object value);
}
