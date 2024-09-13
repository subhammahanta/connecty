package com.socialmedia.config;
import com.socialmedia.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.socialmedia.filter.JwtAuthFilter;
import com.socialmedia.service.AppUserDetailService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    //authentication
    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails employee = User.withUsername("Basant")
//                .password(passwordEncoder.encode("Pwd1"))
//                .roles("EMPLOYEE").build();
//
//        UserDetails hr = User.withUsername("Amit")
//                .password(passwordEncoder.encode("Pwd2"))
//                .roles("HR").build();
//
//        UserDetails admin = User.withUsername("Parmesh")
//                .password(passwordEncoder.encode("Pwd3"))
//                .roles("MANAGER", "HR").build();
//
//        return new InMemoryUserDetailsManager(employee, admin, hr);
        return new AppUserDetailService();
    }

    //authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.authorizeRequests()
//                .antMatchers("/nonsecureapp").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/welcome", "/text")
//                .authenticated().and().httpBasic().and().build();
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/signup","/user/authenticate").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user/test")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
