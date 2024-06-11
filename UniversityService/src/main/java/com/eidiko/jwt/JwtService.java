package com.eidiko.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET = "9a2f8c4e6b0d71f3e8b925a45747f894a3d6bc70fa8d5e21a15a6d8c3b9a0e7c";
	
	//this method returns token as a string 
	public String tokenGenarateMethod(UserDetails userDetails) {
		
		System.out.println("----------------------------JwtService-----------------");
		
		
		return Jwts.builder().
				setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+6000000))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	//this is for secret key (this method called inside tokenGenaratorMethod() method)
	private Key getSignInKey() {
		 byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	}

}
