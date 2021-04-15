package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String LastName;
    private String email;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
    }

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private Set<UserGroupRole> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private  Set<UserToPartition> partitions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private Set<UserCourseRole> courses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private Set<UserCoursePartRole> courseParts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private Set<UserWork> userWorks = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Set<UserGroupRole> getGroups() {
        return groups;
    }

    public void setGroups(Set<UserGroupRole> groups) {
        this.groups = groups;
    }

    public Set<UserToPartition> getPartitions() {
        return partitions;
    }

    public void setPartitions(Set<UserToPartition> partitions) {
        this.partitions = partitions;
    }

    public Set<UserCourseRole> getCourses() {
        return courses;
    }

    public void setCourses(Set<UserCourseRole> courses) {
        this.courses = courses;
    }

    public Set<UserCoursePartRole> getCourseParts() {
        return courseParts;
    }

    public void setCourseParts(Set<UserCoursePartRole> courseParts) {
        this.courseParts = courseParts;
    }

    public Set<UserWork> getUserWorks() {
        return userWorks;
    }

    public void setUserWorks(Set<UserWork> userWorks) {
        this.userWorks = userWorks;
    }
}
