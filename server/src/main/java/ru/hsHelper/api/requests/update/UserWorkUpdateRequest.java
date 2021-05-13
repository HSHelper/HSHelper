package ru.hsHelper.api.requests.update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserWorkUpdateRequest implements Serializable {
    @NotNull
    @NotEmpty
    private String solution;

    private Double mark;

    public UserWorkUpdateRequest() {
    }

    public UserWorkUpdateRequest(@NotNull @NotEmpty String solution, Double mark) {
        this.solution = solution;
        this.mark = mark;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
