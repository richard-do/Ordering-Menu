package ca.sheridancollege.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	public User findByUserId(Long id);
}
