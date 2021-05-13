package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.keys.UserCoursePartRoleKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserCoursePartRoleRepository extends CrudRepository<UserCoursePartRole, UserCoursePartRoleKey> {
    void deleteByUser(User user);

    Set<UserCoursePartRole> findAllByUserAndCoursePartIn(User user, Set<CoursePart> coursesParts);

    void deleteAllByUserAndCoursePartIn(User user, Set<CoursePart> coursesParts);

    Set<UserCoursePartRole> findAllByUser(User user);

    void deleteAllByUser(User user);

    Set<UserCoursePartRole> findAllByCoursePart(CoursePart coursePart);

    Set<UserCoursePartRole> findAllByCoursePartAndUserIn(CoursePart coursePart, Set<User> users);
}
