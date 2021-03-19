package ru.hsHelper.api.repositories;

import ru.hsHelper.api.entities.Permissions;
import org.springframework.data.repository.CrudRepository;

public interface PermissionsRepository extends CrudRepository<Permissions, Long> {
}
