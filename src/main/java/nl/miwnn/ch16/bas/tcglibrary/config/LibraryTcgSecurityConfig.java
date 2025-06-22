package nl.miwnn.ch16.bas.tcglibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Bas Folkers
 * Configure the security for my applicaition
 */

@Configuration
@EnableWebSecurity
public class LibraryTcgSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/expansion/overview").permitAll()
                        .requestMatchers( "/card/overview").permitAll()
                        .requestMatchers( "/deck/overview").permitAll()
                        .requestMatchers("/webjars/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(login -> login
                        .defaultSuccessUrl("/expansion/overview", true)
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/"));

        return httpSecurity.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
