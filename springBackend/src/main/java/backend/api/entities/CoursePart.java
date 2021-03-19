package backend.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class CoursePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "partition_id", nullable = false)
    private Partition partition;

    @OneToMany(mappedBy = "coursePart")
    private Set<UserCoursePartRole> users;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private double weight;
    private double block;
}
