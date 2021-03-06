package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hsHelper.api.keys.UserWorkKey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class UserWork {
    @EmbeddedId
    @NotNull
    UserWorkKey id = new UserWorkKey();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("workId")
    @JoinColumn(name = "work_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Work work;

    @NotNull
    @Column(nullable = false)
    private Date sendTime;

    @NotNull
    @Column(nullable = false)
    private String solution;

    private Double mark;

    public UserWork() {
    }

    public UserWork(User user, Work work, Date sendTime, String solution) {
        this.user = user;
        this.work = work;
        this.sendTime = sendTime;
        this.solution = solution;
        mark = null;
    }

    public UserWorkKey getId() {
        return id;
    }

    public void setId(UserWorkKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public void removeUserAndWork() {
        user.removeWork(this);
        work.removeUser(this);
    }
}
