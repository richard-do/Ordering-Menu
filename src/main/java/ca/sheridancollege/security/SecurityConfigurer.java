package ca.sheridancollege.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.sheridancollege.filters.JwtRequestFilter;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth using our UserDetailsServiceImpl
		// and plain text passwords
		auth.userDetailsService
		(userDetailsService).passwordEncoder
		(NoOpPasswordEncoder.getInstance());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		//TODO -- remove this next line in a production system!
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.antMatchers("/api/menuitems/**").hasRole("ADMIN")
			//allow everybody to try to authenticate
			.antMatchers("/api/login/authenticate", "/api/user/username/**").permitAll()
			//TODO -- remove this next line in a production system!
			.antMatchers("/h2-console/**").permitAll()
			//but lock down the rest of the ReSTful service
			.anyRequest().authenticated()
			//essentially, tell our ReSTful service it should not manage sessions
			//.. meaning we should not record authenticated users in a session – they must pass a valid jwt with each request
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			//finally, inject our filter
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/api/menuitems", "/api/menuitems/printreciept");
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}