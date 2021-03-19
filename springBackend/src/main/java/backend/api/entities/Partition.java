package backend.api.entities;

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
public class Partition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "partition")
    private Set<UserToPartition> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "defaultPartition")
    private Set<Course> coursesWithThisDefaultPartition = new HashSet<>();

    @OneToMany(mappedBy = "partition")
    private Set<CoursePart> courseParts = new HashSet<>();

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

    public Set<UserToPartition> getUsers() {
        return users;
    }

    public void setUsers(Set<UserToPartition> users) {
        this.users = users;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Course> getCoursesWithThisDefaultPartition() {
        return coursesWithThisDefaultPartition;
    }

    public void setCoursesWithThisDefaultPartition(Set<Course> coursesWithThisDefaultPartition) {
        this.coursesWithThisDefaultPartition = coursesWithThisDefaultPartition;
    }

    public Set<CoursePart> getCourseParts() {
        return courseParts;
    }

    public void setCourseParts(Set<CoursePart> courseParts) {
        this.courseParts = courseParts;
    }
}
