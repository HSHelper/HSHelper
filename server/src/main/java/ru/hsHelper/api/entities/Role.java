package ru.hsHelper.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permissions permissions;

    @OneToMany(mappedBy = "role")
    private Set<UserGroupRole> userGroupRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserCourseRole> userCourseRoles = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserCoursePartRole> userCoursePartRoles = new HashSet<>();

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

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
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
}
