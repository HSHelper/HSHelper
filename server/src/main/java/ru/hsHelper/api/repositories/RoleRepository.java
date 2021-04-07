package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findAllByRoleType(Role.RoleType roleType);
}
