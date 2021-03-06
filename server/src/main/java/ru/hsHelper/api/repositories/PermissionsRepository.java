package ru.hsHelper.api.repositories;

import org.jetbrains.annotations.NotNull;
import ru.hsHelper.api.entities.Permissions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface PermissionsRepository extends CrudRepository<Permissions, Long> {
    Optional<Permissions> findByPermissionType(Permissions.PermissionType permissionType);
    Set<Permissions> findByIdIn(Set<Long> ids);
    @NotNull
    Set<Permissions> findAll();
}
