package ru.hsHelper.api.requests.add;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.Map;

public class ObjectsWithSolutionsAddRequest implements Serializable {
    @NotNull
    Set<Long> objectsIds;

    @NotNull
    Map<Long, String> solutions;

    public ObjectsWithSolutionsAddRequest() {
    }

    public ObjectsWithSolutionsAddRequest(@NotNull Set<Long> objectsIds, @NotNull Map<Long, String> solutions) {
        this.objectsIds = objectsIds;
        this.solutions = solutions;
    }

    public Set<Long> getObjectsIds() {
        return objectsIds;
    }

    public void setObjectsIds(Set<Long> objectsIds) {
        this.objectsIds = objectsIds;
    }

    public Map<Long, String> getSolutions() {
        return solutions;
    }

    public void setSolutions(Map<Long, String> solutions) {
        this.solutions = solutions;
    }
}
