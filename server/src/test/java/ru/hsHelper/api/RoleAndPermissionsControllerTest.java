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
    public void test1() {
        Map<Permissions.PermissionType, Long> map = new HashMap<>();
        for (Permissions.PermissionType pt : Permissions.PermissionType.values()) {
            map.put(pt, permissionService.createPermission(new Permissions(pt)).getId());
        }
        Assertions.assertEquals(3, permissionService.getAllPermissions().size());
        long roleId = roleService.createRole(new Role(Role.RoleType.STUDENT)).getId();
        roleService.addPermissions(roleId, Set.of(map.get(Permissions.PermissionType.VIEW)));
        Assertions.assertEquals(3, permissionService.getAllPermissions().size());
        Assertions.assertEquals(1,
                roleService.getRoleById(roleId).getPermissions().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        roleService.addPermissions(roleId, Set.of(map.get(Permissions.PermissionType.COMMENT), map.get(Permissions.PermissionType.UPDATE)));
        Assertions.assertEquals(3,
                roleService.getRoleById(roleId).getPermissions().size());
        long roleId2 = roleService.createRole(new Role(Role.RoleType.OBSERVER)).getId();
        roleService.addPermissions(roleId2, Set.of(map.get(Permissions.PermissionType.COMMENT), map.get(Permissions.PermissionType.UPDATE)));
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(2, permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(2, permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());
        roleService.deletePermissions(roleId, Set.of(map.get(Permissions.PermissionType.COMMENT)));
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(2, permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());
        /*roleService.deleteRole(roleId);
        Assertions.assertEquals(0, permissionService.getPermissionById(map.get(Permissions.PermissionType.VIEW)).getRoles().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.UPDATE)).getRoles().size());
        Assertions.assertEquals(1, permissionService.getPermissionById(map.get(Permissions.PermissionType.COMMENT)).getRoles().size());*/
        permissionService.deletePermission(map.get(Permissions.PermissionType.UPDATE));
        Assertions.assertEquals(1, roleService.getRoleById(roleId).getPermissions().size());
        Assertions.assertEquals(1, roleService.getRoleById(roleId2).getPermissions().size());
    }
}
