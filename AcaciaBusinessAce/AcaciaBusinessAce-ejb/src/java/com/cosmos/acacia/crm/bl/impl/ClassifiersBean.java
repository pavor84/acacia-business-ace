/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.validation.ClassifierValidatorLocal;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.ClassifiedObjectBean;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierAppliedForDot;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.EntityProperties;

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

    @EJB
    private AcaciaSessionLocal session;

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

        throw new ValidationException("Classifier.err.systemGroupForbidden");
    }

    @Override
    public int deleteClassifierGroup(ClassifierGroup classifierGroup) {
        if (!classifierGroup.getIsSystemGroup())
            return esm.remove(em, classifierGroup);

        throw new ValidationException("ClassifierGroup.err.systemGroupForbidden");

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ClassifierGroup> getClassifierGroups() {
         Query q = em.createNamedQuery("ClassifierGroup.getAllNotDeleted");
         q.setParameter("parentId", session.getOrganization().getId());
         return new ArrayList<ClassifierGroup>(q.getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Classifier> getClassifiers(BigInteger parentDataObjectId,
            DataObjectType dataObjectType) {

        List<Classifier> result = null;

        Query q;
        if(parentDataObjectId != null)
        {
            q = em.createNamedQuery("Classifier.findByParentDataObjectAndDeleted");
            q.setParameter("groupId", parentDataObjectId);
        }
        else
        {
            q = em.createNamedQuery("Classifier.findAllAndDeleted");
        }
        q.setParameter("parentId", session.getOrganization().getId());

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
    public ClassifierGroup newClassifierGroup(BigInteger parentId) {
        ClassifierGroup group = new ClassifierGroup();
        group.setParentId(parentId);
        return group;
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
    public Classifier saveClassifier(Classifier classifier, BigInteger groupId) {

        if (classifier.getClassifierGroup().getIsSystemGroup())
            throw new ValidationException("Classifier.err.systemGroupForbidden");

        classifier.setParentId(groupId);

        if (classifier.getDataObject() == null){
            DataObject dataObject = new DataObject();
            dataObject.setParentDataObjectId(groupId);
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

    @SuppressWarnings("unchecked")
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
        return session.getDataObjectTypes();
    }

    @Override
    public EntityProperties getDataObjectTypeEntityProperties() {
       EntityProperties entityProperties = esm.getEntityProperties(DataObjectType.class);
       entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

       return entityProperties;
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override
    public List<DataObject> getDataObjects(Classifier classifier) {
        Query q;
        if (classifier != null) {
            q = em.createNamedQuery("ClassifiedObject.findByClassifier");
            q.setParameter("classifier", classifier);
        } else {
            q = em.createNamedQuery("ClassifiedObject.findAll");
        }

        q.setParameter("deleted", false);

        List<ClassifiedObject> cos = q.getResultList();
        List<DataObject> result = new ArrayList<DataObject>(cos.size());

        for (ClassifiedObject co : cos) {
            result.add(co.getDataObject());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Classifier getClassifier(String code) {
        Query q = em.createNamedQuery("Classifier.findByCode");
        q.setParameter("code", code);
        q.setParameter("deleted", false);
        q.setParameter("parentId", session.getOrganization().getId());

        List<Classifier> result = q.getResultList();

        if (result == null || result.size() == 0)
            throw new ValidationException("No classifier found with this code");

        return result.get(0);
    }

    @Override
    public List<ClassifiedObjectBean> getClassifiedObjectBeans(Classifier classifier) {
        List<DataObject> dataObjects = getDataObjects(classifier);

        List<ClassifiedObjectBean> result = new ArrayList<ClassifiedObjectBean>(dataObjects.size());

        for (DataObject dataObject : dataObjects) {
            DataObjectBean dob = getDataObjectBean(dataObject);
            if (dob != null) {
                ClassifiedObjectBean cob = new ClassifiedObjectBean();
                String type = dob.getClass().getName().replaceAll(dob.getClass().getPackage().getName() + "\\.", "");

                cob.setTitle(dob.getInfo());
                cob.setType(type);
                result.add(cob);
            }

        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataObjectBean getDataObjectBean(DataObject dataObject) {
        if (dataObject == null)
            return null;

        DataObjectType dot = dataObject.getDataObjectType();
        try {
            Class clazz = Class.forName(dot.getDataObjectType());
            DataObjectBean dob = (DataObjectBean) em.find(clazz, dataObject.getDataObjectId());
            return dob;
        } catch (Exception ex){
            log.error("", ex);
            return null;
        }
    }
    
    private ClassifierGroup getSystemGroup(){
        for (ClassifierGroup g : getClassifierGroups()) {
            if ( g.getIsSystemGroup() || "system".equals(g.getClassifierGroupCode()) )
                return g;
        }
        return null;
    }

    @Override
    public EntityProperties getClassifiedObjectBeansEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(ClassifiedObjectBean.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);

        return entityProperties;
    }

    @Override
    public Classifier getOrCreateSystemClassifier(String classifierKey) {
        Classifier result = null;
        try{
            result = getClassifier(classifierKey);
        }catch ( Exception e){
            log.debug("Classifier: "+classifierKey+" not found! Creating new...");
        }
        
        if ( result == null ){
            result = newClassifier();
            result.setClassifierCode(classifierKey);
            String name = classifierKey.substring(0, 1).toUpperCase() + classifierKey.substring(1);
            result.setClassifierName(name);
            ClassifierGroup group = getSystemGroup();
            result.setClassifierGroup(group);
            result.setParentId(group.getId());
            
            classifierValidator.validate(result);
            esm.persist(em, result);
        }
        
        return result;
    }
    
    @Override
    public Classifier saveInitialClassifier(Classifier classifier, BigInteger parentDataObjectId) {

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
    public Boolean isClassifiedAs(DataObjectBean bean, Classifier classifier) {
        try{
            List<Classifier> classifiers = getClassifiers(bean.getDataObject());
            return new Boolean(classifiers.contains(classifier));
        }catch ( Exception e ){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean isClassifiedAs(DataObjectBean bean, String classifierCode) {
        try{
            Classifier c = getClassifier(classifierCode);
            return isClassifiedAs(bean, c);
        }catch ( Exception e ){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
