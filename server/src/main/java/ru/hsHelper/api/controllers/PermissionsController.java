package ru.hsHelper.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.services.PermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionsController {
    PermissionService permissionService;

    @Autowired
    public PermissionsController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping({"", "/"})
    public Permissions createPermission(@RequestBody Permissions.PermissionType permissionType) {
        return permissionService.createPermission(new Permissions(permissionType));
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable long id) {
        permissionService.deletePermission(id);
    }

    @GetMapping("/{id}")
    public Permissions getPermissionById(@PathVariable long id) {
        return permissionService.getPermissionById(id);
    }
    
    @GetMapping("/")
    public Permissions getPermissionByPermissionType(@RequestBody Permissions.PermissionType permissionType) {
        return permissionService.getPermissionByPermissionType(permissionType);
    }
}
