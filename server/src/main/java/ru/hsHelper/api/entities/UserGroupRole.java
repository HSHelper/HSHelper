package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hsHelper.api.keys.UserGroupRoleKey;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PreRemove;
import java.util.Set;

@Entity
public class UserGroupRole {
    @EmbeddedId
    private UserGroupRoleKey id = new UserGroupRoleKey();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.JOIN)
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    @Fetch(FetchMode.JOIN)
    private Group group;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;

    public UserGroupRole(User user, Group group, Set<Role> roles) {
        this.user = user;
        this.group = group;
        this.roles = roles;
    }

    public UserGroupRole() {

    }

    public UserGroupRoleKey getId() {
        return id;
    }

    public void setId(UserGroupRoleKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void removeUserGroupAndRoles() {
        user.removeUserGroupRole(this);
        group.removeUserGroupRole(this);
        for (Role role : roles) {
            role.removeUserGroupRole(this);
        }
    }
}