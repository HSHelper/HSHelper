package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import org.springframework.data.repository.CrudRepository;

public interface PartitionRepository extends CrudRepository<Partition, Long> {
    public void deleteByGroup(Group group);
}
