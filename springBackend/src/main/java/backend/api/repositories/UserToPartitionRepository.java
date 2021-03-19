package backend.api.repositories;

import backend.api.entities.UserToPartition;
import backend.api.keys.UserToPartitionKey;
import org.springframework.data.repository.CrudRepository;

public interface UserToPartitionRepository extends CrudRepository<UserToPartition, UserToPartitionKey> {
}
