/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data.properties;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Miro
 */
@Embeddable
public class DbPropertySublevelPK
    implements Serializable
{
    @Basic(optional = false)
    @Column(name = "level_id", nullable = false)
    private int levelId;

    @Basic(optional = false)
    @Column(name = "sublevel_name", nullable = false, length = 255)
    private String sublevelName;

    public DbPropertySublevelPK()
    {
    }

    public DbPropertySublevelPK(int levelId, String sublevelName)
    {
        this.levelId = levelId;
        this.sublevelName = sublevelName;
    }

    public int getLevelId()
    {
        return levelId;
    }

    public void setLevelId(int levelId)
    {
        this.levelId = levelId;
    }

    public String getSublevelName()
    {
        return sublevelName;
    }

    public void setSublevelName(String sublevelName)
    {
        this.sublevelName = sublevelName;
    }

    @Override
    public int hashCode()
    {
        int hash = levelId;
        hash += (sublevelName != null ? sublevelName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof DbPropertySublevelPK))
        {
            return false;
        }

        DbPropertySublevelPK other = (DbPropertySublevelPK) object;
        if(this.levelId != other.levelId)
        {
            return false;
        }

        if(this.sublevelName == null || other.sublevelName != null ||
           !this.sublevelName.equals(other.sublevelName))
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.properties.DbPropertySublevelPK[levelId=" + levelId + ", sublevelName=" + sublevelName + "]";
    }

}
