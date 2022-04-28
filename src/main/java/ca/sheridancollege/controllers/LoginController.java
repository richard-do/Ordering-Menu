package ca.sheridancollege.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.beans.Role;
import ca.sheridancollege.beans.User;
import ca.sheridancollege.models.AuthenticationRequest;
import ca.sheridancollege.models.AuthenticationResponse;
import ca.sheridancollege.repositories.RoleRepository;
import ca.sheridancollege.repositories.UserRepository;
import ca.sheridancollege.security.JwtUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
@CrossOrigin(origins="http://localhost:8080")
public class LoginController {
	
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	private JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")

	public ResponseEntity<?> createAuthenticationToken(@RequestBody
			AuthenticationRequest authenticationRequest) throws Exception {
		//System.out.println("hello");
		try {
		//try to authenticate using a standard Username and Password token format
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken
					(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		//if no exception, get the username using our UserDetailsServiceImpl converter
		final UserDetails userDetails = 
				userDetailsService.
				loadUserByUsername(authenticationRequest
						.getUsername());
		
		//generate a token based on the authenticated user and wrap said token 
		//in an AuthenticationResponse in a ResponseEntity that says everything worked (ok) 	
		return ResponseEntity.ok(new AuthenticationResponse
				(jwtUtil.generateToken(userDetails)));
	}
	
}
