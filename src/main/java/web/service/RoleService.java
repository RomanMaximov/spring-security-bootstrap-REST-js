package web.service;

import web.model.Role;

import java.util.Set;

public interface RoleService {
    void saveRole(Set<Role> roles);
    Set<Role> getAllRoles();
    Role findRole(Role role);
}

