package backend.api.repositories;

import backend.api.entities.Group;
import backend.api.entities.User;
import backend.api.entities.UserGroupRole;
import backend.api.keys.UserGroupRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRoleRepository extends CrudRepository<UserGroupRole, UserGroupRoleKey> {
    void deleteByUser(User user);
    void deleteByGroup(Group group);
}
