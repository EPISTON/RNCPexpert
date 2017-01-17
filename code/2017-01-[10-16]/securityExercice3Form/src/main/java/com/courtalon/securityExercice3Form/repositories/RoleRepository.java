package com.courtalon.securityExercice3Form.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.securityExercice3Form.metier.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	Role findByRoleName(String roleName);
}
