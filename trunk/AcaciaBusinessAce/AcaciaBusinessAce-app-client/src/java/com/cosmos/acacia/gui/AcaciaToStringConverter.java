/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.gui;

import java.util.Arrays;

import org.jdesktop.beansbinding.ELProperty;

import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.swingb.BeanResourceToStringConverter;

/**
 *
 * @author Miro
 */
public class AcaciaToStringConverter
    extends BeanResourceToStringConverter
{
    /**
     * Default constructor 
     */
    public AcaciaToStringConverter()
    {
    }
    
    @SuppressWarnings("unchecked")
    private ELProperty property;
    
    @SuppressWarnings("unchecked")
    /**
     * Define the string with el property
     */
    public AcaciaToStringConverter(ELProperty elProperty){
        this.property = elProperty;
    }
    
    /**
     * Define the String result with elPropertyExpression.
     * Will throw exception if the string is not valid el property string.
     * @param elPropertyExpression
     */
    public AcaciaToStringConverter(String elPropertyExpression){
        this ( ELProperty.create(elPropertyExpression) );
    }
    
    @Override
    public String[] getPossibleStringsForItem(Object item) {
        String[] superResult = super.getPossibleStringsForItem(item);
        //if custom result - add it to other values
        if ( property!=null ){
            String[] result = Arrays.copyOf(superResult, superResult.length+1);
            result[superResult.length] = getCustomDisplay(item);
            return result;
        //otherwise just return the super result
        }else{
            return superResult;
        }
    }
    
    /**
     * 
     * @param item
     * @return
     */
    @SuppressWarnings("unchecked")
    protected String getCustomDisplay(Object item){
        if ( item==null )
            return "";
        try{
            if (item instanceof String)
                return (String) item;
            
            Object value = property.getValue(item);
            if ( value==null )
                return "";
            else 
                return value.toString();
        }catch (Exception e){
            //notify in logs but don't fail
            e.printStackTrace();
            return super.getPreferredStringForItem(item);
        }
        
    }
    
    @Override
    public String getPreferredStringForItem(Object item) {
        if ( property!=null )
            return
            getCustomDisplay(item);
        else
            return super.getPreferredStringForItem(item);
    }
}
