/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.persistence.hibernate.dialect;

import java.sql.Types;
import org.hibernate.dialect.PostgreSQLDialect;

/**
 *
 * @author Miro
 */
public class PostgreSQLDialectUUID extends PostgreSQLDialect {

    public PostgreSQLDialectUUID() {
        super();
        registerColumnType(Types.OTHER, "uuid");
    }
}
