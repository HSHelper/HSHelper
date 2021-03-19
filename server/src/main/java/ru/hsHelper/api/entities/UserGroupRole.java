package ru.hsHelper.api.entities;

import ru.hsHelper.api.keys.UserGroupRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class UserGroupRole {
    @EmbeddedId
    private UserGroupRoleKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public UserGroupRole(User user, Group group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
