package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hsHelper.api.keys.UserCoursePartRoleKey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserCoursePartRole {
    @EmbeddedId
    @NotNull
    UserCoursePartRoleKey id = new UserCoursePartRoleKey();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("coursePartId")
    @JoinColumn(name = "course_part_id")
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    private CoursePart coursePart;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.JOIN)
    @NotNull
    @Column(nullable = false)
    Set<Role> roles = new HashSet<>();

    public UserCoursePartRole() {
    }

    public UserCoursePartRole(User user, CoursePart coursePart, Set<Role> roles) {
        this.user = user;
        this.coursePart = coursePart;
        this.roles = roles;
    }

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

    public void removeUserCoursePartAndRoles() {
        user.removeCoursePart(this);
        coursePart.removeUser(this);
        for (Role role : roles) {
            role.removeUserCoursePartRole(this);
        }
    }
}
