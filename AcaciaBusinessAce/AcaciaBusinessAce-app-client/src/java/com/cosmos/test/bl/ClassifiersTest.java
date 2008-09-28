package com.cosmos.test.bl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cosmos.acacia.crm.bl.impl.ClassifiersBean;
import com.cosmos.acacia.crm.bl.impl.ClassifiersRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.ClassifierGroup;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.gui.AcaciaPanel;
import com.cosmos.beansbinding.EntityProperties;

/**
 *
 * Created	:	06.04.2008
 * @author	Bozhidar Bozhanov
 * @version $Id: $
 *
 * Business logic test for
 * {@link ClassifiersBean}
 *
 */
public class ClassifiersTest {

    @EJB
    private ClassifiersRemote formSession;


    @Before
    public void setUp() {
        if ( formSession==null )
                formSession = AcaciaPanel.getBean(ClassifiersRemote.class, false);
    }

    @Test
    public void methodsTest(){
        EntityProperties entityProperties =
            formSession.getClassifiedObjectEntityProperties();
        Assert.assertNotNull(entityProperties);

        entityProperties = formSession.getClassifierEntityProperties();
        Assert.assertNotNull(entityProperties);

        entityProperties = formSession.getClassifierGroupEntityProperties();
        Assert.assertNotNull(entityProperties);
    }

    /**
     * Test - create, retrieve, update, delete operations
     * over Warehouse
     * @throws UncompleteUnitTestException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void crudTest(){

        LoginResult loginResult = TestUtils.login();

        BigInteger parentId = loginResult.getOrganization().getId();

        // Creating a classifier group
        ClassifierGroup group = formSession.newClassifierGroup(parentId);
        group.setClassifierGroupCode(TestUtils.getRandomString(10));
        group.setClassifierGroupName(TestUtils.getRandomString(10));
        group = formSession.saveClassifierGroup(group);

        // Create the classifier
        Classifier classifier = formSession.newClassifier();
        classifier.setClassifierGroup(group);
        classifier.setClassifierCode(TestUtils.getRandomString(10));
        classifier.setClassifierName(TestUtils.getRandomString(10));
        classifier = formSession.saveClassifier(classifier, group.getId());

        // Get a data object - the one of the group
        DataObject dataObject = group.getDataObject();

        // Add data object type constraint
        formSession.addDataObjectTypeConstraint(classifier,
                dataObject.getDataObjectType());

        // Classify the data object
        formSession.classifyDataObject(dataObject, classifier);

        // Retrieve data objects for this classifier
        List list = formSession.getDataObjects(classifier);
        Assert.assertTrue(list != null && list.size() == 1);

        // Retrieve all classifiers for the group
        List cList = formSession.getClassifiers(group.getId(), dataObject.getDataObjectType());
        Assert.assertTrue(cList != null && cList.size() == 1);

        // Cleanup actions

        // Unclassify the object
        formSession.unclassifyDataObject(dataObject, classifier);

        // Remove the constraint
        formSession.removeDataObjectTypeConstraint(classifier,
                dataObject.getDataObjectType());

        // Delete the classifier
        formSession.deleteClassifier(classifier);

        // Delete the group
        formSession.deleteClassifierGroup(group);

        TestUtils.clearLogin(loginResult.getUser());
    }

}
