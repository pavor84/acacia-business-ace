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
import com.cosmos.acacia.crm.validation.ValidationException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
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
            throw new ValidationException("Classifier.err.systemGroupForbidden");
    }

    @Override
    public int deleteClassifierGroup(ClassifierGroup classifierGroup) {
        if (!classifierGroup.getIsSystemGroup())
            return esm.remove(em, classifierGroup);
        else
            throw new ValidationException("ClassifierGroup.err.systemGroupForbidden");
        
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
        
        Query q;
        if(parentDataObject != null)
        {
            q = em.createNamedQuery("Classifier.findByParentDataObjectAndDeleted");
            q.setParameter("parentDataObjectId", parentDataObject.getDataObjectId());
        }
        else
        {
            q = em.createNamedQuery("Classifier.findAllAndDeleted");
        }
        q.setParameter("deleted", false);
        result = new ArrayList(q.getResultList());
        List<Classifier> mirror = new ArrayList<Classifier>(result);
        
        if (dataObjectType != null) {
            Query dotQuery;
            dotQuery = em.createNamedQuery("ClassifierAppliedForDot.findAll");
            Set<ClassifierAppliedForDot> cafds = new HashSet(dotQuery.getResultList());
            
            Set<Classifier> applicableClassifiers = new HashSet<Classifier>(cafds.size());
            
            for (ClassifierAppliedForDot cafd : cafds) {
                applicableClassifiers.add(cafd.getClassifier());
            }
            
            for (Classifier classifier : mirror) {
                if (applicableClassifiers.contains(classifier)) {
                    ClassifierAppliedForDot tmpCafd = new ClassifierAppliedForDot(
                            classifier.getClassifierId(), dataObjectType.getDataObjectTypeId());

                    if (!cafds.contains(tmpCafd))
                        result.remove(classifier);
                }
            }
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

        if (classifier.getClassifierGroup().getIsSystemGroup())
            throw new ValidationException("Classifier.err.systemGroupForbidden");

        BigInteger parentDataObjectId = parentDataObject.getDataObjectId();
        classifier.setParentId(parentDataObjectId);

        if (classifier.getDataObject() == null){
            DataObject dataObject = new DataObject();
            dataObject.setParentDataObjectId(parentDataObjectId);
            classifier.setDataObject(dataObject);
        }

        classifierValidator.validate(classifier);

        esm.persist(em, classifier);
        
        return classifier;
    }

    @Override
    public ClassifierGroup saveClassifierGroup(ClassifierGroup classifierGroup) {
        if (classifierGroup.getIsSystemGroup())
            throw new ValidationException("ClassifierGroup.err.systemGroupForbidden");
        
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
        return new ArrayList<DataObjectType>(q.getResultList());
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
        
        return new ArrayList<DataObjectType>(result);
    }

    @Override
    public List<DataObject> getDataObjects(Classifier classifier) {
        Query q = em.createNamedQuery("ClassifiedObject.findByClassifier");
        q.setParameter("classifier", classifier);
        q.setParameter("deleted", false);
        
        List<ClassifiedObject> cos = q.getResultList();
        List<DataObject> result = new ArrayList<DataObject>(cos.size());
        
        for (ClassifiedObject co : cos) {
            result.add(co.getDataObject());
        }
        return result;
    }

    @Override
    public Classifier getClassifier(String code) {
        Query q = em.createNamedQuery("Classifier.findByCode");
        q.setParameter("code", code);
        q.setParameter("deleted", false);
        
        List<Classifier> result = q.getResultList();
        
        if (result == null || result.size() == 0)
            throw new ValidationException("No classifier found with this code");
        else
            return result.get(0);
    }
}
