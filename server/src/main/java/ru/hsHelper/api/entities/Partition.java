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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Partition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "partition", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private Set<UserToPartition> users = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "group_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Group group;

    @JsonIgnore
    @OneToMany(mappedBy = "defaultPartition", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<Course> coursesWithThisDefaultPartition = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "partition", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<CoursePart> courseParts = new HashSet<>();

    public Partition(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Partition() {
    }

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

    public void addUser(UserToPartition user) {
        users.add(user);
    }

    public void removeUser(UserToPartition user) {
        users.remove(user);
    }

    public void addCourse(Course course) {
        coursesWithThisDefaultPartition.add(course);
    }

    public void removeCourse(Course course) {
        coursesWithThisDefaultPartition.remove(course);
    }

    public void addCoursePart(CoursePart coursePart) {
        courseParts.add(coursePart);
    }

    public void removeCoursePart(CoursePart coursePart) {
        courseParts.remove(coursePart);
    }
}
