package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
