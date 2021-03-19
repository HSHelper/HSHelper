package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.CoursePart;
import org.springframework.data.repository.CrudRepository;

public interface CoursePartRepository extends CrudRepository<CoursePart, Long> {
}
