package ru.hsHelper.api.requests.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CoursePartCreateRequest implements Serializable {
    @NotEmpty
    @NotNull
    private String name;
    
    @NotEmpty
    @NotNull
    private String gSheetId;

    @NotEmpty
    @NotNull
    private String gSheetPage;

    @NotNull
    private long partitionId;

    @NotNull
    private  long courseId;

    @NotNull
    @Min(value = 0L, message = "weight should be positive")
    private double weight;

    @NotNull
    private double block;

    public CoursePartCreateRequest() {
    }

    public CoursePartCreateRequest(@NotEmpty String name, @NotEmpty String gSheetId, @NotEmpty String gSheetPage, @NotNull long partitionId,
                                   @NotNull long courseId,
                                   @NotNull @Min(value = 0L, message = "weight should be positive") double weight,
                                   @NotNull double block) {
        this.name = name;
        this.gSheetId = gSheetId;
        this.gSheetPage = gSheetPage;
        this.partitionId = partitionId;
        this.courseId = courseId;
        this.weight = weight;
        this.block = block;
    }

    public String getGSheetId() {
        return gSheetId;
    }

    public void setGSheetId(String gSheetId) {
        this.gSheetId = gSheetId;
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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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


    public String getGSheetPage() {
        return gSheetPage;
    }

    public void setGSheetPage(String gSheetPage) {
        this.gSheetPage = gSheetPage;
    }
}
