package backend.api.repositories;

import backend.api.entities.UserCoursePartRole;
import backend.api.keys.UserCoursePartRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserCoursePartRoleRepository extends CrudRepository<UserCoursePartRole, UserCoursePartRoleKey> {
}
