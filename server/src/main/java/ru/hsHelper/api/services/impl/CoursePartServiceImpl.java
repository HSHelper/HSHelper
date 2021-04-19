package ru.hsHelper.api.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.repositories.CoursePartRepository;
import ru.hsHelper.api.repositories.CourseRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.requests.create.CoursePartCreateRequest;
import ru.hsHelper.api.services.CoursePartService;

@Service
public class CoursePartServiceImpl implements CoursePartService {

    private final CoursePartRepository coursePartRepository;
    private final PartitionRepository partitionRepository;
    private final CourseRepository courseRepository;

    public CoursePartServiceImpl(CoursePartRepository coursePartRepository, PartitionRepository partitionRepository,
                                 CourseRepository courseRepository) {
        this.coursePartRepository = coursePartRepository;
        this.partitionRepository = partitionRepository;
        this.courseRepository = courseRepository;
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
}
