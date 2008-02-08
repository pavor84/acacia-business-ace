/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.persistence.id;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;



/**
 *
 * @author miro
 */
public class GUIDSequenceGenerator
    extends SequenceGenerator
{
    private Class returnClass;

    public void configure(Type type, Properties params, Dialect d)
        throws MappingException
    {
        System.out.println("Dialect: " + d);
        super.configure(type, params, d);
        returnClass = type.getReturnedClass();
        System.out.println("returnClass: " + returnClass);
    }

    private static int counter = 0;

    public synchronized Serializable generate(SessionImplementor session, Object obj) 
        throws HibernateException
    {
        //System.out.println("super.generate(session, obj): " + super.generate(session, obj));
        /*if (maxLo < 1) {
                //keep the behavior consistent even for boundary usages
                long val = ( (Number) super.generate(session, obj) ).longValue();
                if (val == 0) val = ( (Number) super.generate(session, obj) ).longValue();
                return IdentifierGeneratorFactory.createNumber( val, returnClass );
        }
        if ( lo>maxLo ) {
                long hival = ( (Number) super.generate(session, obj) ).longValue();
                lo = (hival == 0) ? 1 : 0;
                hi = hival * ( maxLo+1 );
                if ( log.isDebugEnabled() )
                        log.debug("new hi value: " + hival);
        }*/

        return BigInteger.valueOf(System.currentTimeMillis() + (++counter));
    }

    @Override
    public String[] sqlCreateStrings(Dialect dialect)
        throws HibernateException
    {
        String []sqlCreateStrings = super.sqlCreateStrings(dialect);
        System.out.println("sqlCreateStrings: " + Arrays.asList(sqlCreateStrings));
        return sqlCreateStrings;
    }

    @Override
    public String[] sqlDropStrings(Dialect arg0)
        throws HibernateException
    {
        String[] sqlDropStrings = super.sqlDropStrings(arg0);
        System.out.println("sqlDropStrings: " + sqlDropStrings);
        return sqlDropStrings;
    }

    @Override
    public Object generatorKey() {
        Object sequenceName = super.generatorKey();
        System.out.println("generatorKey(sequenceName): " + sequenceName);
        return sequenceName;
    }

    @Override
    public String getSequenceName() {
        String sequenceName = super.getSequenceName();
        System.out.println("sequenceName: " + sequenceName);
        return sequenceName;
    }
    
}
