/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "pattern_mask_formats")
@NamedQueries({})
public class PatternMaskFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pattern_mask_format_id", nullable = false)
    @SequenceGenerator(name="PatternMaskFormatSequenceGenerator", sequenceName="pattern_mask_formats_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PatternMaskFormatSequenceGenerator")
    private Integer patternMaskFormatId;

    @Column(name = "owner_id")
    private BigInteger ownerId;

    @Column(name = "pattern_name", nullable = false)
    private String patternName;

    @Column(name = "format_type", nullable = false)
    private char formatType;

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "description")
    private String description;


    public PatternMaskFormat() {
    }

    public PatternMaskFormat(Integer patternMaskFormatId) {
        this.patternMaskFormatId = patternMaskFormatId;
    }

    public PatternMaskFormat(Integer patternMaskFormatId, String patternName, char formatType, String format) {
        this.patternMaskFormatId = patternMaskFormatId;
        this.patternName = patternName;
        this.formatType = formatType;
        this.format = format;
    }

    public Integer getPatternMaskFormatId() {
        return patternMaskFormatId;
    }

    public void setPatternMaskFormatId(Integer patternMaskFormatId) {
        this.patternMaskFormatId = patternMaskFormatId;
    }

    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public char getFormatType() {
        return formatType;
    }

    public void setFormatType(char formatType) {
        this.formatType = formatType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patternMaskFormatId != null ? patternMaskFormatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatternMaskFormat)) {
            return false;
        }
        PatternMaskFormat other = (PatternMaskFormat) object;
        if ((this.patternMaskFormatId == null && other.patternMaskFormatId != null) || (this.patternMaskFormatId != null && !this.patternMaskFormatId.equals(other.patternMaskFormatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cosmos.acacia.crm.data.PatternMaskFormat[patternMaskFormatId=" + patternMaskFormatId + "]";
    }

}
