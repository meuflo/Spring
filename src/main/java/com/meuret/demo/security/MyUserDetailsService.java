package com.meuret.demo.security;

import com.meuret.demo.dao.UserDao;
import com.meuret.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optional = userDao.findByEmail(email);

        if(optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new MyUserDetails(optional.get());
    }
}
