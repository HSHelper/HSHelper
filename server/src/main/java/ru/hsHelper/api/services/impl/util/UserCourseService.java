package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.keys.UserCourseRoleKey;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserCourseRoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;

import java.util.Set;

@Service
public class UserCourseService {
    private final RoleRepository roleRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;

    @Autowired
    public UserCourseService(RoleRepository roleRepository, UserCourseRoleRepository userCourseRoleRepository) {
        this.roleRepository = roleRepository;
        this.userCourseRoleRepository = userCourseRoleRepository;
    }

    @Transactional
    public void createUserCourseRole(User user, Course course, Set<Long> roleIds) {
        Set<Role> roles = roleRepository.findAllByIdIn(roleIds);
        UserCourseRole userCourseRole = userCourseRoleRepository.save(new UserCourseRole(user, course, roles));
        for (Role role : roles) {
            role.addUserCourseRole(userCourseRole);
        }
        course.addUser(userCourseRole);
        user.addCourse(userCourseRole);
    }

    @Transactional(readOnly = true)
    public UserCourseRole getUserCourseRole(long userId, long courseId) {
        return userCourseRoleRepository.findById(new UserCourseRoleKey(userId, courseId)).orElseThrow(
                () -> new IllegalArgumentException("There is not such user in this course")
        );
    }
}
