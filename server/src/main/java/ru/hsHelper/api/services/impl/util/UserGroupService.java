package ru.hsHelper.api.services.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.keys.UserGroupRoleKey;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.services.GroupService;
import ru.hsHelper.api.services.UserService;

import java.util.Set;

@Service
public class UserGroupService {

    private final RoleRepository roleRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;

    @Autowired
    public UserGroupService(RoleRepository roleRepository, UserGroupRoleRepository userGroupRoleRepository) {
        this.roleRepository = roleRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
    }

    @Transactional
    public void createUserGroupRole(User user, Group group, Set<Long> roleIds) {
        Set<Role> roles = roleRepository.findAllByIdIn(roleIds);
        UserGroupRole userGroupRole = userGroupRoleRepository.save(new UserGroupRole(user, group, roles));
        for (Role role : roles) {
            role.addUserGroupRole(userGroupRole);
        }
        group.addUserGroupRole(userGroupRole);
        user.addUserGroupRole(userGroupRole);
    }

    @Transactional(readOnly = true)
    public UserGroupRole getUserGroupRole(long userId, long groupId) {
        return userGroupRoleRepository.findById(new UserGroupRoleKey(userId, groupId)).orElseThrow(
                () -> new IllegalArgumentException("There is not such user in this group")
        );
    }
}
