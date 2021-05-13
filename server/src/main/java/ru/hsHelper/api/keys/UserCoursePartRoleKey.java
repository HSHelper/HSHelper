package ru.hsHelper.api.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCoursePartRoleKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_part_id")
    private Long coursePartId;

    public UserCoursePartRoleKey() {
    }

    public UserCoursePartRoleKey(Long userId, Long coursePartId) {
        this.userId = userId;
        this.coursePartId = coursePartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoursePartId() {
        return coursePartId;
    }

    public void setCoursePartId(Long coursePartId) {
        this.coursePartId = coursePartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCoursePartRoleKey that = (UserCoursePartRoleKey) o;
        return userId.equals(that.userId) && coursePartId.equals(that.coursePartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, coursePartId);
    }
}
