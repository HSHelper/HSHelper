package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.requests.update.UserUpdateRequest;

import java.util.Set;
import java.util.Map;

public interface UserService {
    User createUser(User user);
    void deleteUser(long id);
    User updateUser(long id, UserUpdateRequest updateRequest);
    User getUserById(long id);
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
}
