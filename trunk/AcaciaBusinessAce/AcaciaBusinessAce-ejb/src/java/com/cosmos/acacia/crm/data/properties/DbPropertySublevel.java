/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.properties;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "db_property_sublevels", catalog = "acacia", schema = "public")
@NamedQueries(
    {
        @NamedQuery(
            name = "DbPropertySublevel.findByLevelIdAndSublevelName",
            query = "SELECT d.sublevelId FROM DbPropertySublevel d" +
            " WHERE" +
            " d.dbPropertySublevelPK.levelId = :levelId" +
            " AND" +
            " d.dbPropertySublevelPK.sublevelName = :sublevelName"),
        @NamedQuery(
            name = "DbPropertySublevel.maxSublevelIdByLevelId",
            query = "SELECT max(d.sublevelId) FROM DbPropertySublevel d" +
            " WHERE" +
            " d.dbPropertySublevelPK.levelId = :levelId")
    })
public class DbPropertySublevel
    implements Serializable
{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected DbPropertySublevelPK dbPropertySublevelPK;

    @Column(name = "sublevel_id")
    private Integer sublevelId;

    public DbPropertySublevel()
    {
    }

    public DbPropertySublevel(DbPropertySublevelPK dbPropertySublevelPK)
    {
        this.dbPropertySublevelPK = dbPropertySublevelPK;
    }

    public DbPropertySublevel(int levelId, String sublevelName)
    {
        this.dbPropertySublevelPK = new DbPropertySublevelPK(levelId, sublevelName);
    }

    public DbPropertySublevelPK getDbPropertySublevelPK()
    {
        return dbPropertySublevelPK;
    }

    public void setDbPropertySublevelPK(DbPropertySublevelPK dbPropertySublevelPK)
    {
        this.dbPropertySublevelPK = dbPropertySublevelPK;
    }

    public Integer getSublevelId()
    {
        return sublevelId;
    }

    public void setSublevelId(Integer sublevelId)
    {
        this.sublevelId = sublevelId;
    }

    @Override
    public int hashCode()
    {
        return dbPropertySublevelPK != null ? dbPropertySublevelPK.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof DbPropertySublevel))
        {
            return false;
        }

        DbPropertySublevel other = (DbPropertySublevel) object;
        if(dbPropertySublevelPK == null || other.dbPropertySublevelPK == null ||
           !this.dbPropertySublevelPK.equals(other.dbPropertySublevelPK))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return "com.cosmos.acacia.crm.data.properties.DbPropertySublevel[dbPropertySublevelPK=" + dbPropertySublevelPK + "]";
    }

}
