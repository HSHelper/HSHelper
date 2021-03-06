package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.requests.update.GroupUpdateRequest;
import ru.hsHelper.api.services.CourseService;
import ru.hsHelper.api.services.GroupService;
import ru.hsHelper.api.services.PartitionService;
import ru.hsHelper.api.services.impl.util.UserGroupService;

import java.util.Map;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final UserGroupService userGroupService;
    private final PartitionRepository partitionRepository;
    private final PartitionService partitionService;
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository,
                            UserGroupRoleRepository userGroupRoleRepository, RoleRepository roleRepository,
                            UserGroupService userGroupService, PartitionRepository partitionRepository,
                            PartitionService partitionService, CourseService courseService,
                            CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.userGroupService = userGroupService;
        this.partitionRepository = partitionRepository;
        this.partitionService = partitionService;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    @Override
    public Group getGroupById(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
    }

    @Transactional
    @Override
    public Group updateGroup(long groupId, GroupUpdateRequest groupUpdateRequest) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        group.setName(groupUpdateRequest.getName());
        return group;
    }

    @Transactional
    @Override
    public void deleteGroup(long groupId) {
        Group group = getGroupById(groupId);
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findAllByGroup(group);
        for (UserGroupRole userGroupRole : userGroupRoles) {
            userGroupRole.removeUserGroupAndRoles();
        }
        userGroupRoleRepository.deleteAll(userGroupRoles);
        Set<Partition> partitions = partitionRepository.findAllByGroup(group);
        for (Partition partition : partitions) {
            partitionService.preDeletePartition(partition);
        }
        partitionRepository.deleteAll(partitions);
        Set<Course> courses = courseRepository.findAllByGroup(group);
        for (Course course : courses) {
            courseService.preDeleteCourse(course);
        }
        courseRepository.deleteAll(courses);
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

    @Transactional(readOnly = true)
    @Override
    public Set<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserGroupRole getUserGroupRole(long groupId, long userId) {
        return userGroupService.getUserGroupRole(userId, groupId);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserGroupRole> getAllUserGroupRoles(long groupId) {
        return userGroupRoleRepository.findAllByGroup(getGroupById(groupId));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Partition> getAllPartitions(long groupId) {
        return partitionRepository.findAllByGroup(getGroupById(groupId));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Course> getAllCourses(long groupId) {
        return courseRepository.findAllByGroup(getGroupById(groupId));
    }
}
