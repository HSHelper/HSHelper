package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    public enum RoleType {
        ADMIN,
        STUDENT,
        OBSERVER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private RoleType roleType;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Fetch(FetchMode.JOIN)
    private Set<Permissions> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserGroupRole> userGroupRoles = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserCourseRole> userCourseRoles = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserCoursePartRole> userCoursePartRoles = new HashSet<>();

    public Role() {
        this.roleType = RoleType.OBSERVER;
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserGroupRole> getUserGroupRoles() {
        return userGroupRoles;
    }

    public void setUserGroupRoles(Set<UserGroupRole> userGroupRoles) {
        this.userGroupRoles = userGroupRoles;
    }

    public Set<UserCourseRole> getUserCourseRoles() {
        return userCourseRoles;
    }

    public void setUserCourseRoles(Set<UserCourseRole> userCourseRoles) {
        this.userCourseRoles = userCourseRoles;
    }

    public Set<UserCoursePartRole> getUserCoursePartRoles() {
        return userCoursePartRoles;
    }

    public void setUserCoursePartRoles(Set<UserCoursePartRole> userCoursePartRoles) {
        this.userCoursePartRoles = userCoursePartRoles;
    }

    @PreRemove
    private void removeRolesFromPermissions() {
        for (Permissions p : permissions) {
            p.getRoles().remove(this);
        }
    }
}