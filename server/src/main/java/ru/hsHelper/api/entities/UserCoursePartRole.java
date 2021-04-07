package ru.hsHelper.api.entities;

import ru.hsHelper.api.keys.UserCoursePartRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
