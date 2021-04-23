package ru.hsHelper.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hsHelper.api.entities.Permissions;
import ru.hsHelper.api.entities.Role;
import ru.hsHelper.api.repositories.PermissionsRepository;
import ru.hsHelper.api.repositories.RoleRepository;
import ru.hsHelper.api.services.RoleService;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionsRepository permissionsRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionsRepository permissionsRepository) {
        this.roleRepository = roleRepository;
        this.permissionsRepository = permissionsRepository;
    }

    @Transactional
    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No role with such id")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> getRoleByRoleType(Role.RoleType roleType) {
        return roleRepository.findAllByRoleType(roleType);
    }

    @Transactional
    @Override
    public Role addPermissions(long roleId, Set<Long> permissionsIds) {
        Role role = getRoleById(roleId);
        Set<Permissions> permissions = permissionsRepository.findByIdIn(permissionsIds);
        role.getPermissions().addAll(permissions);
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role deletePermissions(long roleId, Set<Long> permissionsIds) {
        Role role = getRoleById(roleId);
        Set<Permissions> permissions = permissionsRepository.findByIdIn(permissionsIds);
        role.getPermissions().removeAll(permissions);
        return roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
