package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Notification;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.requests.update.UserWorkUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface UserService {
    User createUser(User user);
    void deleteUser(long userId);
    User updateUser(long userId, UserUpdateRequest updateRequest);
    User getUserById(long userId);
    User addGroups(long userId, Set<Long> groupIds, Map<Long, Set<Long>> roleIds);
    User deleteGroups(long userId, Set<Long> groupsIds);
    User addToPartitions(long userId, Set<Long> partitionIds, Map<Long, Integer> userParts);
    User deletePartitions(long userId, Set<Long> partitionIds);
    User addCourses(long userId, Set<Long> courseIds, Map<Long, Set<Long>> roleIds);
    User deleteCourses(long userId, Set<Long> courseIds);
    User addCourseParts(long userId, Set<Long> coursePartIds, Map<Long, Set<Long>> roleIds);
    User deleteCourseParts(long userId, Set<Long> coursePartIds);
    User addWorks(long userId, Set<Long> workIds, Map<Long, String> solutions);
    User deleteWorks(long userId, Set<Long> workIds);
    Set<User> getAllUsers();
    User getUserByEmail(String email);
    UserGroupRole getUserGroupRole(long userId, long groupId);
    Set<UserGroupRole> getAllUserGroupRoles(long userId);
    UserToPartition getUserToPartition(long userId, long partitionId);
    Set<UserToPartition> getAllUserToPartitions(long userId);
    UserCourseRole getUserCourseRole(long userId, long courseId);
    Set<UserCourseRole> getAllUserCourseRoles(long userId);
    UserCoursePartRole getUserCoursePartRole(long userId, long coursePartId);
    Set<UserCoursePartRole> getAllUserCoursePartRoles(long userId);
    UserWork getUserWork(long userId, long workId);
    Set<UserWork> getAllUserWorks(long userId);
    UserWork updateUserWork(long userId, long workId, UserWorkUpdateRequest userWorkUpdateRequest);
    User addNotifications(long userId, Set<Long> notificationsIds);
    User deleteNotifications(long userId, Set<Long> notificationIds);
    Set<Notification> getAllNotifications(long userId);
    Set<UserWork> getAllUserWorksByGroup(long userId, long groupId);
    Set<UserWork> getAllUserWorksByCourse(long userId, long courseId);
    Set<UserWork> getAllUserWorksByCoursePart(long userId, long coursePartId);
}
