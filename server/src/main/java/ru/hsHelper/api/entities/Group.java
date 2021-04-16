package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private Set<UserGroupRole> userGroupRoleSet = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE, CascadeType.PERSIST},orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private Set<Partition> partitions = new HashSet<>();

    @OneToMany(mappedBy = "group",  cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private Set<Course> courses = new HashSet<>();

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

    public void addUserGroupRole(UserGroupRole userGroupRole) {
        userGroupRoleSet.add(userGroupRole);
    }

    public void removeUserGroupRole(UserGroupRole userGroupRole) {
        userGroupRoleSet.remove(userGroupRole);
    }

    public void addPartition(Partition partition) {
        partitions.add(partition);
    }

    public void removePartition(Partition partition) {
        partitions.remove(partition);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
