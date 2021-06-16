package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;

import java.util.Set;

public interface PermissionService {
    Permissions createPermission(Permissions permissions);
    void deletePermission(long permissionId);
    Permissions getPermissionById(long permissionId);
    Permissions getPermissionByPermissionType(Permissions.PermissionType permissionType);
    Set<Permissions> getAllPermissions();
    Set<Role> getAllRoles(long permissionId);
}
