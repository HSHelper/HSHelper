package backend.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "permissions")
    private Set<Role> roles;
}
