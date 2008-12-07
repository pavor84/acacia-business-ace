package com.cosmos.acacia.util;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.gui.AcaciaApplication;

public class ResourceMapUtil {

     public static ResourceMap getResourceMap() {
            ResourceMap resourceMap = Application.getInstance(AcaciaApplication.class)
                .getContext().getResourceMap(Currency.class);

            //initializing...
            resourceMap.getString("");

            return resourceMap;

        }
}
