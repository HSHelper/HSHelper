package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.keys.UserCourseRoleKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserCourseRoleRepository extends CrudRepository<UserCourseRole, UserCourseRoleKey> {
    void deleteByUser(User user);

    Set<UserCourseRole> findAllByUserAndCourseIn(User user, Set<Course> courses);

    void deleteAllByUserAndCourseIn(User user, Set<Course> courses);

    Set<UserCourseRole> findAllByUser(User user);

    void deleteAllByUser(User user);
}
