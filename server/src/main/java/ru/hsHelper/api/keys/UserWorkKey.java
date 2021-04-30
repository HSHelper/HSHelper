package ru.hsHelper.api.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserWorkKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "work_id")
    private Long workId;

    public UserWorkKey() {
    }

    public UserWorkKey(Long userId, Long workId) {
        this.userId = userId;
        this.workId = workId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWorkKey that = (UserWorkKey) o;
        return userId.equals(that.userId) && workId.equals(that.workId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, workId);
    }
}
