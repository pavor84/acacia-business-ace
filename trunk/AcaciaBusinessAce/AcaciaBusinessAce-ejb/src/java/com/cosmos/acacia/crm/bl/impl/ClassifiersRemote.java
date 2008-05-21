package com.cosmos.acacia.crm.bl.impl;

import java.util.List;

import javax.ejb.Remote;

import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierAppliedForDot;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectType;
import com.cosmos.beansbinding.EntityProperties;

/**
 * An EJB for handling classifiers
 *
 * @author Bozhidar Bozhanov
 */
@Remote
public interface ClassifiersRemote {

    /* Methods for handling classifiers */

    /**
     * Lists all classifiers belonging to a parent (specified group),
     * and applicable for the specified data object type
     * Returns all classifiers if the group is null;
     * Returns all existing classifiers if the object type is null;
     *
     * @param parentDataObject the data object of the classifier group
     * @param dataObjectType the data object type of the object
     * @return the list of classifiers
     */
    List<Classifier> getClassifiers(DataObject parentDataObject,
            DataObjectType dataObjectType);
    
    
    /**
     * Lists all classifiers applied to a specified data object
     *
     * @param classifiedDataObject
     * @return the list of classifiers
     */
    List<Classifier> getClassifiers(DataObject classifiedDataObject);
    
    /**
     * Creates a new classifier
     *
     * @return the newly created classifier
     */
    Classifier newClassifier();

    /**
     * Saves a classifier
     *
     * @param classifier the classifier to be saved
     * @param parentDataObject the parent object (classifier group)
     * @return the saved classifier
     */
    Classifier saveClassifier(Classifier classifier, DataObject parentDataObject);

    /**
     * Deletes the specified classifier
     *
     * @param classifier
     * @return the version of the deleted Classifier
     */
    int deleteClassifier(Classifier classifier);

    /* End of methods for handling classifiers */

    /* Methods for handling classifier groups */

    /**
     * Lists all classifiers groups.
     *
     * @return the list of classifier groups
     */
    List<ClassifierGroup> getClassifierGroups();

    /**
     * Creates a new classifier group
     *
     * @return the newly created classifier group
     */
    ClassifierGroup newClassifierGroup();

    /**
     * Saves a classifier group
     *
     * @param classifierGroup the classifier group to be saved
     * @return the saved classifier group
     */
    ClassifierGroup saveClassifierGroup(ClassifierGroup classifierGroup);

    /**
     * Deletes the specified classifier group
     *
     * @param classifier
     * @return the version of the deleted Classifier Group
     */
    int deleteClassifierGroup(ClassifierGroup classifierGroup);

    /* End of methods for handling classifier groups */

    /* Other methods */

    /**
     * Applies a classifier to a specified data object
     *
     * @param dataObject the dataObject to be classified
     * @param classifier the classifier to be applied
     */
    void classifyDataObject(DataObject dataObject, Classifier classifier);

    /**
     * Removes a classifier from a specified data object
     *
     * @param dataObject the dataObject to be unclassified
     * @param classifier the classifier to be removed
     */
    void unclassifyDataObject(DataObject dataObject, Classifier classifier);

    /**
     * Removes a classifier from a specified data object
     *
     * @param classifiedObject
     */
    void unclassifyDataObject(ClassifiedObject classifiedObject);


    /**
     * Adds a possible data object type to which the classifier can be added
     * Remember, that if a classifier has no data objects types associated,
     * it is presumed that all data object types are applicable.
     *
     * @param classifier the classifier
     * @param dot the data object type
     */
    void addDataObjectTypeConstraint(Classifier classifier, DataObjectType dot);

    /**
     * Removes a possible data object type to which the classifier can be added
     * Remember, that if a classifier has no data objects types associated,
     * it is presumed that all data object types are applicable.
     *
     * @param classifier the classifier
     * @param dot the data object type
     */
    void removeDataObjectTypeConstraint(Classifier classifier, DataObjectType dot);

    /**
     * Removes a possible data object type to which the classifier can be added
     * Remember, that if a classifier has no data objects types associated,
     * it is presumed that all data object types are applicable.
     *
     * @param classifierAppliedForDot the constraint
     * @param
     */
    void removeDataObjectTypeConstraint(ClassifierAppliedForDot classifierAppliedForDot);


    /**
     * Removes all data object types added for the classifier, thus making all
     * existing data object types applicable for it
     *
     * @param classifier
     */
    void clearDataObjectTypeConstraints(Classifier classifier);
    
    
    /**
     * Gets the entity properties of Classifier
     * 
     * @return the entity properties
     */
    EntityProperties getClassifierEntityProperties();
   
    /**
     * Gets the entity properties of ClassifierGroup
     * 
     * @return the entity properties
     */
    EntityProperties getClassifierGroupEntityProperties();
    
    /**
     * Gets the entity properties of ClassifierGroup
     * 
     * @return the entity properties
     */
    EntityProperties getClassifiedObjectEntityProperties();
    
    /**
     * Gets the entity properties of DataObjectType
     * 
     * @return the entity properties
     */
    EntityProperties getDataObjectTypeEntityProperties();
    
     /**
     * Creates a new classified object
     * 
     * @return the newly created classified object
     */
    ClassifiedObject newClassifiedObject();
    
    /**
     * Lists data object types applied for the specified Classifier
     * 
     * @param classifier
     * @return the list
     */
    List<DataObjectType> getDataObjectTypes(Classifier classifier);
    
    
    /**
     * Lists all data object types
     * 
     * @return the list
     */
    List<DataObjectType> getDataObjectTypes();
    
}