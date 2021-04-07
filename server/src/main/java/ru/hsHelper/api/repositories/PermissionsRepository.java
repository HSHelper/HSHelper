package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Permissions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PermissionsRepository extends CrudRepository<Permissions, Long> {
    Optional<Permissions> findByPermissionType(Permissions.PermissionType permissionType);
}
