package ru.hsHelper.api.entities;

import ru.hsHelper.api.keys.UserCoursePartRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserCoursePartRole {
    @EmbeddedId
    UserCoursePartRoleKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("coursePartId")
    @JoinColumn(name = "course_part_id")
    private CoursePart coursePart;

    @ManyToMany
    Set<Role> roles = new HashSet<>();

    public UserCoursePartRoleKey getId() {
        return id;
    }

    public void setId(UserCoursePartRoleKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CoursePart getCoursePart() {
        return coursePart;
    }

    public void setCoursePart(CoursePart coursePart) {
        this.coursePart = coursePart;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
