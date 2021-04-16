package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;

public interface PartitionService {
    Partition createPartition(PartitionCreateRequest partitionCreateRequest);
    Partition getPartitionById(long id);
}
