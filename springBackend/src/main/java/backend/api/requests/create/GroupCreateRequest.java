package backend.api.requests.create;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class GroupCreateRequest implements Serializable {
    @NotEmpty
    private String name;

    public GroupCreateRequest(@NotEmpty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
