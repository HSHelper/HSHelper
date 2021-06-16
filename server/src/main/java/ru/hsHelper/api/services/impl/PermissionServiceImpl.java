package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.repositories.PermissionsRepository;
import ru.hsHelper.api.services.PermissionService;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionsRepository permissionsRepository;

    @Autowired
    public PermissionServiceImpl(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    @Transactional
    @Override
    public Permissions createPermission(Permissions permissions) {
        return permissionsRepository.save(permissions);
    }

    @Transactional
    @Override
    public void deletePermission(long permissionId) {
        permissionsRepository.deleteById(permissionId);
    }

    @Transactional(readOnly = true)
    @Override
    public Permissions getPermissionById(long permissionId) {
        return permissionsRepository.findById(permissionId).orElseThrow(
                () -> new IllegalArgumentException("No permission with such id")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Permissions getPermissionByPermissionType(Permissions.PermissionType permissionType) {
        return permissionsRepository.findByPermissionType(permissionType).orElseThrow(
                () -> new IllegalArgumentException("No permission with such id")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Permissions> getAllPermissions() {
        return permissionsRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> getAllRoles(long permissionId) {
        return getPermissionById(permissionId).getRoles();
    }
}
