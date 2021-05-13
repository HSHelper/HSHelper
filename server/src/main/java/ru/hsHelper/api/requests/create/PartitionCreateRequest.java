package ru.hsHelper.api.requests.create;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PartitionCreateRequest implements Serializable {
    @NotEmpty
    private String name;

    @NotNull
    private long groupId;

    public PartitionCreateRequest(@NotEmpty String name, @NotNull long groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
