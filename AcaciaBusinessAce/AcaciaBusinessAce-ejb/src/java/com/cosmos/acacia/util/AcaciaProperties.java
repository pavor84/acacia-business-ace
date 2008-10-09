/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.util.Properties;
import java.io.Serializable;

/**
 *
 * @author Miro
 */
public class AcaciaProperties
    extends Properties
    implements Serializable
{
    private static final long serialVersionUID = 3758888654566816952L;

    private String schemaName;


    public AcaciaProperties(String schemaName, Properties defaults)
    {
        super(defaults);
        this.schemaName = schemaName;
    }

    public AcaciaProperties(String schemaName)
    {
        this(schemaName, null);
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }


}
