package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
                           UserGroupRoleRepository userGroupRoleRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        User user = getUserById(id);
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findAllByUser(user);
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.removeUserGroupAndRoles();
        }
        userGroupRoleRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public User updateUser(long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No user with such id"));
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setEmail(updateRequest.getEmail());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("No user with such id"));
    }

    @Transactional
    @Override
    public User addGroups(long userId, Set<Long> groupIds, Map<Long, Set<Long>> roleIds) {
        User user = getUserById(userId);
        Set<Group> groups = groupRepository.findAllByIdIn(groupIds);
        for (Group group : groups) {
            Set<Role> roles = roleRepository.findAllByIdIn(roleIds.get(group.getId()));
            UserGroupRole userGroupRole = userGroupRoleRepository.save(new UserGroupRole(user, group, roles));
            for (Role role : roles) {
                role.addUserGroupRole(userGroupRole);
            }
            group.addUserGroupRole(userGroupRole);
            user.addUserGroupRole(userGroupRole);
        }
        return user;
    }

    @Transactional
    @Override
    public User deleteGroups(long userId, Set<Long> groupIds) {
        User user = getUserById(userId);
        Set<Group> groups = groupRepository.findAllByIdIn(groupIds);
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findAllByUserAndGroupIn(user, groups);
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.removeUserGroupAndRoles();
        }
        userGroupRoleRepository.deleteAllByUserAndGroupIn(user, groups);
        return user;
    }
}
