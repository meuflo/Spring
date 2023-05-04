package com.meuret.demo.security;

import com.meuret.demo.dao.UserDao;
import com.meuret.demo.model.User;
import io.jsonwebtoken.Claims;
import org.hibernate.persister.collection.OneToManyPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
   MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String jwt = request.getHeader("Authorization");

            if(jwt != null && jwt.startsWith("Bearer ")) {

                String token = jwt.substring(7);

                if(jwtUtils.isTokenValid(token)) {

                    Claims donnes = jwtUtils.getData(token);

                    //On recupre le user dans la base de données en fonction de l'email du jwt
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(donnes.getSubject());

                    //On ajoute le user au processus d'identification de spring security
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }

            }

            filterChain.doFilter(request, response);



    }

}




