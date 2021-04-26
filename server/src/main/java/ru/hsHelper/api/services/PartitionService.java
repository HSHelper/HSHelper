package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.requests.update.PartitionUpdateRequest;

import java.util.Map;
import java.util.Set;

public interface PartitionService {
    Partition createPartition(PartitionCreateRequest partitionCreateRequest);
    Partition getPartitionById(long id);
    Partition updatePartition(long id, PartitionUpdateRequest partitionUpdateRequest);
    void deletePartition(long id);
    void preDeletePartition(Partition partition);
    Partition addUsers(long partitionId, Set<Long> userIds, Map<Long, Integer> userParts);
    Partition deleteUsers(long partitionId, Set<Long> userIds);
    Set<Partition> getAll();
}
