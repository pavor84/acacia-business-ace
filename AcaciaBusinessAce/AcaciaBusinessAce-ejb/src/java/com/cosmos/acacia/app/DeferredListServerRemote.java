package com.cosmos.acacia.app;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Bozhidar Bozhanov
 *
 */
@Remote
public interface DeferredListServerRemote {

    /**
     * The method serves portions of list data, for a specified listId
     *
     * @param listId the id of the list
     * @param currentClientSize
     * @param requestedElements
     * @return
     */
    public List serve(Integer listId, Integer currentClientSize, Integer requestedElements);

}