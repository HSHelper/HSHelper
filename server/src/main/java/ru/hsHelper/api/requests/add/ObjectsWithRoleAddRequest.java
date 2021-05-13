package ru.hsHelper.api.requests.add;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class ObjectsWithRoleAddRequest implements Serializable {
    @NotNull
    private Set<Long> objectIds;

    @NotNull
    private Map<Long, Set<Long>> roleIds;

    public ObjectsWithRoleAddRequest() {
    }

    public ObjectsWithRoleAddRequest(@NotNull Set<Long> objectIds, @NotNull Map<Long, Set<Long>> roleIds) {
        this.objectIds = objectIds;
        this.roleIds = roleIds;
    }

    public Set<Long> getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(Set<Long> objectIds) {
        this.objectIds = objectIds;
    }

    public Map<Long, Set<Long>> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Map<Long, Set<Long>> roleIds) {
        this.roleIds = roleIds;
    }
}
