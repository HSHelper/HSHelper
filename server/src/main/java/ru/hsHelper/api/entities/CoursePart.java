package ru.hsHelper.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
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
public class CoursePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "partition_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Partition partition;

    @JsonIgnore
    @OneToMany(mappedBy = "coursePart", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    private Set<UserCoursePartRole> users = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "coursePart", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    private Set<Work> works = new HashSet<>();

    private double weight;
    private double block;

    public CoursePart() {
    }

    public CoursePart(String name, Partition partition, Course course, double weight, double block) {
        this.name = name;
        this.partition = partition;
        this.course = course;
        this.weight = weight;
        this.block = block;
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

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    public Set<UserCoursePartRole> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCoursePartRole> users) {
        this.users = users;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Work> getWorks() {
        return works;
    }

    public void setWorks(Set<Work> works) {
        this.works = works;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBlock() {
        return block;
    }

    public void setBlock(double block) {
        this.block = block;
    }

    public void addUser(UserCoursePartRole userCoursePartRole) {
        users.add(userCoursePartRole);
    }

    public void removeUser(UserCoursePartRole userCoursePartRole) {
        users.remove(userCoursePartRole);
    }

    public void addWork(Work work) {
        works.add(work);
    }

    public void removeWork(Work work) {
        works.remove(work);
    }
}
