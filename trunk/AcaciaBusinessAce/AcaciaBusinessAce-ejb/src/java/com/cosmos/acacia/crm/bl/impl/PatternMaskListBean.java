package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.beansbinding.EntityProperties;

/**
 * Created	:	25.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
@Stateless
public class PatternMaskListBean implements PatternMaskListRemote {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private EntityStoreManagerLocal esm;

    /**
     * @see com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote#getPatternMaskEntityProperties()
     */
    @Override
    public EntityProperties getPatternMaskEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(PatternMaskFormat.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    /**
     * @see com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote#listPatternsByName()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PatternMaskFormat> listPatternsByName() {
        
        Query q = em.createNamedQuery("PatternMaskFormat.findForParentByName");
        
        return q.getResultList();
    }

    @Override
    public PatternMaskFormat newPatternMaskFormat() {
        return new PatternMaskFormat();
    }

    @Override
    public PatternMaskFormat savePatternMaskFormat(PatternMaskFormat format) {
        esm.persist(em, format);
        return format; 
    }
}
