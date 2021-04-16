package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.keys.UserGroupRoleKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserGroupRoleRepository extends CrudRepository<UserGroupRole, UserGroupRoleKey> {
    void deleteAllByUser(User user);
    void deleteAllByGroup(Group group);
    void deleteAllByUserAndGroup(User user, Group group);
    void deleteAllByUserAndGroupIn(User user, Set<Group> groupSet);
    Set<UserGroupRole> findAllByUserAndGroupIn(User user, Set<Group> groupSet);
    Set<UserGroupRole> findAllByUser(User user);
}
