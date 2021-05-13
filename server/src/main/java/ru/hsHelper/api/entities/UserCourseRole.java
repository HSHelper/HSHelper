package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hsHelper.api.keys.UserCourseRoleKey;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.util.Set;

@Entity
public class UserCourseRole {
    @EmbeddedId
    UserCourseRoleKey id = new UserCourseRoleKey();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.JOIN)
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @Fetch(FetchMode.JOIN)
    private Course course;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;

    public UserCourseRole() {
    }

    public UserCourseRole(User user, Course course, Set<Role> roles) {
        this.user = user;
        this.course = course;
        this.roles = roles;
    }

    public UserCourseRoleKey getId() {
        return id;
    }

    public void setId(UserCourseRoleKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void removeUserCourseAndRoles() {
        user.removeCourse(this);
        course.removeUser(this);
        for (Role role : roles) {
            role.removeUserCourseRole(this);
        }
    }
}
