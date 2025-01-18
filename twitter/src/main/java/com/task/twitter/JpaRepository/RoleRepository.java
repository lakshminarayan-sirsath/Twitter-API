package com.task.twitter.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.task.twitter.RoleBasedAuthentication.ValidRole;
import com.task.twitter.Table.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	
	@Query("SELECT r FROM Role r WHERE r.role = ?1")
	public Role findByRole(ValidRole role);
	
}
