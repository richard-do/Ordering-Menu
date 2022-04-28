package ca.sheridancollege.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	private String SECRET_KEY = 
			"this is a secret, should be original, "
			+ "and of a decent length!";

	//extract the username from a set of known claims
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	//extract the expiration from a set of known claims
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	//resolve all claims based on an existing token
	//used by extractUsername and extractExpiration
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	//cryptographically securely parse all claims from a JWT token based on our secret key
	//used by extractClaim
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	//does token expire before now?
	//used in validateToken
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	//generate a token based on our userDetails
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}
	
	//used in generateToken to set the various fields and cryptographically sign
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
			.setClaims(claims)
			//our authenticated user
			.setSubject(subject)
			//issued at the current time
			.setIssuedAt(new Date(System.currentTimeMillis()))
			//expiring 5 hours from now
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	//does the username match, and is the token still valid?
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}

