package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Course;
import ru.hsHelper.api.entities.CoursePart;
import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.requests.create.PartitionCreateRequest;
import ru.hsHelper.api.requests.update.PartitionUpdateRequest;

import java.util.Map;
import java.util.Set;

public interface PartitionService {
    Partition createPartition(PartitionCreateRequest partitionCreateRequest);
    Partition getPartitionById(long partitionId);
    Partition updatePartition(long partitionId, PartitionUpdateRequest partitionUpdateRequest);
    void deletePartition(long partitionId);
    void preDeletePartition(Partition partition);
    Partition addUsers(long partitionId, Set<Long> userIds, Map<Long, Integer> userParts);
    Partition deleteUsers(long partitionId, Set<Long> userIds);
    Set<Partition> getAllPartitions();
    UserToPartition getUserToPartition(long partitionId, long userId);
    Set<UserToPartition> getAllUserToPartitions(long partitionId);
    Set<Course> getCoursesWithSuchDefaultPartition(long partitionId);
    Set<CoursePart> getCourseParts(long partitionId);
}
