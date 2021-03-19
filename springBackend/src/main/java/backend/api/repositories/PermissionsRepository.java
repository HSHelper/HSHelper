package backend.api.repositories;

import backend.api.entities.Permissions;
import org.springframework.data.repository.CrudRepository;

public interface PermissionsRepository extends CrudRepository<Permissions, Long> {
}
