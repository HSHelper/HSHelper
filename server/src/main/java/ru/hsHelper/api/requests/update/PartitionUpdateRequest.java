package ru.hsHelper.api.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PartitionUpdateRequest implements Serializable {
    @NotEmpty
    private String name;

    public PartitionUpdateRequest() {
    }

    public PartitionUpdateRequest(@NotEmpty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
