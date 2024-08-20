package com.practicespring.stay_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.practicespring.stay_manager.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder());
                return authProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/user/**").hasRole("USER")
                                                .requestMatchers("/register", "/requestformadmin", "/apply-room",
                                                                "/forgot_password", "/forgot-password",
                                                                "/reset-password",
                                                                "/admin/register", "/applicationform", "/index",
                                                                "/register2", "/admin/approverequest", "/verify-otp",
                                                                "/reset-password",
                                                                "/addbankaccount", "/about", "/contact", "/css/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/admin/register", "/forgot-password",
                                                                "/feedbackform", "/apply-room",
                                                                "/applicationform", "/admin/approverequest",
                                                                "/addbankaccount"))
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/index")
                                                .loginProcessingUrl("/login-user")
                                                .successHandler(new CustomAuthenticationSuccessHandler())
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login")
                                                .permitAll());

                return http.build();
        }
}
