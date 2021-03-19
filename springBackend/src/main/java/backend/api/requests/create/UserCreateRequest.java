package backend.api.requests.create;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class UserCreateRequest implements Serializable {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public UserCreateRequest(@NotEmpty String firstName, @NotEmpty String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
