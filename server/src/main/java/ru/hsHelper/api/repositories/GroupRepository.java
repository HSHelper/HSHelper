package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Set<Group> findAllByIdIn(Set<Long> ids);
}
