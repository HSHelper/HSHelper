package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.CoursePart;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CoursePartRepository extends CrudRepository<CoursePart, Long> {
    Set<CoursePart> findAllByIdIn(Set<Long> coursePartIds);
}
