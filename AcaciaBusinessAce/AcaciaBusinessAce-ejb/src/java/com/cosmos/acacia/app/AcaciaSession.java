package com.cosmos.acacia.app;

/**
 * Created	:	22.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public interface AcaciaSession {
    public Object getValue(String name);

    public void setValue(String name, Object value);
}
