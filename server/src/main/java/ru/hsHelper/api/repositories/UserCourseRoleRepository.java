package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.keys.UserCourseRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserCourseRoleRepository extends CrudRepository<UserCourseRole, UserCourseRoleKey> {
    void deleteByUser(User user);
}
