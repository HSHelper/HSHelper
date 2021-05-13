package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import org.springframework.data.repository.CrudRepository;
import ru.hsHelper.api.entities.Partition;

import java.util.Set;

public interface CourseRepository extends CrudRepository<Course, Long> {
    void deleteByGroup(Group group);

    Set<Course> findAllByIdIn(Set<Long> groupIds);

    Set<Course> findAllByDefaultPartition(Partition partition);

    Set<Course> findAllByGroup(Group group);

    Set<Course> findAll();
}
