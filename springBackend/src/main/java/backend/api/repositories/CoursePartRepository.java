package backend.api.repositories;

import backend.api.entities.CoursePart;
import org.springframework.data.repository.CrudRepository;

public interface CoursePartRepository extends CrudRepository<CoursePart, Long> {
}
