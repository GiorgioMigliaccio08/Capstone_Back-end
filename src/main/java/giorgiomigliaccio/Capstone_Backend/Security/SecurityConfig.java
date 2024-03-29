package giorgiomigliaccio.Capstone_Backend.Security;

import giorgiomigliaccio.Capstone_Backend.Exceptions.ExceptionHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    @Autowired
    JWTAuthFilter jwtAuthFilter;
    @Autowired
    CorsFilter corsFilter;
    @Autowired
    ExceptionHandlerFilter exceptionHandlerFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.disable());
        http.csrf(c -> c.disable());

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/users/**").authenticated());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/prenotazioni/**").authenticated());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/archiviazione/**").authenticated());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/role/**").authenticated());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter, JWTAuthFilter.class);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();    }
    @Bean
    PasswordEncoder pwEncoder() {
        return new BCryptPasswordEncoder(10);    }
}