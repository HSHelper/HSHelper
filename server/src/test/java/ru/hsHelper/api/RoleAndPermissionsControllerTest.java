package ru.hsHelper.api;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.services.PermissionService;
import ru.hsHelper.api.services.RoleService;

import java.util.Set;

@SpringBootTest
public class RoleAndPermissionsControllerTest {
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Autowired
    public RoleAndPermissionsControllerTest(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Test
    public void BasicTest() {
        Map<Permissions.PermissionType, Long> map = new HashMap<>();
        for (Permissions.PermissionType pt : Permissions.PermissionType.values()) {
            map.put(pt, permissionService.createPermission(new Permissions(pt)).getId());
        }

        final int permissionCount = 3;
        int permissionCountInFirstRole = 0;
        int permissionCountInSecondRole = 0;
        int[] roleCountInPermissions = new int[3];

        Assertions.assertEquals(permissionCount, permissionService.getAllPermissions().size());

        long FirstRoleId = roleService.createRole(new Role(Role.RoleType.STUDENT)).getId();

        roleService.addPermissions(FirstRoleId, Set.of(map.get(Permissions.PermissionType.VIEW)));
        permissionCountInFirstRole = 1;
        roleCountInPermissions[0] = 1;
        Assertions.assertEquals(permissionCount, permissionService.getAllPermissions().size());
        Assertions.assertEquals(permissionCountInFirstRole,
                roleService.getRoleById(FirstRoleId).getPermissions().size());
        Assertions.assertEquals(roleCountInPermissions[0],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());

        roleService.addPermissions(FirstRoleId, Set.of(map.get(Permissions.PermissionType.COMMENT),
                map.get(Permissions.PermissionType.UPDATE)));
        permissionCountInFirstRole = 3;
        roleCountInPermissions[1] = roleCountInPermissions[2] = 1;
        Assertions.assertEquals(permissionCountInFirstRole,
                roleService.getRoleById(FirstRoleId).getPermissions().size());

        long SecondRoleId2 = roleService.createRole(new Role(Role.RoleType.OBSERVER)).getId();

        roleService.addPermissions(SecondRoleId2, Set.of(map.get(Permissions.PermissionType.COMMENT),
                map.get(Permissions.PermissionType.UPDATE)));
        permissionCountInSecondRole = 2;
        roleCountInPermissions[1] = roleCountInPermissions[2] = 2;
        Assertions.assertEquals(roleCountInPermissions[0],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(roleCountInPermissions[1],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(roleCountInPermissions[2],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());

        roleService.deletePermissions(FirstRoleId, Set.of(map.get(Permissions.PermissionType.COMMENT)));
        permissionCountInFirstRole = 1;
        roleCountInPermissions[1] = 1;
        Assertions.assertEquals(roleCountInPermissions[0],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(roleCountInPermissions[2],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(roleCountInPermissions[1],
                permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());
        /*roleService.deleteRole(FirstRoleId);
        Assertions.assertEquals(0, permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());*/
        permissionService.deletePermission(map.get(Permissions.PermissionType.UPDATE));
        roleCountInPermissions[2] = 0;
        permissionCountInSecondRole = 1;
        Assertions.assertEquals(permissionCountInFirstRole,
                roleService.getRoleById(FirstRoleId).getPermissions().size());
        Assertions.assertEquals(permissionCountInSecondRole,
                roleService.getRoleById(SecondRoleId2).getPermissions().size());
    }
}
