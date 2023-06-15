package com.meuret.demo.security;

import com.meuret.demo.model.Role;
import com.meuret.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    public MyUserDetails() {
    }

    private User user;
    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return roles;
//
//        if(utilisateur.isAdmin()) {
//            roles.add(new SimpleGrantedAuthority("ADMINISTRATEUR"));
//        } else {
//            roles.add(new SimpleGrantedAuthority("UTILISATEUR"));
//        }
//        return roles;

//        return utilisateur.isAdmin()
//                ? List.of(new SimpleGrantedAuthority("ADMINISTRATEUR"))
//                : List.of(new SimpleGrantedAuthority("UTILISATEUR"));

//        return List.of(new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMINISTRATEUR" : "ROLE_UTILISATEUR"));

//        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));




    }



    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
