package ca.sheridancollege.services;

import java.util.List;

import ca.sheridancollege.beans.User;

public interface UserService {
	public User findById(Long id);
	public List<String> findRolesByUserId(Long id);
	public List<String> findRolesByUsername(String name);
}
