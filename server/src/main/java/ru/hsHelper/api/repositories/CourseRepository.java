package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import org.springframework.data.repository.CrudRepository;
import ru.hsHelper.api.entities.Partition;

import java.util.Set;

public interface CourseRepository extends CrudRepository<Course, Long> {

    Set<Course> findAllByIdIn(Set<Long> groupIds);

    Set<Course> findAllByDefaultPartition(Partition partition);

    Set<Course> findAllByGroup(Group group);

    @NotNull
    Set<Course> findAll();
}
