/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.beansbinding.EntityProperties;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.bl.validation.ClassifierValidatorLocal;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierAppliedForDot;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 * Implementation of handling classifiers (see interface for more information)
 *
 * @author Bozhidar Bozhanov
 */
@Stateless
public class ClassifiersBean implements ClassifiersRemote, ClassifiersLocal {

    protected static Logger log = Logger.getLogger(ClassifiersBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EntityStoreManagerLocal esm;

    @EJB
    private ClassifierValidatorLocal classifierValidator;

    @Override
    public void addDataObjectTypeConstraint(Classifier classifier,
            DataObjectType dot) {

        ClassifierAppliedForDot cafd = new ClassifierAppliedForDot(
                classifier.getClassifierId(),
                dot.getDataObjectTypeId());
        esm.persist(em, cafd);
    }

    @Override
    public void classifyDataObject(DataObject dataObject, Classifier classifier) {
        ClassifiedObject co = new ClassifiedObject(
                classifier.getClassifierId(),
                dataObject.getDataObjectId());

        Query q = em.createNamedQuery("ClassifiedObject.findClassifiedObject");
        q.setParameter("classifiedObject", co);
        if (q.getResultList().size() == 0)
            esm.persist(em, co);
    }

    @Override
    public void clearDataObjectTypeConstraints(Classifier classifier) {
        Query q = em.createNamedQuery("ClassifierAppliedForDot.clearTypesForCliassifier");
        q.setParameter("classifier", classifier);
        q.executeUpdate();
    }

    @Override
    public int deleteClassifier(Classifier classifier) {
        if (!classifier.getClassifierGroup().getIsSystemGroup())
            return esm.remove(em, classifier);
        else
            return 0;
    }

    @Override
    public int deleteClassifierGroup(ClassifierGroup classifierGroup) {
        if (!classifierGroup.getIsSystemGroup())
            return esm.remove(em, classifierGroup);
        else
            return 0;
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ClassifierGroup> getClassifierGroups() {
         Query q = em.createNamedQuery("ClassifierGroup.getAllNotDeleted");
         return new ArrayList<ClassifierGroup>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Classifier> getClassifiers(DataObject parentDataObject,
            DataObjectType dataObjectType) {

        List<Classifier> result = null;
        
        if (dataObjectType != null) {
            result = new ArrayList();
            Query dotQuery;
            if (parentDataObject == null) {
                dotQuery = em.createNamedQuery("ClassifierAppliedForDot.findByDataObjectType");
            } else {
                dotQuery = em.createNamedQuery("ClassifierAppliedForDot.findByDataObjectTypeAndParent");
                dotQuery.setParameter("parent", parentDataObject);
            }
            dotQuery.setParameter("dataObjectType", dataObjectType);
            
            List<ClassifierAppliedForDot> cafds = dotQuery.getResultList();
            
            for (ClassifierAppliedForDot cafd : cafds) {
                log.info("Adding " + cafd.toString());
                result.add(cafd.getClassifier());
            }
            
            log.info("SIZE: " + cafds.size());
            if (cafds.size() == 0)
                dataObjectType = null;
        }
        log.info("DOT: " + dataObjectType);
        
        if (dataObjectType == null) {
            
            Query q;
            if(parentDataObject != null)
            {
                q = em.createNamedQuery("Classifier.findByParentDataObjectAndDeleted");
                q.setParameter("parentDataObject", parentDataObject);
            }
            else
            {
                q = em.createNamedQuery("Classifier.findAllAndDeleted");
            }
            q.setParameter("deleted", false);
            result = new ArrayList(q.getResultList());
            log.info("NEXTSIZE: " + result.size() + ", p:" + parentDataObject);
        }
                    
        return result;
    }

    @Override
    public Classifier newClassifier() {
        return new Classifier();
    }

    @Override
    public ClassifierGroup newClassifierGroup() {
       return new ClassifierGroup();
    }

    @Override
    public void removeDataObjectTypeConstraint(Classifier classifier,
            DataObjectType dot) {
        ClassifierAppliedForDot cafd = new ClassifierAppliedForDot(
                classifier.getClassifierId(),
                dot.getDataObjectTypeId());
        removeDataObjectTypeConstraint(cafd);
    }

    @Override
    public Classifier saveClassifier(Classifier classifier, DataObject parentDataObject) {

        classifier.setParentId(parentDataObject.getDataObjectId());

        if (classifier.getDataObject() == null){
            DataObject dataObject = new DataObject();
            dataObject.setParentDataObject(parentDataObject);
            classifier.setDataObject(dataObject);
        }

        classifierValidator.validate(classifier);

        esm.persist(em, classifier);
        
        return classifier;
    }

    @Override
    public ClassifierGroup saveClassifierGroup(ClassifierGroup classifierGroup) {
        esm.persist(em, classifierGroup);
        return classifierGroup;
    }

    @Override
    public void unclassifyDataObject(DataObject dataObject,
            Classifier classifier) {

        ClassifiedObject co = new ClassifiedObject(
                classifier.getClassifierId(),
                dataObject.getDataObjectId());

        unclassifyDataObject(co);

    }

    @Override
    public void removeDataObjectTypeConstraint(
            ClassifierAppliedForDot classifierAppliedForDot) {

        esm.remove(em, classifierAppliedForDot);
    }

    @Override
    public void unclassifyDataObject(ClassifiedObject classifiedObject) {
        esm.remove(em, classifiedObject);
    }

    @Override
    public EntityProperties getClassifierEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(Classifier.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public EntityProperties getClassifierGroupEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ClassifierGroup.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public EntityProperties getClassifiedObjectEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ClassifiedObject.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public ClassifiedObject newClassifiedObject() {
        return new ClassifiedObject();
    }

    @Override
    public List<Classifier> getClassifiers(DataObject classifiedDataObject) {
        Query q = em.createNamedQuery("ClassifiedObject.findByDataObject");
        q.setParameter("dataObject", classifiedDataObject);
        
        List<ClassifiedObject> classifiedObjects = q.getResultList();
        
        List<Classifier> result = new ArrayList(classifiedObjects.size());
        
        for (ClassifiedObject co : classifiedObjects) {
            result.add(co.getClassifier());
        }
        
        return result;
    }

    @Override
    public List<DataObjectType> getDataObjectTypes() {
        Query q = em.createNamedQuery("DataObjectType.listAll");
        return q.getResultList();
    }

    @Override
    public EntityProperties getDataObjectTypeEntityProperties() {
       EntityProperties entityProperties = esm.getEntityProperties(DataObjectType.class);
       entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

       return entityProperties;
    }

    @Override
    public List<DataObjectType> getDataObjectTypes(Classifier classifier) {
        if (classifier == null || classifier.getDataObject() == null) {
            return new ArrayList<DataObjectType>();
        }
        
        Query q = em.createNamedQuery("ClassifierAppliedForDot.findByClassifier");
        q.setParameter("classifier", classifier);
        
        List<DataObjectType> result = new ArrayList<DataObjectType>();
        List<ClassifierAppliedForDot> cafds = q.getResultList();
        
        for (ClassifierAppliedForDot cafd : cafds) {
            result.add(cafd.getDataObjectType());
        }
        
        return result;
    }
      

}
