package com.courtalon.jpaSecurityForm.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.jpaSecurityForm.metier.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
