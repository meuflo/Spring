package com.meuret.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.annotation.security.PermitAll;
import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity
public class ConfigurationSecurite extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    JwtFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //co depuis le code
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("user")
//                .roles("UTILISATEUR")
//                .and()
//                .withUser("admin")
//                .password("admin")
//                .roles("ADMINISTRATEUR");

//co depuis bdd
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, password, 1 FROM user WHERE email = ?")
//                .authoritiesByUsernameQuery(
//                        " SELECT email, IF(admin, 'ROLE_ADMINISTRATEUR','ROLE_UTILISATEUR') " +
//                        " FROM user" +
//                        " WHERE email = ?"
//
//                );

        auth.userDetailsService(myUserDetailsService);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(httpServletRequest -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.applyPermitDefaultValues();
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
            corsConfiguration.setAllowedHeaders(
                    Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                            "Accept", "Authorization","Access-Control-Allow-Origin"));
            return corsConfiguration;
        })
                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/connexion").permitAll()
//                .antMatchers("/login").permitAll()
////                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
//                .antMatchers("/**").hasAnyRole("ADMINISTRATEUR", "UTILISATEUR");

                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/connexion", "/inscription", "/users", "/user", "/user/**", "/user-par-pays/{monpays}/**").permitAll()
                .antMatchers("/**").hasAnyRole("ADMINISTRATEUR", "UTILISATEUR")

                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder creationPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
