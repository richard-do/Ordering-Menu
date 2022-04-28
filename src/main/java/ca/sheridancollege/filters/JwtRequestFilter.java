package ca.sheridancollege.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ca.sheridancollege.security.JwtUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;
	private List<String> excludedURLs = new ArrayList<String>();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authorizationHeader =
				request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		//extract jwt token from request header,
		// then the username form the token
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		//verify we have a user and that they are not already authenticated and authorized
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//get the Spring version of our User
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			//if the username is valid and the token is not expired
			if (jwtUtil.validateToken(jwt, userDetails)) {
				//set the security context for this user's request with a valid usernamePasswordAuthenticationToken
				//ie set them as authenticated and authorized
				UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upaToken);
			}
			
			// continue the chain
			filterChain.doFilter(request, response);
		}
		
		
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		
		excludedURLs.add("/api/menuitems");
		excludedURLs.add("/api/login/authenticate");
		excludedURLs.add("/api/menuitems/printreciept");
		
		for ( String excludedURL : excludedURLs) {
			if (excludedURL.equals(path)) return true;
			if (excludedURL.matches(path)) return true;
		}
		
		return false;
	}
}
