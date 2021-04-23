package ru.hsHelper.api.requests.add;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class PartitionAddRequest implements Serializable {
    @NotNull
    private Set<Long> partitionIds;

    @NotNull
    private Map<Long, Integer> userParts;

    public PartitionAddRequest(@NotNull Set<Long> partitionIds, @NotNull Map<Long, Integer> userParts) {
        this.partitionIds = partitionIds;
        this.userParts = userParts;
    }

    public PartitionAddRequest() {
    }

    public Set<Long> getPartitionIds() {
        return partitionIds;
    }

    public void setPartitionIds(Set<Long> partitionIds) {
        this.partitionIds = partitionIds;
    }

    public Map<Long, Integer> getUserParts() {
        return userParts;
    }

    public void setUserParts(Map<Long, Integer> userParts) {
        this.userParts = userParts;
    }
}
