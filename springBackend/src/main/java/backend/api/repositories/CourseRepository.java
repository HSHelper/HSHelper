package backend.api.repositories;

import backend.api.entities.Course;
import backend.api.entities.Group;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    public void deleteByGroup(Group group);
}
