package com.eden.enforcementService.security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtility implements Serializable {

    private static final long serialVersionUID = 234234523523L;

    @Value("${jwt.secret}")
    private String secretKey;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return applyClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return applyClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T applyClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //validate token
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return (!isTokenExpired(token));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        List<LinkedHashMap<String, String>> authoritiesCalim = (List<LinkedHashMap<String, String>>) getAllClaimsFromToken(token).get("Authorities");
        Collection<GrantedAuthority> authorities = new LinkedList<>();
        for (LinkedHashMap<String, String> map : authoritiesCalim) {
            authorities.add(new SimpleGrantedAuthority(map.get("authority")));
        }
        return authorities;
    }

    public void setBasicUserInfo(String token, HashMap<String, Object> basicUserinfo) {
        Claims claims = getAllClaimsFromToken(token);
        Integer operationId = (Integer) claims.get("operationId");
        String operationNameEn = (String) claims.get("operationName");
        String operationNameAr = (String) claims.get("operationNameAr");
        Integer userId = (Integer)  claims.get("userId");

        basicUserinfo.put("operationId", operationId);
        basicUserinfo.put("operationNameEn", operationNameEn);
        basicUserinfo.put("operationNameAr", operationNameAr);
        basicUserinfo.put("userId", userId);
    }

}
