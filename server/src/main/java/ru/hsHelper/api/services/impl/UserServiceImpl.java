package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.entities.UserGroupRole;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository;
import ru.hsHelper.api.repositories.UserCourseRoleRepository;
import ru.hsHelper.api.repositories.UserGroupRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserToPartitionRepository;
import ru.hsHelper.api.repositories.UserWorkRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.update.UserUpdateRequest;
import ru.hsHelper.api.services.UserService;
import ru.hsHelper.api.services.impl.util.UserCoursePartService;
import ru.hsHelper.api.services.impl.util.UserCourseService;
import ru.hsHelper.api.services.impl.util.UserGroupService;

import java.util.Date;
import java.util.Set;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRoleRepository userGroupRoleRepository;
    private final PartitionRepository partitionRepository;
    private final UserToPartitionRepository userToPartitionRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;
    private final CoursePartRepository coursePartRepository;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;
    private final WorkRepository workRepository;
    private final UserWorkRepository userWorkRepository;
    private final UserGroupService userGroupService;
    private final UserCourseService userCourseService;
    private final UserCoursePartService userCoursePartService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository,
                           UserGroupRoleRepository userGroupRoleRepository,
                           PartitionRepository partitionRepository,
                           UserToPartitionRepository userToPartitionRepository,
                           CourseRepository courseRepository, UserCourseRoleRepository userCourseRoleRepository,
                           CoursePartRepository coursePartRepository,
                           UserCoursePartRoleRepository userCoursePartRoleRepository, WorkRepository workRepository,
                           UserWorkRepository userWorkRepository, UserGroupService userGroupService,
                           UserCourseService userCourseService, UserCoursePartService userCoursePartService) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRoleRepository = userGroupRoleRepository;
        this.userCoursePartService = userCoursePartService;
        this.partitionRepository = partitionRepository;
        this.userToPartitionRepository = userToPartitionRepository;
        this.courseRepository = courseRepository;
        this.userCourseRoleRepository = userCourseRoleRepository;
        this.coursePartRepository = coursePartRepository;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
        this.workRepository = workRepository;
        this.userWorkRepository = userWorkRepository;
        this.userGroupService = userGroupService;
        this.userCourseService = userCourseService;
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
        Set<UserCourseRole> userCourseRoles = userCourseRoleRepository.findAllByUser(user);
        for (UserCourseRole userCourseRole : userCourseRoles) {
            userCourseRole.removeUserCourseAndRoles();
        }
        userCourseRoleRepository.deleteAllByUser(user);
        Set<UserCoursePartRole> userCoursePartRoles = userCoursePartRoleRepository.findAllByUser(user);
        for (UserCoursePartRole userCoursePartRole : userCoursePartRoles) {
            userCoursePartRole.removeUserCoursePartAndRoles();
        }
        userCoursePartRoleRepository.deleteAllByUser(user);
        Set<UserWork> userWorks = userWorkRepository.findAllByUser(user);
        for (UserWork userWork : userWorks) {
            userWork.removeUserAndWork();
        }
        userWorkRepository.deleteAll(userWorks);
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
            userGroupService.createUserGroupRole(user, group, roleIds.get(group.getId()));
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

    @Transactional
    @Override
    public User addCourses(long userId, Set<Long> courseIds, Map<Long, Set<Long>> roleIds) {
        User user = getUserById(userId);
        Set<Course> courses = courseRepository.findAllByIdIn(courseIds);
        for (Course course : courses) {
            userCourseService.createUserCourseRole(user, course, roleIds.get(course.getId()));
        }
        return user;
    }

    @Transactional
    @Override
    public User deleteCourses(long userId, Set<Long> courseIds) {
        User user = getUserById(userId);
        Set<Course> courses = courseRepository.findAllByIdIn(courseIds);
        Set<UserCourseRole> userCourseRoles = userCourseRoleRepository.findAllByUserAndCourseIn(user, courses);
        for (UserCourseRole userCourseRole : userCourseRoles) {
            userCourseRole.removeUserCourseAndRoles();
        }
        userCourseRoleRepository.deleteAllByUserAndCourseIn(user, courses);
        return user;
    }

    @Transactional
    @Override
    public User addCourseParts(long userId, Set<Long> coursePartIds, Map<Long, Set<Long>> roleIds) {
        User user = getUserById(userId);
        Set<CoursePart> courseParts = coursePartRepository.findAllByIdIn(coursePartIds);
        for (CoursePart coursePart : courseParts) {
            userCoursePartService.createUserCoursePartRole(user, coursePart, roleIds.get(coursePart.getId()));
        }
        return user;
    }

    @Transactional
    @Override
    public User deleteCourseParts(long userId, Set<Long> coursePartIds) {
        User user = getUserById(userId);
        Set<CoursePart> coursesParts = coursePartRepository.findAllByIdIn(coursePartIds);
        Set<UserCoursePartRole> userCoursePartRoles =
                userCoursePartRoleRepository.findAllByUserAndCoursePartIn(user, coursesParts);
        for (UserCoursePartRole userCoursePartRole : userCoursePartRoles) {
            userCoursePartRole.removeUserCoursePartAndRoles();
        }
        userCoursePartRoleRepository.deleteAllByUserAndCoursePartIn(user, coursesParts);
        return user;
    }

    @Transactional
    @Override
    public User addWorks(long userId, Set<Long> workIds, Map<Long, String> solutions) {
        User user = getUserById(userId);
        Set<Work> works = workRepository.findAllByIdIn(workIds);
        for (Work work : works) {
            UserWork userWork = userWorkRepository.save(new UserWork(user, work,
                    new Date(), solutions.get(work.getId()), 0));
            user.addWork(userWork);
            work.addUser(userWork);
        }
        return user;
    }

    @Transactional
    @Override
    public User deleteWorks(long userId, Set<Long> workIds) {
        User user = getUserById(userId);
        Set<Work> works = workRepository.findAllByIdIn(workIds);
        Set<UserWork> userWorks = userWorkRepository.findAllByUserAndWorkIn(user, works);
        for (UserWork userWork : userWorks) {
            userWork.removeUserAndWork();
        }
        userWorkRepository.deleteAll(userWorks);
        return user;
    }

    @Override
    public Set<User> getAll() {
        return userRepository.findAll();
    }
}
