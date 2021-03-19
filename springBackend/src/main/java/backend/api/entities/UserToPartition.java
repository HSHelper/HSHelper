package backend.api.entities;

import backend.api.keys.UserToPartitionKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class UserToPartition {
    @EmbeddedId
    UserToPartitionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("partitionId")
    @JoinColumn(name = "partition_id")
    private Partition partition;

    private int userPart;

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
}
