package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Partition;
import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.keys.UserToPartitionKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserToPartitionRepository extends CrudRepository<UserToPartition, UserToPartitionKey> {
    void deleteByUser(User user);
    Set<UserToPartition> findAllByUserAndPartitionIn(User user, Set<Partition> partitionSet);
    void deleteAllByUserAndPartitionIn(User user, Set<Partition> partitionSet);
    Set<UserToPartition> findAllByUser(User user);
    void deleteAllByUser(User user);

    Set<UserToPartition> findAllByPartitionAndUserIn(Partition partition, Set<User> users);

    Set<UserToPartition> findAllByPartition(Partition partition);
}
