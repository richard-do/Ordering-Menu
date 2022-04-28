package ca.sheridancollege.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public List<String> findRolesByUserId(Long id) {
		List <String> roles = new ArrayList<String>();
		User u = userRepository.findById(id).get();
		
		// get roles
		List<Role> roleList = u.getRoleList();
		for (Role r : roleList) {
			roles.add(r.getRoleName());
		}
		
		return roles;
	}

	@Override
	public List<String> findRolesByUsername(String name) {
		List <String> roles = new ArrayList<String>();
		User u = userRepository.findByUsername(name);
		
		// get roles
		List<Role> roleList = u.getRoleList();
		for (Role r : roleList) {
			roles.add(r.getRoleName());
		}
		
		return roles;
	}

}
