package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Permissions;

import java.util.Set;

public interface PermissionService {
    Permissions createPermission(Permissions permissions);
    void deletePermission(long id);
    Permissions getPermissionById(long id);
    Permissions getPermissionByPermissionType(Permissions.PermissionType permissionType);
    Set<Permissions> getAllPermissions();
}
