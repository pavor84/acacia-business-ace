/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyValidator;
import com.cosmos.acacia.annotation.ValidationType;

/**
 *
 * @author miro
 */
@Entity
@Table(name = "pattern_mask_formats")
@NamedQueries({ 
    @NamedQuery
    (
        /**
         * Parameters:
         * - productCode - find all undeleted products with the same code (at most one should exist)
         */
        name = "PatternMaskFormat.findForParentByName",
        query = "select p from PatternMaskFormat p order by p.patternName"
    ),
    @NamedQuery
    ( 
        /**
         * Get all mask formats for a given name - at most one should exist
         * Parameters:
         * - name - the name of the pattern mask
         */
        name = "PatternMaskFormat.findByName",
        query = "select p from PatternMaskFormat p where p.patternName like :patternName"
    )
})
public class PatternMaskFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pattern_mask_format_id", nullable = false)
    @SequenceGenerator(name="PatternMaskFormatSequenceGenerator", sequenceName="pattern_mask_formats_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PatternMaskFormatSequenceGenerator")
    private Integer patternMaskFormatId;

    @Column(name = "pattern_name", nullable = false)
    @Property(title="Name",
            propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, minLength=2, maxLength=64))
    private String patternName;
    
    @Property(title="Format",
        propertyValidator=@PropertyValidator(validationType=ValidationType.MASK_FORMATTER, maxLength=128))
    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "format_type", nullable = false)
    private char formatType;

    @Property(title="Description", 
        propertyValidator=@PropertyValidator(validationType=ValidationType.LENGTH, maxLength=4000))
    @Column(name = "description")
    private String description;
    
//    @Column(name = "owner_id")
//    @Property(title="Owner")
//    private BigInteger ownerId;
    
    @ManyToOne
    @JoinColumn (name="owner_id")
    @Property(title="Owner")
    private BusinessPartner owner;

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
    
    /**
     * Getter for owner
     * @return BusinessPartner
     */
    public BusinessPartner getOwner() {
        return owner;
    }

    /**
     * Setter for owner
     * @param owner - BusinessPartner
     */
    public void setOwner(BusinessPartner owner) {
        this.owner = owner;
    }
}
