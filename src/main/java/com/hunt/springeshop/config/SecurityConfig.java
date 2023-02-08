package com.hunt.springeshop.config;

import com.hunt.springeshop.domain.Role;
import com.hunt.springeshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private  UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> {
//                            try {
//                                authz
//                                        .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name())
//                                        .anyRequest().authenticated()
//                                                .and()
//                                    .formLogin()
//                                    .loginPage("/login")
//                                    .loginProcessingUrl("/auth")
//                                    .permitAll()
//                                .and()
//                                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                    .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
//                                    .invalidateHttpSession(true)
//                                .and()
//                                    .csrf().disable();
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                )
//                .httpBasic(withDefaults());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/users").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name())
                .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/auth")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                    .csrf().disable();
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}

//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http.authorizeHttpRequests()
//                    .anyRequest().authenticated()
//                    // ...
//                    .securityContext((securityContext) -> securityContext
//                            .requireExplicitSave(true)
//                    );
//            return http.build();


//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest().authenticated().and().httpBasic();
//
//        return http.build();




//        @Bean
//        SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
//            http
//                    .authorizeHttpRequests(requests -> requests
//                            .antMatchers("/api/v1/users/**").hasRole("USER")
//                            .antMatchers("/api/v1/admins/**").hasRole("ADMIN")
//                            .anyRequest().permitAll());
//
//            return http.build();
//        }













