package backend.api.repositories;

import backend.api.entities.UserWork;
import backend.api.keys.UserWorkKey;
import org.springframework.data.repository.CrudRepository;

public interface UserWorkRepository extends CrudRepository<UserWork, UserWorkKey> {
}
