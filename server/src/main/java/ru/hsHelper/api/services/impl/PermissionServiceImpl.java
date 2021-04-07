package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.repositories.PermissionsRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.services.PermissionService;

import javax.persistence.Access;

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
    public void deletePermission(long id) {
        permissionsRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Permissions getPermissionById(long id) {
        return permissionsRepository.findById(id).orElseThrow(
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
}
