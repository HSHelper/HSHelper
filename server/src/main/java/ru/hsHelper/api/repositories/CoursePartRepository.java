package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import org.springframework.data.repository.CrudRepository;
import ru.hsHelper.api.entities.Partition;

import java.util.Set;

public interface CoursePartRepository extends CrudRepository<CoursePart, Long> {
    Set<CoursePart> findAllByIdIn(Set<Long> coursePartIds);

    Set<CoursePart> findAllByCourse(Course course);

    Set<CoursePart> findAllByPartition(Partition partition);

    Set<CoursePart> findAll();
}
