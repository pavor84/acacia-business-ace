/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.FormContainer;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.RelationshipType;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.Organization;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBTextField;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "teams", catalog = "acacia", schema = "public",
uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_id", "team_name"})
})
@NamedQueries({
    @NamedQuery(
        name = Team.NQ_FIND_ALL,
        query = "SELECT t FROM Team t" +
                " WHERE" +
                "  t.organization = :organization"
    ),
    @NamedQuery(
        name = Team.NQ_FIND_BY_BUSINESS_UNIT,
        query = "SELECT t FROM Team t" +
                " WHERE" +
                "  t.businessUnit = :businessUnit"
    )
})
@Form(
    formContainers={
        @FormContainer(
            name="memberList",
            title="Members",
            depends={"<entityForm>"},
            container=@Component(
                componentClass=JBPanel.class
            ),
            relationshipType=RelationshipType.OneToMany,
            entityClass=TeamMember.class
        )
    },
    serviceClass=UsersServiceRemote.class
)
public class Team extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "Team";
    public static final String NQ_FIND_ALL = CLASS_NAME + ".findAll";
    public static final String NQ_FIND_BY_BUSINESS_UNIT = CLASS_NAME + ".findByBusinessUnit";

    @Id
    @Basic(optional = false)
    @Column(name = "team_id", nullable = false, precision = 19, scale = 0)
    private BigInteger teamId;

    @Basic(optional = false)
    @Column(name = "team_name", nullable = false, length = 50)
    @Property(title="Team Name",
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Team Name:"
            ),
            secondComponent=@Component(
                componentClass=JBTextField.class
            )
        )
    )
    private String teamName;

    @JoinColumn(name = "business_unit_id", referencedColumnName = "business_unit_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Business Unit",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.BusinessUnitListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Business Unit:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private BusinessUnit businessUnit;

    @JoinColumn(name = "status_id", referencedColumnName = "resource_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Status",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.enums.AccountStatus",
            constructorParameters={
                @PropertyName(getter="entity", setter="class")
            }
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="Status:"
            ),
            secondComponent=@Component(
                componentClass=JBComboBox.class
            )
        )
    )
    private DbResource status;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", nullable = false)
    @ManyToOne(optional = false)
    private Organization organization;

    @JoinColumn(name = "team_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public Team() {
    }

    public Team(BigInteger teamId) {
        this.teamId = teamId;
    }

    public Team(BigInteger teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public BigInteger getTeamId() {
        return teamId;
    }

    public void setTeamId(BigInteger teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
    }

    @Override
    public BigInteger getId() {
        return getTeamId();
    }

    @Override
    public void setId(BigInteger id) {
        setTeamId(id);
    }

    @Override
    public BigInteger getParentId() {
        if(organization != null) {
            return organization.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        return getTeamName();
    }
}
