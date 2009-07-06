/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import java.math.BigInteger;
import java.sql.BatchUpdateException;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.data.Address;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.acacia.crm.data.BusinessDocument;
import com.cosmos.acacia.crm.data.BusinessDocumentStatusLog;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.EnumClass;
import com.cosmos.acacia.crm.data.Product;
import com.cosmos.acacia.crm.data.Right;
import com.cosmos.acacia.crm.enums.DocumentStatus;
import com.cosmos.acacia.crm.enums.DocumentType;
import com.cosmos.acacia.crm.validation.ValidationException;
import com.cosmos.beansbinding.BeansBindingHelper;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import static com.cosmos.beansbinding.BeansBindingHelper.createEntityProperties;

/**
 *
 * @author miro
 */
@Stateless
public class EntityStoreManagerBean implements EntityStoreManagerLocal {

    private static final long DOCUMENT_NUMBER_MULTIPLIER = 10000000;
    @EJB
    private DataObjectTypeLocal dotLocal;
    @EJB
    private AcaciaSessionLocal session;
    @EJB
    private EntitySequenceServiceLocal entitySequenceService;
    
    //
    private static Integer addressDataObjectTypeId;
    private Map<String, EntityProperties> entityPropertiesMap = new TreeMap<String, EntityProperties>();

    @Override
    public void persist(EntityManager em, Object entity) {
        boolean mustMerge = false;
        BusinessDocument oldBusinessDocument = null;
        if (entity instanceof BusinessDocument && ((BusinessDocument) entity).getId() != null) {
            oldBusinessDocument = (BusinessDocument) getDataObjectBean(em, ((BusinessDocument) entity).getDataObject());
        }
        if (entity instanceof DataObjectBean) {
            DataObjectBean doBean = (DataObjectBean) entity;
            BigInteger id = doBean.getId();
            DataObject dataObject = doBean.getDataObject();
            BigInteger parentId = doBean.getParentId();
            if (id == null) {
                if (dataObject == null || dataObject.getDataObjectId() == null) {
                    DataObjectTypeLocal dotLocal = getDataObjectTypeLocal();
                    DataObjectType dot = dotLocal.getDataObjectType(entity.getClass().getName());

                    if (dataObject == null) {
                        dataObject = new DataObject();
                    }

                    dataObject.setParentDataObjectId(parentId);
                    dataObject.setDataObjectType(dot);
                    dataObject.setDataObjectVersion(1);
                    BigInteger creatorId = null;
                    BigInteger ownerId = null;

                    try {
                        creatorId = session.getUser().getUserId();
                        ownerId = session.getBranch().getAddressId();
                    } catch (Exception ex) {
                        creatorId = BigInteger.ZERO;
                        ownerId = BigInteger.ZERO;
                    }

                    dataObject.setCreatorId(creatorId);
                    dataObject.setOwnerId(ownerId);

                    em.persist(dataObject);
                } else {
                    BigInteger doParentId = dataObject.getParentDataObjectId();
                    boolean mustUpdateDO = false;
                    if (parentId != null) {
                        if (doParentId == null || !parentId.equals(doParentId)) {
                            dataObject.setParentDataObjectId(parentId);
                            mustUpdateDO = true;
                        }
                    } else if (doParentId != null) {
                        dataObject.setParentDataObjectId(parentId);
                        mustUpdateDO = true;
                    }

                    if (mustUpdateDO) {
                        dataObject = em.merge(dataObject);
                        em.persist(dataObject);
                    }
                }

                //id = new BigInteger(dataObject.getDataObjectId().toByteArray());
                id = dataObject.getDataObjectId();
                doBean.setId(id);
            } else {
                mustMerge = true;
                if (dataObject == null) {
                    dataObject = em.find(DataObject.class, id);
                } else {
                    dataObject = em.merge(dataObject);
                }
                int version = dataObject.getDataObjectVersion();
                dataObject.setParentDataObjectId(parentId);
                dataObject.setDataObjectVersion(version + 1);
                em.persist(dataObject);
            }

            if (dataObject.getOrderPosition() == null) {
                setOrderPosition(em, dataObject);
            }
            doBean.setDataObject(dataObject);
        } else {
            mustMerge = !(entity.hashCode() == 0);
        }

        if (mustMerge) {
            entity = em.merge(entity);
        }

        em.persist(entity);

        if (entity instanceof BusinessDocument) {
            BusinessDocument businessDocument = (BusinessDocument) entity;
            DbResource docStatus;
            if ((docStatus = businessDocument.getDocumentStatus()) != null) {
                DbResource oldDocStatus;
                if (oldBusinessDocument != null) {
                    oldDocStatus = oldBusinessDocument.getDocumentStatus();
                } else {
                    oldDocStatus = null;
                }

                if (!docStatus.equals(oldDocStatus)) {
                    BusinessDocumentStatusLog log = new BusinessDocumentStatusLog(
                            businessDocument.getDocumentId(), docStatus.getResourceId(), new Date());
                    log.setOfficer(session.getPerson());
                    em.persist(log);
                }
            }
        }
    }

    @Override
    public void setDocumentNumber(EntityManager em, BusinessDocument businessDocument) {
        //persist(em, businessDocument);
        businessDocument.setDocumentDate(new Date());
        BigInteger parentEntityId = businessDocument.getParentId();
        DataObject dataObject = businessDocument.getDataObject();
        int dataObjectTypeId = dataObject.getDataObjectType().getDataObjectTypeId();
        //Address branch = getParentAddress(em, dataObject);
        Address branch = businessDocument.getPublisherBranch();
        if (branch.getOrderPosition() == null) {
            // store the address to generate order position for the old objects
            persist(em, branch);
        }
        long initialValue = branch.getOrderPosition() * DOCUMENT_NUMBER_MULTIPLIER;
        long docNumber = entitySequenceService.nextValue(parentEntityId, dataObjectTypeId, initialValue);
        businessDocument.setDocumentNumber(docNumber);
    }

    private void setOrderPosition(EntityManager em, DataObject dataObject) {
        BigInteger parentDataObjectId;
        Query q;
        if ((parentDataObjectId = dataObject.getParentDataObjectId()) != null) {
            q = em.createNamedQuery("DataObject.maxOrderPositionByParentDataObjectIdAndDataObjectType");
            q.setParameter("parentDataObjectId", parentDataObjectId);
        } else {
            q = em.createNamedQuery("DataObject.maxOrderPositionByDataObjectType");
        }
        q.setParameter("dataObjectType", dataObject.getDataObjectType());
        Integer maxOrderPosition;
        try {
            maxOrderPosition = (Integer) q.getSingleResult();
        } catch (NoResultException ex) {
            maxOrderPosition = 0;
        }
        if (maxOrderPosition == null) {
            maxOrderPosition = 0;
        }
        dataObject.setOrderPosition(maxOrderPosition + 1);
        em.persist(dataObject);
    }

    @Override
    public int remove(EntityManager em, Object entity) {
        int version = -1;
        if (entity instanceof DataObjectBean) {
            DataObjectBean doBean = (DataObjectBean) entity;
            DataObject dataObject = doBean.getDataObject();
            if (dataObject == null) {
                dataObject = em.find(DataObject.class, doBean.getId());
            } else {
                dataObject = em.merge(dataObject);
            }
            version = dataObject.getDataObjectVersion() + 1;
            dataObject.setDataObjectVersion(version);
            dataObject.setDeleted(true);
            em.persist(dataObject);
        }

        entity = em.merge(entity);
        em.remove(entity);
        try {
            em.flush();
        } catch (PersistenceException pe) {
            throw new ValidationException(getRootCauseMessage(pe));
        }
        return version;
    }

    private String getRootCauseMessage(Throwable ex) {
        if (ex.getCause() != null) {
            return getRootCauseMessage(ex.getCause());
        }

        if (ex instanceof BatchUpdateException) {
            BatchUpdateException bue = (BatchUpdateException) ex;
            String message = bue.getNextException().getMessage();
            message = message.substring(
                    message.lastIndexOf("from table "));
            message = message.replaceAll("\"", "");
            message = message.replaceAll("\\.", "");
            message = message.replaceAll("from table ", "");
            return message;
        }
        return "";
    }

    private DataObjectTypeLocal getDataObjectTypeLocal() {
        if (dotLocal == null) {
            try {
                dotLocal = InitialContext.doLookup(DataObjectTypeLocal.class.getName());
            } catch (NamingException ex) {
                throw new RuntimeException(ex);
            }
        }

        return dotLocal;
    }

    @Override
    public void prePersist(DataObjectBean entity) {
        System.out.println("EntityStoreManager.prePersist: " + entity);
    }

    // Add business logic below. (Right-click in editor and choose
    // "EJB Methods > Add Business Method" or "Web Service > Add Operation")
    @SuppressWarnings("unchecked")
    @Override
    public EntityProperties getEntityProperties(Class entityClass) {
        String entityClassName = entityClass.getName();
        EntityProperties entityProperties = entityPropertiesMap.get(entityClassName);
        if (entityProperties == null) {
            if (BusinessPartner.class.isAssignableFrom(entityClass)) {
                entityProperties = createEntityProperties(BusinessPartner.class);
                entityProperties.addEntityProperties(createEntityProperties(entityClass));
            } else if (BusinessDocument.class.isAssignableFrom(entityClass)) {
                entityProperties = createEntityProperties(BusinessDocument.class);
                entityProperties.addEntityProperties(createEntityProperties(entityClass));
            } else if (Product.class.isAssignableFrom(entityClass)) {
                entityProperties = createEntityProperties(Product.class);
                entityProperties.addEntityProperties(createEntityProperties(entityClass));
            } else if (Right.class.isAssignableFrom(entityClass)) {
                entityProperties = createEntityProperties(Right.class);
                entityProperties.addEntityProperties(createEntityProperties(entityClass));
            } else {
                entityProperties = createEntityProperties(entityClass);
            }

            entityPropertiesMap.put(entityClassName, entityProperties);
        }

        return (EntityProperties) entityProperties.clone();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyDetails getPropertyDetails(Class entityClass, String propertyName, int position) {
        return BeansBindingHelper.createPropertyDetails(entityClass, propertyName, position);
    }

    @Override
    public DataObjectBean getDataObjectBean(EntityManager em, DataObject dataObject) {
        if (dataObject == null) {
            return null;
        }

        DataObjectType dot = dataObject.getDataObjectType();
        Class cls;
        try {
            cls = Class.forName(dot.getDataObjectType());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        DataObjectBean dob = (DataObjectBean) em.find(cls, dataObject.getDataObjectId());
        return dob;
    }

    @Override
    public DataObjectBean getDataObjectBean(EntityManager em, BigInteger dataObjectId) {
        DataObject dataObject = em.find(DataObject.class, dataObjectId);
        if (dataObject == null) {
            return null;
        }

        return getDataObjectBean(em, dataObject);
    }

    public DataObjectBean getParentEntity(EntityManager em, DataObjectBean entity) {
        return getParentEntity(em, entity.getDataObject());
    }

    public DataObjectBean getParentEntity(EntityManager em, DataObject dataObject) {
        BigInteger parentDataObjectId;
        if ((parentDataObjectId = dataObject.getParentDataObjectId()) == null) {
            return null;
        }

        return getDataObjectBean(em, parentDataObjectId);
    }

    private Integer getAddressDotId() {
        if (addressDataObjectTypeId == null) {
            DataObjectType dot = dotLocal.getDataObjectType(Address.class.getName());
            addressDataObjectTypeId = dot.getDataObjectTypeId();
        }

        return addressDataObjectTypeId;
    }

    @Override
    public Address getParentAddress(EntityManager em, DataObject dataObject) {
        int addressDotId = getAddressDotId();
        while (dataObject != null &&
                dataObject.getDataObjectType().getDataObjectTypeId() != addressDotId) {
            BigInteger parentId;
            if ((parentId = dataObject.getParentDataObjectId()) == null) {
                return null;
            }
            dataObject = em.find(DataObject.class, parentId);
        }

        if (dataObject == null) {
            return null;
        }

        return em.find(Address.class, dataObject.getDataObjectId());
    }

    @Override
    public <D extends BusinessDocument> D newBusinessDocument(DocumentType documentType) {
        try {
            Class<? extends BusinessDocument> cls = documentType.getDocumentClass();
            D document = (D) cls.newInstance();
            document.setPublisher(session.getOrganization());
            document.setPublisherBranch(session.getBranch());
            document.setPublisherOfficer(session.getPerson());
            document.setDocumentType(documentType.getDbResource());
            document.setDocumentStatus(DocumentStatus.Draft.getDbResource());
            document.setCreationTime(new Date());
            DataObject dataObject = new DataObject();
            dataObject.setDataObjectType(dotLocal.getDataObjectType(cls.getName()));
            document.setDataObject(dataObject);
            return document;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<DbResource> getResources(EntityManager em, Class<? extends Enum> cls,
            Class<? extends Enum>... enumCategoryClasses) {
        Query q = em.createNamedQuery("EnumClass.findByEnumClassName");
        q.setParameter("enumClassName", cls.getName());
        EnumClass enumClass = (EnumClass)q.getSingleResult();
        q = em.createNamedQuery("DbResource.findAllByEnumClass");
        q.setParameter("enumClass", enumClass);
        return new ArrayList<DbResource>(q.getResultList());
    }
}
