package ca.sheridancollege.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.beans.User;
import ca.sheridancollege.domain.MenuItem;
import ca.sheridancollege.services.MenuItemService;
import ca.sheridancollege.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:8080")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuItemService menuItemService;
	
	@GetMapping(value = "/id/{id}")
	public List<String> findRolesByUserId(@PathVariable Long id) {
		return userService.findRolesByUserId(id);
	}
	
	@GetMapping(value = "/username/{username}")
	public List<String> findRolesByUsername(@PathVariable String username) {
		return userService.findRolesByUsername(username);
	}
}
