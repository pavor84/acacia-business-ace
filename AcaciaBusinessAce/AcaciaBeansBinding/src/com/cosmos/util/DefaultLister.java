package com.cosmos.util;

import java.util.List;

/**
 * Created	:	10.05.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class DefaultLister<T> implements Lister<T>{
    
    /** list */
    private List<T> list;
    
    public DefaultLister(List<T> list){
        setList(list);
    }

    /** @see com.cosmos.util.Lister#getList()
     */
    @Override
    public List<T> getList() {
        return list;
    }

    /**
     * Setter for list
     * @param list - List<T>
     */
    public void setList(List<T> list) {
        this.list = list;
    }
}
