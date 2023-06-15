package com.meuret.demo.security;

import com.meuret.demo.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    String jwtSecret;

    public String generateJwt(MyUserDetails userDetails) {
//si erreur : java.lang.IllegalStateException: Either 'payload' or 'claims' must be specified.
//        Map<String,Object> payload = new HashMap<>();
//        payload.put("test", "test");

//        return Jwts.builder()
//            .setClaims(payload)

        //Map<String,Object> payload = new HashMap<>();

       // payload.put("test", "test");

        String roles ="";
         for(Role role : userDetails.getUser().getRoles()) {
             roles += role.getName() + ",";
         }


        Map<String, Object> donnees = new HashMap<>();
        donnees.put("firstName", userDetails.getUser().getFirstName());
        donnees.put("name", userDetails.getUser().getName());
        donnees.put("roles", roles);



        return Jwts.builder()
                    .setClaims(donnees)
                    .setSubject(userDetails.getUsername())
                   // .setClaims(payload)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();


    }

    public Claims getData(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValid (String jwt) {

        try {

            Claims donnees = getData(jwt);

        } catch (SignatureException e) {
            return false;
        }

        return true;
    }

}
