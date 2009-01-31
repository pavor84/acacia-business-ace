/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.contactbook;

import com.cosmos.acacia.crm.bl.contactbook.BusinessPartnersListRemote;
import com.cosmos.acacia.crm.data.BusinessPartner;
import com.cosmos.acacia.crm.data.ClassifiedObject;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.acacia.crm.gui.ClassifiersListPanel;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaComboList;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.swing.BorderFactory;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.swingbinding.JTableBinding;

/**
 *
 * @author Miro
 */
public class BusinessPartnersListPanel extends AbstractTablePanel {

    @EJB
    private static BusinessPartnersListRemote formSession;

    private Classifier partnerClassifier;
    private BindingGroup bindingGroup;

    private JBPanel topPanel;
    private AcaciaComboList comboList;
    private ClassifiersListPanel comboListPanel;
    private BindingGroup comboListBindingGroup;
    private ClassifiedObject classifiedObject;

    public BusinessPartnersListPanel() {
        this(null);
    }

    public BusinessPartnersListPanel(Classifier partnerClassifier) {
        this.partnerClassifier = partnerClassifier;
    }

    @Override
    protected boolean deleteRow(Object rowObject) {
         if(rowObject != null)
        {
            //deleteCity((City) rowObject);
            return true;
        }

        return false;
    }

    @Override
    protected Object modifyRow(Object rowObject) {
        if(rowObject != null)
        {
//            CityPanel cityPanel =
//                    new CityPanel((City) rowObject);
//            DialogResponse response = cityPanel.showDialog(this);
//            if(DialogResponse.SAVE.equals(response))
//            {
//                return cityPanel.getSelectedValue();
//            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
//        CityPanel cityPanel = null;
//        if (country != null)
//            cityPanel = new CityPanel(country);
//        else
//            cityPanel = new CityPanel();
//
//        DialogResponse response = cityPanel.showDialog(this);
//        if(DialogResponse.SAVE.equals(response))
//        {
//            return cityPanel.getSelectedValue();
//        }
        return null;
    }

    @Override
    protected void initData() {
        super.initData();

        setTopComponent(getTopPanel());

        if(partnerClassifier != null) {
            setTitle(partnerClassifier.getClassifierName());
        }

        bindingGroup = new BindingGroup();
        AcaciaTable citiesTable = getDataTable();
        JTableBinding tableBinding = citiesTable.bind(bindingGroup, getBusinessPartners(), getEntityProperties());

        bindingGroup.bind();
    }

    protected JBPanel getTopPanel() {
        if(topPanel == null) {
            topPanel = new JBPanel();
            topPanel.setLayout(new BorderLayout());
            comboList = new AcaciaComboList();
            comboList.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            topPanel.add(comboList, BorderLayout.CENTER);
            JBLabel partnerClassiferLabel = new JBLabel();
            partnerClassiferLabel.setName("partnerClassifer");
            partnerClassiferLabel.setText(getResourceMap().getString("partnerClassifer.text"));
            partnerClassiferLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            topPanel.add(partnerClassiferLabel, BorderLayout.WEST);

            classifiedObject = new ClassifiedObject();
            comboListBindingGroup = new BindingGroup();
            PropertyDetails propDetails = new PropertyDetails(
                    "classifier", "Classifier", Classifier.class.getName());
            propDetails.setColumnName("classifier");

            if(comboListPanel == null)
                comboListPanel = new ClassifiersListPanel((BigInteger)null);
            comboList.bind(comboListBindingGroup, comboListPanel, classifiedObject,
                    propDetails, "${classifierName}", UpdateStrategy.READ_WRITE);
            comboList.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    onPartnerClassifier((Classifier)e.getItem());
                }
            }, true);

            comboListBindingGroup.bind();
        }

        return topPanel;
    }

    public Classifier getPartnerClassifier() {
        return partnerClassifier;
    }

    public void setPartnerClassifier(Classifier partnerClassifier) {
        this.partnerClassifier = partnerClassifier;
        setEnabled(Button.New, partnerClassifier != null);
        refreshTable();
    }

    protected void onPartnerClassifier(Classifier partnerClassifier) {
        setPartnerClassifier(partnerClassifier);
    }

    protected void refreshTable() {
        AcaciaTable table = getDataTable();
        List data = table.getData();
        if(data != null) {
            data.clear();
            data.addAll(getBusinessPartners());
        }
    }

    protected List<BusinessPartner> getBusinessPartners() {
        return getFormSession().getBusinessPartners(getPartnerClassifier());
    }

    protected EntityProperties getEntityProperties() {
        return getFormSession().getListingEntityProperties();
    }

    protected BusinessPartnersListRemote getFormSession()
    {
        if(formSession == null)
            formSession = getBean(BusinessPartnersListRemote.class);

        return formSession;
    }

}
