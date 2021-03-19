package backend.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    Set<UserGroupRole> userGroupRoles;
}
