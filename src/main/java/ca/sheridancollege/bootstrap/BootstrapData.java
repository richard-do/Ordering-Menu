package ca.sheridancollege.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.domain.MenuItem;
import ca.sheridancollege.repositories.MenuItemRepository;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private MenuItemRepository menuItemRepository;
	
	@Override
	public void run(String... args) throws Exception {
		//Add the following to the run() of the BootstrapDataClass	
		User user = userRepository.save(new User("Dario", "12345"));  
		User user2 = userRepository.save(new User("user", "user"));
				
		Role role = roleRepository.save(new Role("ROLE_ADMIN"));
		Role role2 = roleRepository.save(new Role("ROLE_USER"));
		
			// admin, user
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(role);
			roleList.add(role2);
			
			// dario
			List<User> userList = new ArrayList<User>();
			userList.add(user);
		
			role.setUserList(userList);
			user.setRoleList(roleList);
			
			// user only
			List<Role> roleListUserOnly = new ArrayList<Role>();
			List<User> userListUserOnly = new ArrayList<User>();
			
			roleListUserOnly.add(role2);
			userListUserOnly.add(user2);
			
			role2.setUserList(userListUserOnly);
			user2.setRoleList(roleListUserOnly);
			

		MenuItem m1 = new MenuItem("Spring rolls", 6.99f, "Pork, vegetables, vermincelli noodles", "springrolls.png"); 
		
		MenuItem m2 = new MenuItem("Ga (Chicken)", 18.99f, "Steamed chicken", "ga.png");
		
		MenuItem m3 = new MenuItem("Banh Mi (Vietnamese Sandwich)", 10.99f, "Vietnamese sandwich. Beef, pate, fat,"
				+ " pickled vegetables", "banhmi.png");
		
		MenuItem m4 = new MenuItem("Pho Bo (Beef noodles)", 14.99f, "Beef broth, vermicelli noodles,"
				+ " and an assortment of toppings", "pho.png");
			
		MenuItem m5 = new MenuItem("Bun Bo Hue (Spicy beef noodle)", 10.10f, "Spicy beef broth, vermincelli noodles, "
				+ "and an assortment of toppings", "bunbohue.png");
		
		MenuItem m6 = new MenuItem("Rice", 1f, "Bowl of rice", "rice.png");
			
		
		userRepository.save(user);
		userRepository.save(user2);
		roleRepository.save(role);
		roleRepository.save(role2);
		menuItemRepository.save(m1);
		menuItemRepository.save(m2);
		menuItemRepository.save(m3);
		menuItemRepository.save(m4);
		menuItemRepository.save(m5);
		menuItemRepository.save(m6);
	}
	

}

