package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CourseRepository extends CrudRepository<Course, Long> {
    public void deleteByGroup(Group group);

    Set<Course> findAllByIdIn(Set<Long> groupIds);
}
