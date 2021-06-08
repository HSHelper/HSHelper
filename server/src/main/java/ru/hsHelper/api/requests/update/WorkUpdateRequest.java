package ru.hsHelper.api.requests.update;

import ru.hsHelper.api.entities.Work;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class WorkUpdateRequest implements Serializable {
    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String description;

    @NotNull
    private Date date;

    @Min(value = 0L, message = "weight should be positive")
    @Max(value = 1L, message = "weight should be in range [0, 1]")
    private double weight;

    private double block;

    public WorkUpdateRequest() {
    }

    public WorkUpdateRequest(@NotEmpty String name, @NotEmpty String description, @NotNull Date date,
                             @Min(value = 0L, message = "weight should be positive")
                             @Max(value = 1L, message = "weight should be in range [0, 1]") double weight,
                             double block) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.weight = weight;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBlock() {
        return block;
    }

    public void setBlock(double block) {
        this.block = block;
    }
}
