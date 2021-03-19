package backend.api.repositories;

import backend.api.entities.Group;
import backend.api.entities.Partition;
import org.springframework.data.repository.CrudRepository;

public interface PartitionRepository extends CrudRepository<Partition, Long> {
    public void deleteByGroup(Group group);
}
