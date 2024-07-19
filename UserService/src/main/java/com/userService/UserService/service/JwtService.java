package com.userService.UserService.service;

import com.userService.UserService.payload.AuthRequest;
import com.userService.UserService.utils.Blacklist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtService {

    @Autowired
    Blacklist blackList;

    private static final String SECERET = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";

    public String generateToken(AuthRequest authRequest){
        Map<String, String> claims = new HashMap<>();
        claims.put("email",authRequest.getEmail());
        claims.put("name",authRequest.getUserName());
        claims.put("password",authRequest.getPassword().toString());
        claims.put("roles",authRequest.getRoles().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authRequest.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECERET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECERET)
                .parseClaimsJws(token)
                .getBody();
        return Arrays.stream(claims.get("roles", String.class).split(",")).toList();
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName= extractUserName(token);
        List<String> roles= getRolesFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token) &&!blackList.isBlackListed(token) && !roles.isEmpty() && (roles.contains("USER")||roles.contains("ADMIN")));
    }
}
