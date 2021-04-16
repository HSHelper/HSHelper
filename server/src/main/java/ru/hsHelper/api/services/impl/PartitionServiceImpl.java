package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Group;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.repositories.GroupRepository;
import ru.hsHelper.api.repositories.PartitionRepository;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.services.PartitionService;

@Service
public class PartitionServiceImpl implements PartitionService {

    private final PartitionRepository partitionRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public PartitionServiceImpl(PartitionRepository partitionRepository, GroupRepository groupRepository) {
        this.partitionRepository = partitionRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    @Override
    public Partition createPartition(PartitionCreateRequest partitionCreateRequest) {
        Group group = groupRepository.findById(partitionCreateRequest.getGroupId()).orElseThrow(
                () -> new IllegalArgumentException("No group with such id")
        );
        Partition partition = partitionRepository.save(new Partition(partitionCreateRequest.getName(), group));
        group.addPartition(partition);
        return partition;
    }

    @Transactional
    @Override
    public Partition getPartitionById(long id) {
        return partitionRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No partition with such id")
        );
    }
}
