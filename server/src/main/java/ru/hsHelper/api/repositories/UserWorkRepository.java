package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.keys.UserWorkKey;
import org.springframework.data.repository.CrudRepository;

public interface UserWorkRepository extends CrudRepository<UserWork, UserWorkKey> {
    void deleteByUser(User user);
}
