package ru.hsHelper.api.services.impl.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.UserToPartition;
import ru.hsHelper.api.keys.UserToPartitionKey;
import ru.hsHelper.api.repositories.UserToPartitionRepository;

@Service
public class UserPartitionService {
    private final UserToPartitionRepository userToPartitionRepository;

    public UserPartitionService(UserToPartitionRepository userToPartitionRepository) {
        this.userToPartitionRepository = userToPartitionRepository;
    }

    @Transactional(readOnly = true)
    public UserToPartition getUserToPartition(long userId, long partitionId) {
        return userToPartitionRepository.findById(new UserToPartitionKey(userId, partitionId)).orElseThrow(
                () -> new IllegalArgumentException("There is not such user in this partition")
        );
    }
}
