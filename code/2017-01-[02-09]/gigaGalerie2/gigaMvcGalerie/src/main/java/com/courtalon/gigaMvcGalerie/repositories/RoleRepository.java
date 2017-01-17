package com.courtalon.gigaMvcGalerie.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.gigaMvcGalerie.metier.Role;



public interface RoleRepository extends CrudRepository<Role, Integer> {

	Role findByRoleName(String roleName);
	
}
