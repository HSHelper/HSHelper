package ru.hsHelper.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    public enum RoleType {
        ADMIN,
        STUDENT,
        OBSERVER,
        TEACHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(nullable = false)
    @NotNull
    private RoleType roleType;


    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private Set<Permissions> permissions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserGroupRole> userGroupRoles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserCourseRole> userCourseRoles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
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

    public void addUserGroupRole(UserGroupRole userGroupRole) {
        userGroupRoles.add(userGroupRole);
    }

    public void removeUserGroupRole(UserGroupRole userGroupRole) {
        userGroupRoles.remove(userGroupRole);
    }

    public void addUserCourseRole(UserCourseRole userCourseRole) {
        userCourseRoles.add(userCourseRole);
    }

    public void removeUserCourseRole(UserCourseRole userCourseRole) {
        userCourseRoles.remove(userCourseRole);
    }

    public void addUserCoursePartRole(UserCoursePartRole userCoursePartRole) {
        userCoursePartRoles.add(userCoursePartRole);
    }

    public void removeUserCoursePartRole(UserCoursePartRole userCoursePartRole) {
        userCoursePartRoles.remove(userCoursePartRole);
    }

    @PreRemove
    private void removeRolesFromPermissions() {
        for (Permissions p : permissions) {
            p.getRoles().remove(this);
        }
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.getRoles().remove(this);
        }
    }
}
