package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PartitionRepository extends CrudRepository<Partition, Long> {
    void deleteByGroup(Group group);
    Set<Partition> findAllByIdIn(Set<Long> partitionIds);

    Set<Partition> findAllByGroupAndIdIn(Group group, Set<Long> partitionIds);

    Set<Partition> findAllByGroup(Group group);

    Set<Partition> findAll();
}
