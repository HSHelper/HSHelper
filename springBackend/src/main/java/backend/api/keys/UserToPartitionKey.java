package backend.api.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserToPartitionKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "partition_id")
    private Long partitionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToPartitionKey that = (UserToPartitionKey) o;
        return userId.equals(that.userId) && partitionId.equals(that.partitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, partitionId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Long partitionId) {
        this.partitionId = partitionId;
    }
}
