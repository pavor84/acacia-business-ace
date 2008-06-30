package com.cosmos.swingb.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboListEventListener implements ItemListener {

    private ItemListener nestedListener;

    public ComboListEventListener(ItemListener listener) {
        nestedListener = listener;
    }
    @Override
    public void itemStateChanged(ItemEvent event) {
         if(event.getStateChange() > 0x700)
         {
             nestedListener.itemStateChanged(event);
         }
    }

}
