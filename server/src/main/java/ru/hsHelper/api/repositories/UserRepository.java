package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAllByIdIn(Set<Long> userIds);
}
