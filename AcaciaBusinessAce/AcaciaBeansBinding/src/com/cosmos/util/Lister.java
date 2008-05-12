package com.cosmos.util;

import java.util.List;

/**
 * Created	:	10.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public interface Lister<T> {
    /**
     * Provides the list
     * @return - not null list.
     */
    List<T> getList();
}
