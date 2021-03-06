package ru.hsHelper.api.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hsHelper.api.keys.UserToPartitionKey;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;

@Entity
public class UserToPartition {
    @EmbeddedId
    @NotNull
    UserToPartitionKey id = new UserToPartitionKey();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("partitionId")
    @JoinColumn(name = "partition_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Partition partition;

    @NotNull
    private int userPart;

    public UserToPartition(User user, Partition partition, int userPart) {
        this.user = user;
        this.partition = partition;
        this.userPart = userPart;
    }

    public UserToPartition() {
    }

    public UserToPartitionKey getId() {
        return id;
    }

    public void setId(UserToPartitionKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }

    public int getUserPart() {
        return userPart;
    }

    public void setUserPart(int userPart) {
        this.userPart = userPart;
    }

    public void removePartitionAndUser() {
        user.removePartition(this);
        partition.removeUser(this);
    }
}
