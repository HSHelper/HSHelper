package ru.hsHelper.api.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CourseUpdateRequest implements Serializable {
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private long partitionId;

    public CourseUpdateRequest() {
    }

    public CourseUpdateRequest(@NotEmpty String name, @NotNull long partitionId) {
        this.name = name;
        this.partitionId = partitionId;
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
}
