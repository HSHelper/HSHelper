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
}
