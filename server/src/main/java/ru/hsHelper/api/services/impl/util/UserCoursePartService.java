package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.keys.UserCoursePartRoleKey;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository;
import ru.hsHelper.api.repositories.UserCourseRoleRepository;

import java.util.Set;

@Service
public class UserCoursePartService {
    private final RoleRepository roleRepository;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;

    @Autowired
    public UserCoursePartService(RoleRepository roleRepository,
                                 UserCoursePartRoleRepository userCoursePartRoleRepository) {
        this.roleRepository = roleRepository;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
    }

    @Transactional
    public void createUserCoursePartRole(User user, CoursePart coursePart, Set<Long> roleIds) {
        Set<Role> roles = roleRepository.findAllByIdIn(roleIds);
        UserCoursePartRole userCoursePartRole = userCoursePartRoleRepository.save(new UserCoursePartRole(user, coursePart, roles));
        for (Role role : roles) {
            role.addUserCoursePartRole(userCoursePartRole);
        }
        coursePart.addUser(userCoursePartRole);
        user.addCoursePart(userCoursePartRole);
    }

    @Transactional(readOnly = true)
    public UserCoursePartRole getUserCoursePartRole(long userId, long coursePartId) {
        return userCoursePartRoleRepository.findById(new UserCoursePartRoleKey(userId, coursePartId)).orElseThrow(
                () -> new IllegalArgumentException("There is not such user in this course-part")
        );
    }
}
