package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.services.RoleService;

import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping({"/", ""})
    public Role createRole(@RequestBody Role.RoleType roleType) {
        return roleService.createRole(new Role(roleType));
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable long id) {
        roleService.deleteRole(id);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/")
    public Set<Role> getRoleByRoleType(@RequestBody Role.RoleType roleType) {
        return roleService.getRoleByRoleType(roleType);
    }

    @PutMapping("/{roleId}/permissions/")
    public Role addPermissions(@PathVariable long roleId,
                               @RequestBody Set<Long> permissionIds) {
        return roleService.addPermissions(roleId, permissionIds);
    }

    @DeleteMapping("/{roleId}/permissions/")
    public Role deletePermissions(@PathVariable long roleId, @RequestBody Set<Long> permissionIds) {
        return roleService.deletePermissions(roleId, permissionIds);
    }

    @GetMapping("/{roleId}/permissions")
    public Set<Permissions> getAllPermissions(@PathVariable long roleId) {
        return roleService.getAllPermissions(roleId);
    }
}
