package backend.api.repositories;

import backend.api.entities.UserCoursePartRole;
import backend.api.entities.UserCourseRole;
import backend.api.keys.UserCourseRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserCourseRoleRepository extends CrudRepository<UserCourseRole, UserCourseRoleKey> {
}
