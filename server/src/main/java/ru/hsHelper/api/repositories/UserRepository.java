package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAllByIdIn(Set<Long> userIds);
    @NotNull
    Set<User> findAll();

    Optional<User> findByEmail(String email);
}
