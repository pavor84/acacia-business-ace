/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.util.UUID;
import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.product.PatternMaskFormat;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 *
 * Created	:	22.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class PatternMaskFormatListPanel extends AbstractTablePanel<PatternMaskFormat> {

    protected static Logger log = Logger.getLogger(PatternMaskFormatListPanel.class);

    @EJB
    private PatternMaskListRemote formSession;

    /** Creates new form PersonsListPanel */
    public PatternMaskFormatListPanel(UUID parentDataObjectId){
        super(parentDataObjectId);
    }

    private BindingGroup formatsBindingGroup;
    private List<PatternMaskFormat> formats;
    private EntityProperties entityProperties;

    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();

        setVisible(Button.Select, false);

        entityProperties = getFormSession().getPatternMaskEntityProperties();

        refreshDataTable(entityProperties);
    }

    protected List<PatternMaskFormat> getFormats()
    {
        formats = getFormSession().listPatternsByName(getParentDataObjectId());
        return formats;
    }

    private void refreshDataTable(EntityProperties entProps){
        if ( formatsBindingGroup != null )
            formatsBindingGroup.unbind();
        formatsBindingGroup = new BindingGroup();

        List<PatternMaskFormat> formatsList = getFormats();

        AcaciaTable formatsTable = getDataTable();
        formatsTable.bind(formatsBindingGroup, formatsList,
            entityProperties
            );

        formatsTable.bindComboBoxCellEditor(formatsBindingGroup, formatsList, entityProperties.getEntityProperty("owner"));

        formatsBindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProperties);

        return t;
    }

    @Override
    protected boolean deleteRow(PatternMaskFormat rowObject) {
        return
            getFormSession().deletePatternMaskFormat(rowObject);
    }

    @Override
    protected PatternMaskFormat modifyRow(PatternMaskFormat rowObject)
    {
        if(rowObject instanceof PatternMaskFormat)
        {
            PatternMaskFormatPanel panel = new PatternMaskFormatPanel(rowObject);
            DialogResponse response = panel.showDialog(this);
            if(DialogResponse.SAVE.equals(response)) {
                return (PatternMaskFormat) panel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected PatternMaskFormat newRow() {
        PatternMaskFormat newFormat = getFormSession().newPatternMaskFormat(getParentDataObjectId());

        PatternMaskFormatPanel formatPanel = new PatternMaskFormatPanel(newFormat);
        DialogResponse response = formatPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response)) {
            return (PatternMaskFormat) formatPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(PatternMaskFormat rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(PatternMaskFormat rowObject) {
        return true;
    }

    /**
     * Getter for formSession
     * @return PatternMaskListRemote
     */
    public PatternMaskListRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(PatternMaskListRemote.class);

        return formSession;
    }

    /**
     * Setter for formSession
     * @param formSession - PatternMaskListRemote
     */
    public void setFormSession(PatternMaskListRemote formSession) {
        this.formSession = formSession;
    }
}
