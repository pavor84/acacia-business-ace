package com.cosmos.acacia.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class DeferredListServerBean
    implements DeferredListServerRemote, DeferredListServerLocal {

    private static final String LISTS_KEY = "LISTS_KEY";

    @EJB
    AcaciaSessionLocal session;

    private SecureRandom random = new SecureRandom();

    private Map<Integer, List> savedLists;

    @Override
    public List serve(Integer listId, Integer currentClientSize, Integer requestedElements)  {
        Map<Integer, List> lists = getLists();
        List list = lists.get(listId);
        int toIndex = currentClientSize + requestedElements;
//        if (list == null)
//            return new ArrayList();

        if (toIndex > list.size())
            toIndex = list.size();

        // If there are no more elements to return after this call
        // remove the list from the map
        if (toIndex == list.size() || currentClientSize >= list.size()) {
            lists.remove(listId);
            session.put(LISTS_KEY, list);

            if (currentClientSize >= list.size())
                return new ArrayList();
        }

        System.out.println("AA: " + list.size() + ":" + currentClientSize + ":" + toIndex);
        return new ArrayList(list.subList(currentClientSize, toIndex));
    }


    @Override
    public Integer addList(List list) {

        Map<Integer, List> lists = getLists();
        Integer id = random.nextInt();
        while (lists.containsKey(id)){
            id = random.nextInt();
        }

        lists.put(id, list);

        session.put(LISTS_KEY, lists);

        return id;
    }

    private Map<Integer, List> getLists() {
        if (savedLists == null)
            savedLists = (Map<Integer, List>) session.get(LISTS_KEY);

        if (savedLists == null)
            savedLists = new HashMap<Integer, List>();

        return savedLists;
    }

}
