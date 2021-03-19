package ru.hsHelper.api.entities;

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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "default_partition_id")
    private Partition defaultPartition;

    @OneToMany(mappedBy = "course")
    private Set<UserCourseRole> users = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<CoursePart> courseParts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

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

    public Partition getDefaultPartition() {
        return defaultPartition;
    }

    public void setDefaultPartition(Partition defaultPartition) {
        this.defaultPartition = defaultPartition;
    }

    public Set<UserCourseRole> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCourseRole> users) {
        this.users = users;
    }

    public Set<CoursePart> getCourseParts() {
        return courseParts;
    }

    public void setCourseParts(Set<CoursePart> courseParts) {
        this.courseParts = courseParts;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
