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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "default_partition_id")
    @Fetch(FetchMode.JOIN)
    private Partition defaultPartition;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserCourseRole> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<CoursePart> courseParts = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "group_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Group group;

    public Course() {
    }

    public Course(String name, Partition defaultPartition, Group group) {
        this.name = name;
        this.defaultPartition = defaultPartition;
        this.group = group;
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

    public void addUser(UserCourseRole userCourseRole) {
        users.add(userCourseRole);
    }

    public void removeUser(UserCourseRole userCourseRole) {
        users.remove(userCourseRole);
    }

    public void addCoursePart(CoursePart coursePart) {
        courseParts.add(coursePart);
    }

    public void removeCoursePart(CoursePart coursePart) {
        courseParts.remove(coursePart);
    }
}
