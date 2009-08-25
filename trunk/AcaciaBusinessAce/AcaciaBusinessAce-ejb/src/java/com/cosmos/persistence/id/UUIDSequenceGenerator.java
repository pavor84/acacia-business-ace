/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.persistence.id;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
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
public class UUIDSequenceGenerator extends SequenceGenerator {

    private Class returnClass;

    @Override
    public void configure(Type type, Properties params, Dialect dialect)
            throws MappingException {
        System.out.println("Dialect: " + dialect);
        super.configure(type, params, dialect);
        returnClass = type.getReturnedClass();
        System.out.println("returnClass: " + returnClass);
    }
    private static int counter = 0;

    @Override
    public synchronized Serializable generate(SessionImplementor session, Object obj)
            throws HibernateException {
        return UUID.randomUUID();
        //return BigInteger.valueOf(System.currentTimeMillis() + (++counter));
    }

    @Override
    public String[] sqlCreateStrings(Dialect dialect)
            throws HibernateException {
        String[] sqlCreateStrings = super.sqlCreateStrings(dialect);
        System.out.println("sqlCreateStrings: " + Arrays.asList(sqlCreateStrings));
        return sqlCreateStrings;
    }

    @Override
    public String[] sqlDropStrings(Dialect dialect)
            throws HibernateException {
        String[] sqlDropStrings = super.sqlDropStrings(dialect);
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
