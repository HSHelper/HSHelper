package backend.api.repositories;

import backend.api.entities.Work;
import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Long> {
}
