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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private long id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    private String firebaseMessagingToken;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "user_notification",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private Set<Notification> notifications = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserGroupRole> groups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private  Set<UserToPartition> partitions = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserCourseRole> courses = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserCoursePartRole> courseParts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SELECT)
    @NotNull
    @Column(nullable = false)
    private Set<UserWork> userWorks = new HashSet<>();

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String email,
                String firebaseMessagingToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firebaseMessagingToken = firebaseMessagingToken;
    }

    // For internal usage only
    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String email) {
        this(firstName, lastName, email, null);
    }

    public User() {}

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseMessagingToken() {
        return firebaseMessagingToken;
    }

    public void setFirebaseMessagingToken(String firebaseMessagingToken) {
        this.firebaseMessagingToken = firebaseMessagingToken;
    }

    public void addUserGroupRole(UserGroupRole userGroupRole) {
        groups.add(userGroupRole);
    }

    public void removeUserGroupRole(UserGroupRole userGroupRole) {
        groups.remove(userGroupRole);
    }

    public void addPartition(UserToPartition partition) {
        partitions.add(partition);
    }

    public void removePartition(UserToPartition partition) {
        partitions.remove(partition);
    }

    public void addCourse(UserCourseRole userCourseRole) {
        courses.add(userCourseRole);
    }

    public void removeCourse(UserCourseRole userCourseRole) {
        courses.remove(userCourseRole);
    }

    public void addCoursePart(UserCoursePartRole userCoursePartRole) {
        courseParts.add(userCoursePartRole);
    }

    public void removeCoursePart(UserCoursePartRole userCoursePartRole) {
        courseParts.remove(userCoursePartRole);
    }

    public void addWork(UserWork userWork) {
        userWorks.add(userWork);
    }

    public void removeWork(UserWork userWork) {
        userWorks.remove(userWork);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }
}
