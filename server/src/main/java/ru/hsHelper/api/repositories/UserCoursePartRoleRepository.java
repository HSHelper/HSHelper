package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.keys.UserCoursePartRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserCoursePartRoleRepository extends CrudRepository<UserCoursePartRole, UserCoursePartRoleKey> {
    void deleteByUser(User user);
}
