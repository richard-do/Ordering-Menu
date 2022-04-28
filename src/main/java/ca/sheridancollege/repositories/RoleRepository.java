package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {
	public List<Role> findByUserList_UserId(Long id);
}
