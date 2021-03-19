package backend.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String LastName;

    @OneToMany(mappedBy = "user")
    private Set<UserGroupRole> groups;

    @OneToMany(mappedBy = "user")
    private  Set<UserToPartition> partitions;

    @OneToMany(mappedBy = "user")
    private Set<UserCourseRole> courses;

    @OneToMany(mappedBy = "user")
    private Set<UserCoursePartRole> courseParts;

    @OneToMany(mappedBy = "user")
    private Set<UserWork> userWorks;
}
