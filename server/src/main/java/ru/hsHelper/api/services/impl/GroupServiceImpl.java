package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.requests.update.GroupUpdateRequest;
import ru.hsHelper.api.services.GroupService;
import ru.hsHelper.api.services.impl.util.UserGroupService;

import javax.servlet.http.Part;
import java.util.Map;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final RoleRepository roleRepository;
    private final UserGroupService userGroupService;
    private final PartitionRepository partitionRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository,
                            UserGroupRoleRepository userGroupRoleRepository, RoleRepository roleRepository,
                            UserGroupService userGroupService, PartitionRepository partitionRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.roleRepository = roleRepository;
        this.userGroupService = userGroupService;
        this.partitionRepository = partitionRepository;
    }

    @Transactional
    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    @Override
    public Group getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
    }

    @Transactional
    @Override
    public Group updateGroup(long id, GroupUpdateRequest groupUpdateRequest) {
        Group group = groupRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        group.setName(groupUpdateRequest.getName());
        return group;
    }

    @Transactional
    @Override
    public void deleteGroup(long id) {
        Group group = getGroupById(id);
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findAllByGroup(group);
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.removeUserGroupAndRoles();
        }
        userGroupRoleRepository.deleteAll(userGroupRoles);
        groupRepository.delete(group);
    }

    @Transactional
    @Override
    public Group addUsers(long groupId, Set<Long> userIds, Map<Long, Set<Long>> roleIds) {
        Group group = getGroupById(groupId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        for (User user : users) {
            userGroupService.createUserGroupRole(user, group, roleIds.get(user.getId()));
        }
        return group;
    }

    @Transactional
    @Override
    public Group deleteUsers(long groupId, Set<Long> userIds) {
        Group group = getGroupById(groupId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findAllByGroupAndUserIn(group, users);
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.removeUserGroupAndRoles();
        }
        userGroupRoleRepository.deleteAllByGroupAndUserIn(group, users);
        return group;
    }

    @Transactional
    @Override
    public Group addPartitions(long groupId, Set<Long> partitionIds) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        Set<Partition> partitions = partitionRepository.findAllByGroupAndIdIn(group, partitionIds);
        for (Partition partition : partitions) {
            partition.getGroup().removePartition(partition);
            group.addPartition(partition);
            partition.setGroup(group);
        }
        return group;
    }

    @Transactional
    @Override
    public Group deletePartitions(long groupId, Set<Long> partitionIds, Map<Long, Long> groupIds) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        Set<Partition> partitions = partitionRepository.findAllByGroupAndIdIn(group, partitionIds);
        for (Partition partition : partitions) {
            Group newGroup = groupRepository.findById(groupIds.get(partition.getId())).orElseThrow(
                    () -> new IllegalArgumentException("No group with such id")
            );
            group.removePartition(partition);
            newGroup.addPartition(partition);
            partition.setGroup(newGroup);
        }
        return group;
    }
}
