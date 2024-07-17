package com.eidiko.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eidiko.exception_handler.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j

//this class consist jwt,refresh toeken genaration, extracing claims,validate token
public class JwtService {
	
	//secret key
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public void validateToken(final String token) {
		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);

	}

	//below two methods used for  jwt token genaration
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap();
		claims.put("username", userName);
	//	claims.put("roles", role);
		return createToken(claims, userName);
	}
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	
	//below two methods used for  refresh token genaration
	public String generateRefreshToken(String userName) {
		Map<String, Object> claims = new HashMap();
		claims.put("username", userName);
	//	claims.put("roles", role);
		return createRefreshToken(claims, userName);
	}
	private String createRefreshToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10*1000 * 60 * 60))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//this method is for extract email(subject) in the token 
	public String extractEmailId(String token) {
		log.info("------inside extractEmailid JwtService-----");
		return (String) extractClaims(token).get("username");
	}

	//this is for extract claims(subject,role,playload,signature,etc..)
	public Claims extractClaims(String token) {
		log.info("------inside extractclaims JwtService----");
		Claims clims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		log.info(clims.toString());
		return clims;
	}
	
	
	//another validation token
	public boolean validateToken1(final String token) {
		 try {
	            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	            return true; // Token is valid
		 } catch (Exception e) {
	            log.error("Invalid JWT token: {}", e.getMessage());
	            throw new BadRequestException("Invalid JWT token.");
	        }
    }
	
	
	

}
