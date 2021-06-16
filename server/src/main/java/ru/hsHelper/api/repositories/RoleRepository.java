package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findAllByRoleType(Role.RoleType roleType);
    @NotNull
    Set<Role> findAll();
    Set<Role> findAllByIdIn(Set<Long> ids);
}
