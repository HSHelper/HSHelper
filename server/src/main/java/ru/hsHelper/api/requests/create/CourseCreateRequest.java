package ru.hsHelper.api.requests.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CourseCreateRequest implements Serializable {
    @NotEmpty
    private String name;

    @NotNull
    private long partitionId;

    @NotNull
    private long groupId;

    public CourseCreateRequest() {
    }

    public CourseCreateRequest(@NotEmpty String name, @NotNull long partitionId, @NotNull long groupId) {
        this.name = name;
        this.partitionId = partitionId;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(long partitionId) {
        this.partitionId = partitionId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
