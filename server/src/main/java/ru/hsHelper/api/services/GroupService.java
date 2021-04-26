package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.requests.update.GroupUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(long id);
    Group updateGroup(long id, GroupUpdateRequest groupUpdateRequest);
    void deleteGroup(long id);
    Group addUsers(long groupId, Set<Long> userIds, Map<Long, Set<Long>> roleIds);
    Group deleteUsers(long groupId, Set<Long> userIds);
    Set<Group> getAll();
}
