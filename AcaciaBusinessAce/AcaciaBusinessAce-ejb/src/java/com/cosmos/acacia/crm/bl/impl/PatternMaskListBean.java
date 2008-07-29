package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.crm.bl.validation.PatternMaskFormatValidatorLocal;
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
    
    @EJB
    private PatternMaskFormatValidatorLocal patternMaskFormatValidator;

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
     * @see com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote#listPatternsByName(java.math.BigInteger)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PatternMaskFormat> listPatternsByName(BigInteger parentId) {
        
        Query q = em.createNamedQuery("PatternMaskFormat.findForParentByName");
        q.setParameter("parentDataObjectId", parentId);
        
        List<PatternMaskFormat> result = q.getResultList();
        
        return result;
    }

    @Override
    public PatternMaskFormat newPatternMaskFormat(BigInteger parentId) {
        PatternMaskFormat result = new PatternMaskFormat();
        result.setParentId(parentId);
        return result;
    }

    @Override
    public PatternMaskFormat savePatternMaskFormat(PatternMaskFormat format) {
        patternMaskFormatValidator.validate(format); 
        
        //dummy - because it is not used, but yet set as 'not-null' 
        //in the db schema
        format.setFormatType('-');
        
        esm.persist(em, format);
        return format; 
    }
    
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<BusinessPartner> getOwnersList() {
//        
//        try{
//            Query q = em.createNamedQuery("BusinessPartner.getAllNotDeleted");
//            List<BusinessPartner> result = q.getResultList();
//            
//            /**
//             * Sort by name
//             */
//            Collections.sort(result, new Comparator<BusinessPartner>() {
//            
//                @Override
//                public int compare(BusinessPartner o1, BusinessPartner o2) {
//                    String name1 = "";
//                    if ( o1 instanceof Person )
//                        name1 = ((Person)o1).getFirstName()+" "+((Person)o1).getLastName();
//                    else if ( o1 instanceof Organization )
//                        name1 = ((Organization)o1).getOrganizationName();
//                    
//                    String name2 = "";
//                    if ( o2 instanceof Person )
//                        name2 = ((Person)o2).getFirstName()+" "+((Person)o2).getLastName();
//                    else if ( o2 instanceof Organization )
//                        name2 = ((Organization)o2).getOrganizationName();
//                    
//                    return name1.compareTo(name2);
//                }
//            
//            });
//            
//            return result;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        
//        return null;
//    }

    @Override
    public boolean deletePatternMaskFormat(PatternMaskFormat formatObject) {
        esm.remove(em, formatObject);
        return true;
    }
}
