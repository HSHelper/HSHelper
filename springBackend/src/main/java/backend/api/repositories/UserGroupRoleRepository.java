package backend.api.repositories;

import backend.api.entities.UserGroupRole;
import backend.api.keys.UserGroupRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRoleRepository extends CrudRepository<UserGroupRole, UserGroupRoleKey> {
}
