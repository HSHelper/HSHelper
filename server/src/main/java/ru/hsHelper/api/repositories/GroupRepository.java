package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
