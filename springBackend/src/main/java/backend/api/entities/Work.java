package backend.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
public class Work {
    public enum WorkType {
        HOMEWORK,
        CONTROLWORK
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private Date deadline;
    private double weight;
    private double block;
    private WorkType workType;

    @ManyToOne
    @JoinColumn(name = "course_part_id", nullable = false)
    private CoursePart coursePart;

    @OneToMany(mappedBy = "work")
    private Set<UserWork> users;
}
