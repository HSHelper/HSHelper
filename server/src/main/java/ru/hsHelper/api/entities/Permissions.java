package ru.hsHelper.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.swing.text.View;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Permissions {
    public enum PermissionType {
        VIEW,
        UPDATE,
        COMMENT
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private PermissionType permissionType;

    @JsonBackReference
    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
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
