package com.icm.tiremanagementapi.security;

import com.icm.tiremanagementapi.config.WebConfig;
import com.icm.tiremanagementapi.security.filtrers.JwtAuthenticationFilter;
import com.icm.tiremanagementapi.security.filtrers.JwtAuthorizationFilter;
import com.icm.tiremanagementapi.security.jwt.JwtUtils;
import com.icm.tiremanagementapi.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(WebConfig.class)
public class WebSecurityConfig {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost", "http://192.168.1.232","http://192.168.1.139"  )); // Especificar los orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Especificar los métodos HTTP permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Especificar los encabezados permitidos
        configuration.setAllowCredentials(true); // Permitir el envío de credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        HeaderWriter headerWriter = new StaticHeadersWriter("Access-Control-Allow-Origin", "*");

        return httpSecurity
                .cors()
                .and()
                .csrf(config -> config.disable())
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .headers().addHeaderWriter(headerWriter)  // Agregar el encabezado Access-Control-Allow-Origin
                .and()
                .httpBasic().disable()  // Deshabilitar la autenticación básica
                .formLogin().disable()  // Deshabilitar el formulario de inicio de sesión
                .logout().disable()  // Deshabilitar la funcionalidad de logout
                .exceptionHandling().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/hello").permitAll();
                    auth.requestMatchers("/api/camiones").permitAll();
                    auth.requestMatchers("/unprotected").permitAll();
                    auth.requestMatchers("/unprotected").permitAll();
                    auth.requestMatchers("/redirigido").permitAll();
                    auth.requestMatchers("/api/files/**").permitAll();
                    auth.requestMatchers("/saludof").permitAll();
                    auth.requestMatchers("/get-ip").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/doc/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-resources/**").permitAll();
                    auth.requestMatchers("/webjars/**").permitAll();
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }


    /*
        @Bean
        UserDetailsService userDetailsService(){
            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
            manager.createUser(User.withUsername("Eduardo")
                    .password(passwordEncoder().encode("1234"))
                    .roles()
                    .build());
            return manager;
        }

    */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws  Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}