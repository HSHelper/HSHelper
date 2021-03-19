package backend.api.repositories;

import backend.api.entities.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
