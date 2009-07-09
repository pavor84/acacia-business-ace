/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.persistence.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 *
 * @author Miro
 */
public class UuidUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @Override
    public Class returnedClass() {
        return UUID.class;
    }

    @Override
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        return (arg0 == arg1) || (arg0 != null && arg1 != null && (arg0.equals(arg1)));
    }

    @Override
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        return rs.getObject(names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement pstmt, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            pstmt.setNull(index, Types.OTHER);
            return;
        } else {
            pstmt.setObject(index, value);
        }
    }

    // UUID is immutable, so we do not copy it actually
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return (UUID) value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (UUID) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
