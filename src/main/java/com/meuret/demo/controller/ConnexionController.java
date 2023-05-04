package com.meuret.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.meuret.demo.dao.UserDao;
import com.meuret.demo.model.User;
import com.meuret.demo.security.JwtUtils;
import com.meuret.demo.security.MyUserDetails;
import com.meuret.demo.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MyUserDetailsService userDetailsService;

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody User user) {

        MyUserDetails userDetails;

        try {

            userDetails = (MyUserDetails) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword()
                    )

            ).getPrincipal();


        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        MyUserDetails myUserDetails = (MyUserDetails) userDetailsService.loadUserByUsername(user.getEmail());

        return new ResponseEntity<>(jwtUtils.generateJwt(userDetails), HttpStatus.OK);
    }

    @PostMapping("/inscription")

    public ResponseEntity<User> inscription(@RequestBody User user) {

        if(user.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(user.getEmail()).matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<User> optional = userDao.findByEmail(user.getEmail());

        if(optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



        userDao.save(user);

        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }
}
