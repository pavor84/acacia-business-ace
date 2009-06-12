/*
 * ProductPanelNew.java
 *
 * Created on Неделя, 2009, Февруари 1, 15:20
 */
package com.cosmos.acacia.crm.gui.product;

import com.cosmos.acacia.crm.enums.SpecialPermission;
import com.cosmos.acacia.crm.bl.impl.ProductsListRemote;
import com.cosmos.acacia.crm.data.Classifier;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.ProductCategory;
import com.cosmos.acacia.crm.data.SimpleProduct;
import com.cosmos.acacia.crm.enums.MeasurementUnit;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.EntityFormButtonPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.resource.TextResource;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.ejb.EJB;
import org.jdesktop.beansbinding.BindingGroup;

/**
 *
 * @author  Miro
 */
public class ProductPanel extends BaseEntityPanel {

    @EJB
    private static ProductsListRemote formSession;

    public ProductPanel(SimpleProduct product) {
        super(product.getParentId());
        this.product = product;
        init();
    }

    public SimpleProduct getProduct() {
        return product;
    }

    @Override
    protected void init() {
        initComponents();
        super.init();
    }

    private void initComponents() {
        setName("Form"); // NOI18N
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(920, 480));

        productTabbedPane = new JBTabbedPane();
        productTabbedPane.setName("productTabbedPane"); // NOI18N

        productTabbedPane.addTab(
                getResourceString("primaryInfoPanel.TabConstraints.tabTitle"),
                getPrimaryInfoPanel()); // NOI18N

        productTabbedPane.addTab(
                getResourceString("additionalInfoPanel.TabConstraints.tabTitle"),
                getAdditionalInfoPanel()); // NOI18N

        if (hasProductPricingRights()) {
            productTabbedPane.addTab(
                    getResourceString("productPricingPanel.TabConstraints.tabTitle"),
                    getProductPricingPanel()); // NOI18N
        }

        if (hasProductRights()) {
            productTabbedPane.addTab(
                    getResourceString("suppliersPanel.TabConstraints.tabTitle"),
                    getSuppliersListPanel()); // NOI18N
        }

        add(productTabbedPane, BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.PAGE_END);
    }

    protected boolean hasProductPricingRights() {
        return getRightsManager().isAllowed(SpecialPermission.ProductPricing);
    }

    protected boolean hasProductRights() {
        return getRightsManager().isAllowed(SpecialPermission.Product);
    }

    private SimpleProduct product;
    private BindingGroup productBindingGroup;
    private EntityFormButtonPanel entityFormButtonPanel;
    //
    private JBTabbedPane productTabbedPane;
    private ProductPrimaryInfoPanel primaryInfoPanel;
    private ProductAdditionalInfoPanel additionalInfo;
    private ProductPricingPanel productPricingPanel;
    private ProductSuppliersListPanel suppliersListPanel;
    //
    private EntityProperties productEntityProperties;
    private EntityProperties productSuppliersEntityProperties;

    @Override
    protected void initData() {
        getBindingGroup().bind();
        refreshPrimaryInfoForm();

        getAdditionalInfoPanel().refreshCubature();
        getPrimaryInfoPanel().setProductCodeMaskFormat();
    }

    private ProductPrimaryInfoPanel getPrimaryInfoPanel() {
        if (primaryInfoPanel == null) {
            primaryInfoPanel = new ProductPrimaryInfoPanel(this);
        }

        return primaryInfoPanel;
    }

    private ProductAdditionalInfoPanel getAdditionalInfoPanel() {
        if (additionalInfo == null) {
            additionalInfo = new ProductAdditionalInfoPanel(this);
        }

        return additionalInfo;
    }

    private ProductPricingPanel getProductPricingPanel() {
        if (productPricingPanel == null) {
            productPricingPanel = new ProductPricingPanel(this);
        }

        return productPricingPanel;
    }

    private ProductSuppliersListPanel getSuppliersListPanel() {
        if (suppliersListPanel == null) {
            suppliersListPanel = new ProductSuppliersListPanel(this);
        }

        return suppliersListPanel;
    }

    @Override
    public BindingGroup getBindingGroup() {
        if (productBindingGroup == null) {
            productBindingGroup = new BindingGroup();
        }

        return productBindingGroup;
    }

    protected EntityProperties getProductEntityProperties() {
        if (productEntityProperties == null) {
            productEntityProperties = getFormSession().getProductEntityProperties();
        }

        return productEntityProperties;
    }

    protected EntityProperties getProductSuppliersEntityProperties() {
        if (productSuppliersEntityProperties == null) {
            productSuppliersEntityProperties = getFormSession().getProductSupplierEntityProperties();
        }

        return productSuppliersEntityProperties;
    }

    protected List<DbResource> getMeasureUnits() {
        return getFormSession().getMeasureUnits();
    }

    protected List<DbResource> getMeasureUnits(MeasurementUnit.Category category) {
        return getFormSession().getMeasureUnits(category);
    }

    protected List<DbResource> getProductColors() {
        return getFormSession().getProductColors();
    }

    protected Classifier getProducerClassifier() {
        return getClassifier(Classifier.Producer.getClassifierCode());
    }

    protected ProductCategory refreshProductCategory(ProductCategory category) {
        return getFormSession().refreshProductCategory(category);
    }

    protected void refreshPrimaryInfoForm() {
        getPrimaryInfoPanel().refreshForm();
    }

    @Override
    public void performSave(boolean closeAfter) {
        try {
            product = getFormSession().saveProduct(product);
            setDialogResponse(DialogResponse.SAVE);
            setSelectedValue(product);
            if (closeAfter) {
                close();
            }
        } catch (Exception ex) {
            handleException("product: " + product, ex);
        }
    }

    @Override
    public Object getEntity() {
        return product;
    }

    @Override
    public EntityFormButtonPanel getButtonPanel() {
        if (entityFormButtonPanel == null) {
            entityFormButtonPanel = new EntityFormButtonPanel();
        }

        return entityFormButtonPanel;
    }

    protected ProductsListRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(ProductsListRemote.class);
        }

        return formSession;
    }

    protected String getResourceString(String key) {
        return getResourceMap().getString(key);
    }

    protected void onCurrencyChanged(DbResource dbResource) {
        JBLabel currencyValueLabel = getPrimaryInfoPanel().getCurrencyValueLabel();

        if (dbResource == null) {
            currencyValueLabel.setText(getResourceString("currencyValueLabel.text"));
            return;
        }

        TextResource textResource = (TextResource) dbResource.getEnumValue();
        currencyValueLabel.setText(textResource.toText());
    }

    protected void onProductCategoryChanged() {
        if(hasProductPricingRights()) {
            getProductPricingPanel().refreshPrices();
        }
    }
}
