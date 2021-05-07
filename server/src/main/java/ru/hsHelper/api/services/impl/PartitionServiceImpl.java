package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.UserToPartitionRepository;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.requests.update.PartitionUpdateRequest;
import ru.hsHelper.api.services.CoursePartService;
import ru.hsHelper.api.services.CourseService;
import ru.hsHelper.api.services.PartitionService;
import ru.hsHelper.api.services.impl.util.UserPartitionService;

import java.util.Map;
import java.util.Set;

@Service
public class PartitionServiceImpl implements PartitionService {

    private final PartitionRepository partitionRepository;
    private final GroupRepository groupRepository;
    private final UserToPartitionRepository userToPartitionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final CoursePartRepository coursePartRepository;
    private final CoursePartService coursePartService;
    private final UserPartitionService userPartitionService;

    @Autowired
    public PartitionServiceImpl(PartitionRepository partitionRepository, GroupRepository groupRepository,
                                UserToPartitionRepository userToPartitionRepository, UserRepository userRepository,
                                CourseRepository courseRepository, CourseService courseService,
                                CoursePartRepository coursePartRepository, CoursePartService coursePartService,
                                UserPartitionService userPartitionService) {
        this.partitionRepository = partitionRepository;
        this.groupRepository = groupRepository;
        this.userToPartitionRepository = userToPartitionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.coursePartRepository = coursePartRepository;
        this.coursePartService = coursePartService;
        this.userPartitionService = userPartitionService;
    }

    @Transactional
    @Override
    public Partition createPartition(PartitionCreateRequest partitionCreateRequest) {
        Group group = groupRepository.findById(partitionCreateRequest.getGroupId()).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        Partition partition = partitionRepository.save(new Partition(partitionCreateRequest.getName(), group));
        group.addPartition(partition);
        return partition;
    }

    @Transactional
    @Override
    public Partition getPartitionById(long id) {
        return partitionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
    }

    @Transactional
    @Override
    public Partition updatePartition(long id, PartitionUpdateRequest partitionUpdateRequest) {
        Partition partition = partitionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
        partition.setName(partitionUpdateRequest.getName());
        return partition;
    }

    @Transactional
    @Override
    public void deletePartition(long id) {
        Partition partition = partitionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
        preDeletePartition(partition);
        partitionRepository.delete(partition);
    }

    @Transactional
    @Override
    public void preDeletePartition(Partition partition) {
        partition.getGroup().removePartition(partition);
        Set<UserToPartition> userToPartitions = userToPartitionRepository.findAllByPartition(partition);
        for (UserToPartition userToPartition : userToPartitions) {
            userToPartition.removePartitionAndUser();
        }
        userToPartitionRepository.deleteAll(userToPartitions);
        Set<Course> courses = courseRepository.findAllByDefaultPartition(partition);
        for (Course course : courses) {
            courseService.preDeleteCourse(course);
        }
        courseRepository.deleteAll(courses);
        Set<CoursePart> courseParts = coursePartRepository.findAllByPartition(partition);
        for (CoursePart coursePart : courseParts) {
            coursePartService.preDeleteCoursePart(coursePart);
        }
        coursePartRepository.deleteAll(courseParts);
    }

    @Transactional
    @Override
    public Partition addUsers(long partitionId, Set<Long> userIds, Map<Long, Integer> userParts) {
        Partition partition = getPartitionById(partitionId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        for (User user : users) {
            UserToPartition userToPartition = userToPartitionRepository.save(
                    new UserToPartition(user, partition, userParts.get(user.getId())));
            partition.addUser(userToPartition);
            user.addPartition(userToPartition);
        }
        return partition;
    }

    @Transactional
    @Override
    public Partition deleteUsers(long partitionId, Set<Long> userIds) {
        Partition partition = getPartitionById(partitionId);
        Set<User> users = userRepository.findAllByIdIn(userIds);
        Set<UserToPartition> userToPartitions = userToPartitionRepository.findAllByPartitionAndUserIn(partition, users);
        for (UserToPartition userToPartition : userToPartitions) {
            userToPartition.removePartitionAndUser();
        }
        userToPartitionRepository.deleteAll(userToPartitions);
        return partition;
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Partition> getAll() {
        return partitionRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public UserToPartition getUser(long partitionId, long userId) {
        return userPartitionService.getUserToPartition(userId, partitionId);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<UserToPartition> getAllUsers(long partitionId) {
        return userToPartitionRepository.findAllByPartition(getPartitionById(partitionId));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Course> getCoursesWithSuchDefaultPartition(long partitionId) {
        return courseRepository.findAllByDefaultPartition(getPartitionById(partitionId));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<CoursePart> getCourseParts(long partitionId) {
        return coursePartRepository.findAllByPartition(getPartitionById(partitionId));
    }
}
