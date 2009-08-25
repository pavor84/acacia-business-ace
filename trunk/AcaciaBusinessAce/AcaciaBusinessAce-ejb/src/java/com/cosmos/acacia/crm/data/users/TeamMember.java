/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.data.users;

import com.cosmos.acacia.annotation.Component;
import com.cosmos.acacia.annotation.Form;
import com.cosmos.acacia.annotation.FormComponentPair;
import com.cosmos.acacia.annotation.Property;
import com.cosmos.acacia.annotation.PropertyName;
import com.cosmos.acacia.annotation.SelectableList;
import com.cosmos.acacia.crm.bl.users.UsersServiceRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.swingb.JBComboBox;
import com.cosmos.swingb.JBComboList;
import com.cosmos.swingb.JBLabel;
import java.io.Serializable;
import java.util.UUID;
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
import org.hibernate.annotations.Type;

/**
 *
 * @author Miro
 */
@Entity
@Table(name = "team_members", catalog = "acacia", schema = "public",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id", "user_organization_id"})
})
@NamedQueries({
    @NamedQuery(
        name = TeamMember.NQ_FIND_BY_TEAM,
        query = "SELECT t FROM TeamMember t" +
                " WHERE" +
                "  t.team = :team"
    ),
    @NamedQuery(
        name = TeamMember.NQ_FIND_BY_USER_ORGANIZATION,
        query = "SELECT t FROM TeamMember t" +
                " WHERE" +
                "  t.team.organization = :organization" +
                "  and t.userOrganization = :userOrganization"
    )
})
@Form(
    serviceClass=UsersServiceRemote.class
)
public class TeamMember extends DataObjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    protected static final String CLASS_NAME = "TeamMember";
    public static final String NQ_FIND_BY_TEAM = CLASS_NAME + ".findByTeam";
    public static final String NQ_FIND_BY_USER_ORGANIZATION = CLASS_NAME + ".findByUserOrganization";
    //
    @Id
    @Basic(optional = false)
    @Column(name = "team_member_id", nullable = false, precision = 19, scale = 0)
    @Type(type="uuid")
    private UUID teamMemberId;

    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="Team")
    private Team team;

    @JoinColumn(name = "user_organization_id", referencedColumnName = "user_organization_id", nullable = false)
    @ManyToOne(optional = false)
    @Property(title="User",
        selectableList=@SelectableList(
            className="com.cosmos.acacia.crm.gui.users.UserOrganizationListPanel"
        ),
        formComponentPair=@FormComponentPair(
            parentContainerName=PRIMARY_INFO,
            firstComponent=@Component(
                componentClass=JBLabel.class,
                text="User:"
            ),
            secondComponent=@Component(
                componentClass=JBComboList.class
            )
        )
    )
    private UserOrganization userOrganization;

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

    @JoinColumn(name = "team_member_id", referencedColumnName = "data_object_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private DataObject dataObject;

    public TeamMember() {
    }

    public TeamMember(UUID teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public UUID getTeamMemberId() {
        return teamMemberId;
    }

    public void setTeamMemberId(UUID teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    @Override
    public DataObject getDataObject() {
        return dataObject;
    }

    @Override
    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserOrganization getUserOrganization() {
        return userOrganization;
    }

    public void setUserOrganization(UserOrganization userOrganization) {
        this.userOrganization = userOrganization;
    }

    public DbResource getStatus() {
        return status;
    }

    public void setStatus(DbResource status) {
        this.status = status;
    }

    @Override
    public UUID getId() {
        return getTeamMemberId();
    }

    @Override
    public void setId(UUID id) {
        setTeamMemberId(id);
    }

    @Override
    public UUID getParentId() {
        if(team != null) {
            return team.getId();
        }

        return null;
    }

    @Override
    public String getInfo() {
        if(team == null && userOrganization == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(team).append(":").append(userOrganization);

        return sb.toString();
    }
}
