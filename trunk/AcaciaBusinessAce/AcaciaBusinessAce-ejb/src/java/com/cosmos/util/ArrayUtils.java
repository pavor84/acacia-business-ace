/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Miro
 */
public class ArrayUtils {

    public static <T> List<T> asList(T... a) {
        List<T> list = new ArrayList<T>();
        list.addAll(Arrays.asList(a));

        return new ArrayList<T>();
    }

    public static <T> Set<T> unmodifiableSet(T... items) {
        HashSet<T> set = new HashSet<T>(Arrays.asList(items));
        return Collections.unmodifiableSet(set);
    }

    public static <T> Set<T> unmodifiableSetByCollections(T firstItem, Collection<T>... collectionItems) {
        HashSet<T> set = new HashSet<T>();
        set.add(firstItem);
        if(collectionItems != null && collectionItems.length > 0) {
            for(Collection<T> collection : collectionItems) {
                set.addAll(collection);
            }
        }

        return Collections.unmodifiableSet(set);
    }
}
