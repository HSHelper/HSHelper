package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.keys.UserGroupRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRoleRepository extends CrudRepository<UserGroupRole, UserGroupRoleKey> {
    void deleteByUser(User user);
    void deleteByGroup(Group group);
}
