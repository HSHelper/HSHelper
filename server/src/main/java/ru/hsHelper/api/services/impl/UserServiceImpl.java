package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserToPartitionRepository;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.services.UserService;

import java.util.Set;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final RoleRepository roleRepository;
    private final PartitionRepository partitionRepository;
    private final UserToPartitionRepository userToPartitionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
                           UserGroupRoleRepository userGroupRoleRepository, RoleRepository roleRepository,
                           PartitionRepository partitionRepository,
                           UserToPartitionRepository userToPartitionRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.roleRepository = roleRepository;
        this.partitionRepository = partitionRepository;
        this.userToPartitionRepository = userToPartitionRepository;
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
        Set<UserToPartition> userToPartitions = userToPartitionRepository.findAllByUser(user);
        for (UserToPartition userToPartition : userToPartitions) {
            userToPartition.removePartitionAndUser();
        }
        userToPartitionRepository.deleteAllByUser(user);
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

    @Transactional
    @Override
    public User addToPartitions(long userId, Set<Long> partitionIds, Map<Long, Integer> userParts) {
        User user = getUserById(userId);
        Set<Partition> partitions = partitionRepository.findAllByIdIn(partitionIds);
        for (Partition partition : partitions) {
            UserToPartition userToPartition = userToPartitionRepository.save(
                    new UserToPartition(user, partition, userParts.get(partition.getId())));
            partition.addUser(userToPartition);
            user.addPartition(userToPartition);
        }
        return user;
    }

    @Transactional
    @Override
    public User deletePartitions(long userId, Set<Long> partitionIds) {
        User user = getUserById(userId);
        Set<Partition> partitions = partitionRepository.findAllByIdIn(partitionIds);
        Set<UserToPartition> userToPartitions = userToPartitionRepository.findAllByUserAndPartitionIn(user, partitions);
        for (UserToPartition userToPartition : userToPartitions) {
            userToPartition.removePartitionAndUser();
        }
        userToPartitionRepository.deleteAllByUserAndPartitionIn(user, partitions);
        return user;
    }
}
