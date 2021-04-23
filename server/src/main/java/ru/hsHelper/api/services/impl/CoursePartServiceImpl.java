package ru.hsHelper.api.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserCoursePartRole;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.repositories.UserCoursePartRoleRepository;
import ru.hsHelper.api.repositories.UserRepository;
import ru.hsHelper.api.repositories.WorkRepository;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.services.CoursePartService;
import ru.hsHelper.api.services.WorkService;

import java.util.Set;

@Service
public class CoursePartServiceImpl implements CoursePartService {

    private final CoursePartRepository coursePartRepository;
    private final PartitionRepository partitionRepository;
    private final CourseRepository courseRepository;
    private final UserCoursePartRoleRepository userCoursePartRoleRepository;
    private final UserRepository userRepository;
    private final WorkService workService;
    private final WorkRepository workRepository;

    public CoursePartServiceImpl(CoursePartRepository coursePartRepository, PartitionRepository partitionRepository,
                                 CourseRepository courseRepository, UserRepository userRepository,
                                 UserCoursePartRoleRepository userCoursePartRoleRepository,
                                 WorkService workService, WorkRepository workRepository) {
        this.coursePartRepository = coursePartRepository;
        this.partitionRepository = partitionRepository;
        this.courseRepository = courseRepository;
        this.userCoursePartRoleRepository = userCoursePartRoleRepository;
        this.userRepository = userRepository;
        this.workService = workService;
        this.workRepository = workRepository;
    }

    @Transactional
    @Override
    public CoursePart createCoursePart(CoursePartCreateRequest coursePartCreateRequest) {
        Partition partition = partitionRepository.findById(coursePartCreateRequest.getPartitionId()).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
        Course course = courseRepository.findById(coursePartCreateRequest.getCourseId()).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
        CoursePart coursePart = coursePartRepository.save(new CoursePart(coursePartCreateRequest.getName(), partition,
                course, coursePartCreateRequest.getWeight(), coursePartCreateRequest.getBlock()));
        partition.addCoursePart(coursePart);
        course.addCoursePart(coursePart);
        return coursePart;
    }

    @Transactional(readOnly = true)
    @Override
    public CoursePart getCoursePartById(long id) {
        return coursePartRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No course with such id")
        );
    }

    @Transactional
    @Override
    public void deleteCoursePart(long id) {
        CoursePart coursePart = coursePartRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No coursePart with such id")
        );
        preDeleteCoursePart(coursePart);
        coursePartRepository.delete(coursePart);
    }

    @Transactional
    @Override
    public void preDeleteCoursePart(CoursePart coursePart) {
        coursePart.getCourse().removeCoursePart(coursePart);
        coursePart.getPartition().removeCoursePart(coursePart);
        Set<UserCoursePartRole> userCoursePartRoles = userCoursePartRoleRepository.findAllByCoursePart(coursePart);
        for (UserCoursePartRole userCoursePartRole : userCoursePartRoles) {
            userCoursePartRole.removeUserCoursePartAndRoles();
        }
        userCoursePartRoleRepository.deleteAll(userCoursePartRoles);
        Set<Work> works = workRepository.findAllByCoursePart(coursePart);
        for (Work work : works) {
            workService.preDeleteWork(work);
        }
        workRepository.deleteAll(works);
    }
}
