package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PartitionRepository extends CrudRepository<Partition, Long> {
    Set<Partition> findAllByIdIn(Set<Long> partitionIds);

    Set<Partition> findAllByGroup(Group group);

    @NotNull
    Set<Partition> findAll();
}
