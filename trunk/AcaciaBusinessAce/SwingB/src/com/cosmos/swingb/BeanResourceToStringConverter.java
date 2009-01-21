/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb;

import com.cosmos.resource.BeanResource;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.application.Application;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Miro
 */
public class BeanResourceToStringConverter
    extends ObjectToStringConverter
{
    private BeanResource beanResource;

    public BeanResourceToStringConverter()
    {
        beanResource = new BeanResource();
    }

    public BeanResourceToStringConverter(Application application)
    {
        beanResource = new BeanResource(application);
    }

    @Override
    public String[] getPossibleStringsForItem(Object item)
    {
        List<String> values = new ArrayList<String>(3);

        String name = beanResource.getFullNameValue(item);
        if(name != null && name.length() > 0)
            values.add(name);

        name = beanResource.getNameValue(item);
        if(name != null && name.length() > 0 && !values.contains(name))
            values.add(name);

        name = beanResource.getShortNameValue(item);
        if(name != null && name.length() > 0 && !values.contains(name))
            values.add(name);

        return values.toArray(new String[values.size()]);
    }
    
    public String getPreferredStringForItem(Object item)
    {
        return beanResource.getFullNameValue(item);
    }

}
