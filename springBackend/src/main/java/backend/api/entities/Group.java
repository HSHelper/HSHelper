package backend.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private Set<UserGroupRole> userGroupRoleSet;

    @OneToMany(mappedBy = "group")
    private Set<Partition> partitions;

    @OneToMany(mappedBy = "group")
    private Set<Course> courses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserGroupRole> getUserGroupRoleSet() {
        return userGroupRoleSet;
    }

    public void setUserGroupRoleSet(Set<UserGroupRole> userGroupRoleSet) {
        this.userGroupRoleSet = userGroupRoleSet;
    }

    public Set<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(Set<Partition> partitions) {
        this.partitions = partitions;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
