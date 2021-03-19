package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.keys.UserToPartitionKey;
import org.springframework.data.repository.CrudRepository;

public interface UserToPartitionRepository extends CrudRepository<UserToPartition, UserToPartitionKey> {
    void deleteByUser(User user);
}
