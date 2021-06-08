package ru.hsHelper.api.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GroupUpdateRequest implements Serializable {
    @NotEmpty
    @NotNull
    private String name;

    public GroupUpdateRequest() {}

    public GroupUpdateRequest(@NotEmpty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
