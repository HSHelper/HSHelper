package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
