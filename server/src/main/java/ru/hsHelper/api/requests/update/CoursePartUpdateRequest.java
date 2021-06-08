package ru.hsHelper.api.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CoursePartUpdateRequest implements Serializable {
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private long partitionId;

    @NotNull
    @Min(value = 0L, message = "weight should be positive")
    private double weight;

    @NotNull
    private double block;

    public CoursePartUpdateRequest() {
    }

    public CoursePartUpdateRequest(@NotEmpty String name, @NotNull long partitionId,
                                   @NotNull @Min(value = 0L, message = "weight should be positive") double weight,
                                   @NotNull double block) {
        this.name = name;
        this.partitionId = partitionId;
        this.weight = weight;
        this.block = block;
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
