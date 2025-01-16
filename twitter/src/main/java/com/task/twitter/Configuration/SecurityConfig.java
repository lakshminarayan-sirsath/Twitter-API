package com.task.twitter.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.task.twitter.JWT_UtilityAndFilter.JwtFilter;
import com.task.twitter.RoleBasedAuthentication.UserDetailsService_Impl;

@Configuration
public class SecurityConfig {

	@Autowired
    private UserDetailsService_Impl userDetailsService_Impl;
    
    @Autowired
    private JwtFilter jwtFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Cross-Site Request Forgery
                .authorizeHttpRequests(requests -> requests
//                		// swagger
//                		.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                		
                		//FOR ALL
                		.requestMatchers("/user/new_user", "/user/login").permitAll()
                        .anyRequest().authenticated())
                		.sessionManagement(session -> session
                				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                		
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .permitAll())
//                .logout(logout -> logout
//                        .permitAll());

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
    
    
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService_Impl);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
      AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
      authManagerBuilder.authenticationProvider(authenticationProvider());
      return authManagerBuilder.build();
  }
}
