/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.beansbinding.converters;

import com.cosmos.acacia.annotation.ResourceDisplay;
import com.cosmos.resource.BeanResource;
import com.cosmos.resource.EnumResource;
import org.jdesktop.application.Application;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ResourceConverter extends Converter {

    private BeanResource beanResource;
    
    private ResourceDisplay display = ResourceDisplay.ShortName;
    
    public ResourceConverter(Class<? extends Application> applicationClass)
    {
        beanResource = new BeanResource(applicationClass);
    }
    public ResourceConverter(Class<? extends Application> applicationClass,
           ResourceDisplay display)
    {
        this(applicationClass);
        this.display = display;
    }
    
    @Override
    public Object convertForward(Object source) {
        if (display == ResourceDisplay.Name)
            return beanResource.getName((EnumResource) source);
        
        if (display == ResourceDisplay.ShortName)
            return beanResource.getShortName((EnumResource) source);
        
        if (display == ResourceDisplay.FullName)
            return beanResource.getFullName((EnumResource) source);
            
        return null;
    }

    @Override
    public Object convertReverse(Object target) {
        return null; // no reverse conversion needed;
    }

}
