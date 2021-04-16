package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.requests.create.CourseCreateRequest;
import ru.hsHelper.api.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final PartitionRepository partitionRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, PartitionRepository partitionRepository, GroupRepository groupRepository) {
        this.courseRepository = courseRepository;
        this.partitionRepository = partitionRepository;
        this.groupRepository = groupRepository;
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
}
