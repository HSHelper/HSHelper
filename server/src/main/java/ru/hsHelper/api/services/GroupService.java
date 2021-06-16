package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.requests.update.GroupUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface GroupService {
    Group createGroup(Group group);
    Group getGroupById(long groupId);
    Group updateGroup(long groupId, GroupUpdateRequest groupUpdateRequest);
    void deleteGroup(long groupId);
    Group addUsers(long groupId, Set<Long> userIds, Map<Long, Set<Long>> roleIds);
    Group deleteUsers(long groupId, Set<Long> userIds);
    Set<Group> getAllGroups();
    UserGroupRole getUserGroupRole(long groupId, long userId);
    Set<UserGroupRole> getAllUserGroupRoles(long groupId);
    Set<Partition> getAllPartitions(long groupId);
    Set<Course> getAllCourses(long groupId);
}
