package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import ru.hsHelper.api.entities.UserWork;
import ru.hsHelper.api.entities.Work;
import ru.hsHelper.api.keys.UserWorkKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserWorkRepository extends CrudRepository<UserWork, UserWorkKey> {

    Set<UserWork> findAllByUser(User user);

    Set<UserWork> findAllByUserAndWorkIn(User user, Set<Work> works);

    Set<UserWork> findAllByWork(Work work);

    Set<UserWork> findAllByWorkAndUserIn(Work work, Set<User> users);
}
