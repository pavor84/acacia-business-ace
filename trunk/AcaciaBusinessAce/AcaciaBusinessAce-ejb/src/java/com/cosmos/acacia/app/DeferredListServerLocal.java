package com.cosmos.acacia.app;

import java.util.List;

import javax.ejb.Local;

@Local
public interface DeferredListServerLocal extends DeferredListServerRemote {

    /**
     * Adds a list to the available lists for portioned serving
     *
     * @param list
     * @return the id by which the list is identified in requests
     */
    Integer addList(List list);
}
