package br.com.animelistor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final String ADMIN = "ADMIN";
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        		.csrf(csrf -> csrf.disable())
        		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		.authorizeHttpRequests(auth -> 
        			auth
        			.requestMatchers(HttpMethod.DELETE).hasRole(ADMIN)
        			.requestMatchers(HttpMethod.PUT).hasRole(ADMIN)
        			.anyRequest().authenticated())
        		.httpBasic(Customizer.withDefaults())
        		.build();
    }
    
   
    @Bean
    UserDetailsService userDetailService() {
    	UserDetails user = User.builder()
    			.username("user")
    			.password(passwordEncoder().encode("123"))
    			.roles("USER")
    			.build();
    	
    	UserDetails admin = User.builder()
    			.username("admin")
    			.password(passwordEncoder().encode("123"))
    			.roles(ADMIN)
    			.build();
    	
    	
    	return new InMemoryUserDetailsManager(user,admin);
    	
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
