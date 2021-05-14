package ru.hsHelper.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Permissions {
    public enum PermissionType {
        VIEW,
        UPDATE,
        COMMENT,
        CREATE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @Column(unique = true, nullable = false)
    @NotNull
    private PermissionType permissionType;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private Set<Role> roles = new HashSet<>();


    public Permissions() {
        this.permissionType = PermissionType.VIEW;
    }

    public Permissions(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @PreRemove
    private void removePermissionsFromRoles() {
        for (Role role : roles) {
            role.getPermissions().remove(this);
        }
    }
}
