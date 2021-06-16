package ru.hsHelper.api.services;

import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;

import java.util.Set;

public interface RoleService {
    Role createRole(Role role);
    void deleteRole(long roleId);
    Role getRoleById(long roleId);
    Set<Role> getRoleByRoleType(Role.RoleType roleType);
    Role addPermissions(long roleId, Set<Long> permissionsIds);
    Role deletePermissions(long roleId, Set<Long> permissionIds);
    Set<Role> getAllRoles();
    Set<Permissions> getAllPermissions(long roleId);
}
