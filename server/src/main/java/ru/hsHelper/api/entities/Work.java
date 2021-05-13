package ru.hsHelper.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.util.Date;
import java.util.HashSet;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_part_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private CoursePart coursePart;

    @JsonIgnore
    @OneToMany(mappedBy = "work", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    private Set<UserWork> users = new HashSet<>();

    public Work() {
    }

    public Work(String name, String description, Date deadline, double weight, double block, WorkType workType,
                CoursePart coursePart) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.weight = weight;
        this.block = block;
        this.workType = workType;
        this.coursePart = coursePart;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public CoursePart getCoursePart() {
        return coursePart;
    }

    public void setCoursePart(CoursePart coursePart) {
        this.coursePart = coursePart;
    }

    public Set<UserWork> getUsers() {
        return users;
    }

    public void setUsers(Set<UserWork> users) {
        this.users = users;
    }

    public void addUser(UserWork userWork) {
        users.add(userWork);
    }

    public void removeUser(UserWork userWork) {
        users.remove(userWork);
    }
}
