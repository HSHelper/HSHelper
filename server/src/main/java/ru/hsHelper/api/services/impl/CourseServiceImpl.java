package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserCourseRole;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.UserCourseRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.requests.create.CourseCreateRequest;
import ru.hsHelper.api.services.CoursePartService;
import ru.hsHelper.api.services.CourseService;

import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final PartitionRepository partitionRepository;
    private final GroupRepository groupRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;
    private final UserRepository userRepository;
    private final CoursePartService coursePartService;
    private final CoursePartRepository coursePartRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, PartitionRepository partitionRepository,
                             GroupRepository groupRepository, UserCourseRoleRepository userCourseRoleRepository,
                             UserRepository userRepository, CoursePartService coursePartService,
                             CoursePartRepository coursePartRepository) {
        this.courseRepository = courseRepository;
        this.partitionRepository = partitionRepository;
        this.groupRepository = groupRepository;
        this.userCourseRoleRepository = userCourseRoleRepository;
        this.userRepository = userRepository;
        this.coursePartService = coursePartService;
        this.coursePartRepository = coursePartRepository;
    }

    @Transactional
    @Override
    public Course createCourse(CourseCreateRequest courseCreateRequest) {
        Partition partition = partitionRepository.findById(courseCreateRequest.getPartitionId()).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
        Group group = groupRepository.findById(courseCreateRequest.getGroupId()).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        Course course = courseRepository.save(new Course(courseCreateRequest.getName(), partition, group));
        partition.addCourse(course);
        group.addCourse(course);
        return course;
    }

    @Transactional(readOnly = true)
    @Override
    public Course getCourseById(long id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No course with such id")
        );
    }

    @Override
    public void deleteCourse(long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No course with such id")
        );
        preDeleteCourse(course);
        courseRepository.delete(course);
    }

    @Override
    public void preDeleteCourse(Course course) {
        course.getDefaultPartition().removeCourse(course);
        course.getGroup().removeCourse(course);
        Set<UserCourseRole> userCourseRoles = userCourseRoleRepository.findAllByCourse(course);
        for (UserCourseRole userCourseRole : userCourseRoles) {
            userCourseRole.removeUserCourseAndRoles();
        }
        userCourseRoleRepository.deleteAll(userCourseRoles);
        Set<CoursePart> courseParts = coursePartRepository.findAllByCourse(course);
        for (CoursePart coursePart : courseParts) {
            coursePartService.preDeleteCoursePart(coursePart);
        }
        coursePartRepository.deleteAll(courseParts);
    }
}
