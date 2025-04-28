package com.example.security;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil implements Serializable {

	private static final long serialVersionUID = 654352132132L;

	public static final long JWT_TOKEN_VALIDITY = 500 * 60 * 60;

	//private final String secretKey = "randomkey123";
	
	private final String secretKey = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n";
	/* Gets the Username(email) of the user from token */
	

	public Key getSigningKey() {
		byte[] getKey =  Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(getKey);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/* Retrieves the expiry of the token */

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimFromToken(token);
		return claimsResolver.apply(claims);
	}

	/* Secret key will be required for retrieving data from token */

	private Claims getAllClaimFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	/* Check if the token has expired */

	private Boolean isTokenExpired(String token) {

		return getExpirationDateFromToken(token).before(new Date());
	}

	UserService userService;

	// generate token for user

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(getSigningKey() , SignatureAlgorithm.HS256).compact();
	}

	/* Generate the token from the claims and required details */

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return null;
	}

	/* Check if the provided JWT token is valid or not */

	public Boolean validateToken(String token, UserDetails userDetails) {

		String username  =  getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
}
