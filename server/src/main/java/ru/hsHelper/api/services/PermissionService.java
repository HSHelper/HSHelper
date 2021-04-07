package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Permissions;

public interface PermissionService {
    Permissions createPermission(Permissions permissions);
    void deletePermission(long id);
    Permissions getPermissionById(long id);
    Permissions getPermissionByPermissionType(Permissions.PermissionType permissionType);
}
